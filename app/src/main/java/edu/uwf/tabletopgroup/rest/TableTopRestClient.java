package edu.uwf.tabletopgroup.rest;

import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by michael on 2/6/16.
 */
public class TableTopRestClient  {
    private static Context application;
    private static PersistentCookieStore cookieStore;
    private static AsyncHttpClient client;
    private static final String BASE_URL = "http://192.169.175.123:3000/api/";

    public TableTopRestClient(Context application){
        TableTopRestClient.application = application;
        TableTopRestClient.cookieStore = new PersistentCookieStore(application);
        TableTopRestClient.client = new AsyncHttpClient();
        TableTopRestClient.client.setCookieStore(cookieStore);
    }
    public void setBasicAuth(String username, String password){
        client.setBasicAuth(username, password);
    }
    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
        client.post(getAbsoluteUrl(url), params, responseHandler);
        client.removeHeader(HTTP.CONTENT_TYPE);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}