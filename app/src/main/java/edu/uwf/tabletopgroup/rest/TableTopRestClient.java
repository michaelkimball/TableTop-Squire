package edu.uwf.tabletopgroup.rest;

import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * This handles the post and get requests
 * in an abstract manner.
 * @author Michael Kimball
 * TODO: Add delete requests
 */
public class TableTopRestClient  {

    private static Context application;
    private static PersistentCookieStore cookieStore;
    private static AsyncHttpClient client;
    private static final String BASE_URL = "http://192.168.0.21:3000/api/";

    /**
     * Create HTTP client specific to TT backend
     * @param application - context reference
     */
    public TableTopRestClient(Context application){
        TableTopRestClient.application = application;
        TableTopRestClient.cookieStore = new PersistentCookieStore(application);
        TableTopRestClient.client = new AsyncHttpClient();
        TableTopRestClient.client.setCookieStore(cookieStore);
    }

    /**
     * Set basic authentication header
     * @param username - user email
     * @param password - user password
     */
    public void setBasicAuth(String username, String password){
        client.setBasicAuth(username, password);
    }

    /**
     * Perform a get request to TT backend
     * @param url - route (users, characters, etc)
     * @param params - data being passed for processing
     * @param responseHandler - handle success or failure
     */
    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Perform a post request to TT backend
     * @param url - route (users, characters, etc)
     * @param params - data being passed for processing
     * @param responseHandler - handle success or failure
     */
    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        client.post(getAbsoluteUrl(url), params, responseHandler);
        client.removeHeader(HTTP.CONTENT_TYPE);
    }

    /**
     * Perform a put request to TT backend
     * @param url - route (users, characters, etc)
     * @param params - data being passed for processing
     * @param responseHandler - handle success or failure
     */
    public void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        client.put(getAbsoluteUrl(url), params, responseHandler);
        client.removeHeader(HTTP.CONTENT_TYPE);
    }

    /**
     * Appends given url to the base url
     * @param relativeUrl - partial route
     * @return The full URL
     */
    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}