# SeaCat client bridge for Hessian on Android

The small bridge that enables Android developers to use Hessian over SeaCat.

More about SeaCat at [TeskaLabs.com](http://teskalabs.com/).

## The client bridge

The package name is `com.teskalabs.seacat.android.hessian` and it is available in the folder '**/client**'.

Client bridge depends on `flamingo-android-hessian-client-2.2.0.jar` that is also present in the folder '**/client**'.


## The example

```java
package org.example.hessian.seacatapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.caucho.hessian.client.HessianProxyFactory;
import com.teskalabs.seacat.android.hessian.HessianSeaCatConnectionFactory;
import org.example.hessian.service.HelloService;

import java.io.IOException;

public class HessianActivity extends ActionBarActivity {

    HessianProxyFactory hessianFactory = new HessianProxyFactory();
    HessianSeaCatConnectionFactory hessianSeaCatFactory = new HessianSeaCatConnectionFactory();
    String hessianURL = "http://hessianhost.seacat/HessianHelloServer/HelloServlet";

    private TextView outputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hessian);

        outputView = (TextView)findViewById(R.id.output_view);

        hessianSeaCatFactory.setHessianProxyFactory(hessianFactory);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hessian, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                doHessianRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doHessianRequest() throws IOException
    {
        HelloService hello = (HelloService) hessianFactory.create(HelloService.class, hessianURL);

        final String output = hello.sayHelloTo("Android test");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputView.setText(output);
            }
        });
    }

}

```


### FAQ

#### How to launch simple Hessian Server

The folder '**/server**' contains very simple Hello servlet that can be accessed using Hessian client. Deploy this into an Apache TomCat.

Servlet API will be available at:  
`http://[your-server]:8080/HessianHelloServer/HelloServlet`

#### How to add the Hessian client into your Android app

1. Copy `flamingo-android-hessian-client-2.2.0.jar` into `app/lib` folder of our Android application.
2. Use Android Studio 'Project view', go to `app/lib` folder, right-click on `flamingo-android-hessian-client-2.2.0.jar` file and select `Add as Libary ...`
3. Confirm module (app) of your Android project into which the library is to be added.

##### Simple example of the Hessian client usage in the Android app:

```java
import import com.caucho.hessian.client.HessianProxyFactory;

...

public class MainActivity extends ActionBarActivity {

	String hessianURL = "http://[your-server]:8080/HessianHelloServer/HelloServlet";
    HessianProxyFactory hessianFactory = new HessianProxyFactory();

...

	private void doHessianRequest() throws IOException
	{
		HelloService hello = (HelloService) hessianFactory.create(HelloService.class, hessianURL);
		
		final String output = hello.sayHelloTo("Android test");
    }

```

#### How to add the SeaCat/Hessian bridge into your Android app

1. Copy `hessian/client/com` from this repo into `app/src/main/java` folder of your Android app. The result should look like this: `app/src/main/java/com/teskalabs/seacat/android/hessian`.

2. Add import: import com.teskalabs.seacat.android.hessian.HessianSeaCatConnectionFactory;

##### Simple example of the Hessian client with SeaCat bridge usage in the Android app:

```java
import import com.teskalabs.seacat.android.hessian.HessianSeaCatConnectionFactory;

public class MainActivity extends ActionBarActivity {

	String hessianURL = "http://[hessianhost].seacat/HessianHelloServer/HelloServlet";
	HessianProxyFactory hessianFactory = new HessianProxyFactory();
	HessianSeaCatConnectionFactory hessianSeaCatFactory = new HessianSeaCatConnectionFactory();

...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    ...
    
    	// Inject SeaCat Factory into Hessian
    	hessianSeaCatFactory.setHessianProxyFactory(hessianFactory);
    	
    ...
	}

...

	private void doHessianRequest() throws IOException
	{
		HelloService hello = (HelloService) hessianFactory.create(HelloService.class, hessianURL);
		
		final String output = hello.sayHelloTo("Android test");
    }

```