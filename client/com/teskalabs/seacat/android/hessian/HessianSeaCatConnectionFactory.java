package com.teskalabs.seacat.android.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianURLConnection;

import java.io.IOException;
import java.net.URL;

import mobi.seacat.client.SeaCatClient;

public class HessianSeaCatConnectionFactory implements HessianConnectionFactory
{
    private static final String TAG = HessianSeaCatConnectionFactory.class.getName();


    private HessianProxyFactory _proxyFactory = null;

    public void setHessianProxyFactory(HessianProxyFactory factory)
    {
      _proxyFactory = factory;
      _proxyFactory.setConnectionFactory(this);
    }


    public HessianConnection open(URL url) throws IOException
    {
        mobi.seacat.client.http.URLConnection conn = (mobi.seacat.client.http.URLConnection) SeaCatClient.open(url);

        long connectTimeout = _proxyFactory.getConnectTimeout();

        if (connectTimeout >= 0)
            conn.setConnectTimeout((int) connectTimeout);

        conn.setDoOutput(true);

        long readTimeout = _proxyFactory.getReadTimeout();

        if (readTimeout > 0) {
            try {
                conn.setReadTimeout((int) readTimeout);
            } catch (Throwable e) {
            }
        }

        return new HessianSeaCatConnection(url, conn);
    }

}
