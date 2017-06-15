package com.juanjoflores.mantenimientoapp.Utils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;


import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


import java.io.UnsupportedEncodingException;

import javax.net.ssl.SSLException;


/**
 * Created by DesarrolloMovil on 30/11/16.
 */

public class MantenimientoAppClient {


    // Url Base
    public static final String BASE_URL = "http://data.ct.gov/resource/hma6-9xbg.json";

    private static final String CONTENT_TYPE = "application/json";
    private static AsyncHttpClient client = new AsyncHttpClient();



    public static void get(String url, String controller, String action, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        X509HostnameVerifier verifier = new AbstractVerifier()
        {
            @Override
            public void verify(final String host, final String[]
                    cns, final String[] subjectAlts) throws SSLException
            {
                verify(host, cns, subjectAlts, true);
            }
        };
;       SSLSocketFactory sslSocket = SSLSocketFactory.getSocketFactory();
        sslSocket.setHostnameVerifier(verifier);


        client.setSSLSocketFactory(sslSocket);
        client.get(url + controller + action, params != null ? params : new RequestParams(),
                responseHandler);


    }


    public static void get(Context context, String url,
                           BinaryHttpResponseHandler binaryHttpResponseHandler) {
        client.get(url, binaryHttpResponseHandler);
    }

    public static void post(Context context, String url, String controller,
                            String action, RequestParams params,
                            AsyncHttpResponseHandler responseHandler)
            throws UnsupportedEncodingException {
        client.setTimeout(50000);
        url = url + controller + action;
        client.post(context, url,
                params != null ? new StringEntity(params.toString(), "UTF-8"): new StringEntity(""), // Si param no viene nulo
                CONTENT_TYPE, responseHandler);
    }

    public static void post(String url, String controller, String action,
                            RequestParams params, AsyncHttpResponseHandler responseHandler)
            throws UnsupportedEncodingException {
        client.setTimeout(50000);

        client.post(url + controller + action, params, responseHandler);

    }

    public static void addHeader(String key, String value) {
        client.addHeader(key, value);
    }

    public static void removeHeader(String key) {
        client.removeHeader(key);
    }


}
