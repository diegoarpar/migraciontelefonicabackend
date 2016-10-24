package services;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by joag on 21/10/16.
 * Munkys APPS copyright
 * CERTICAMARA TELEFONICA
 */
public class SingletonAdjuntarArchivos {
    private static HttpClient httpclient;
    private static ResponseHandler<String> responseHandler;

    public static HttpClient getHttpclient() {
        httpclient = null;
        httpclient = new DefaultHttpClient();
        return httpclient;
    }

    public static void setHttpclient(HttpClient httpclient) {
        SingletonAdjuntarArchivos.httpclient = httpclient;
    }

    public static ResponseHandler<String> getResponseHandler() {
        if(responseHandler == null){
            responseHandler = new BasicResponseHandler();
        }
        return responseHandler;
    }

    public static void setResponseHandler(ResponseHandler<String> responseHandler) {
        SingletonAdjuntarArchivos.responseHandler = responseHandler;
    }
}
