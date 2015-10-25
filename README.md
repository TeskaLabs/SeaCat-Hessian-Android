# SeaCat client bridge for Hessian on Android

The small bridge that enables Android developers to use Hessian over SeaCat.

More about SeaCat at [TeskaLabs.com](http://teskalabs.com/).

## The client bridge

The package name is `com.teskalabs.seacat.android.hessian` and it is available in the folder '**/client**'.

Client bridge depends on `flamingo-android-hessian-client-2.2.0.jar` that is also present in the folder '**/client**'.


## The example

...


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

```
public class MainActivity extends ActionBarActivity {

    HessianProxyFactory hessianFactory = new HessianProxyFactory();
...

	private void doHessianRequest() throws IOException
	{
		String hessianURL = "http://[your-server]:8080/HessianServer/HelloServlet";

		HelloService hello = (HelloService) hessianFactory.create(HelloService.class, hessianURL);
		
		final String output = hello.sayHelloTo("Android test");
    }

```

