package edu.uwf.tabletopgroup.rest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.models.User;

/**
 *
 */
public class TableTopRestClientUser {
    public static final String TAG = "TTRCU";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final int SUCCESS_MESSAGE = 0;
    public static final int FAILURE_MESSAGE = 1;
    private static Context application;
    private static TableTopRestClient client;
    private static TableTopRestClientUser clientUser;
    /**
     *
     * @param application
     * @throws Exception
     */
    private TableTopRestClientUser(Context application) throws Exception{
        if(application == null)
            throw new Exception("Context is null!");
        setContext(application);
        setClient(new TableTopRestClient(getContext()));
    }
    public static TableTopRestClientUser getClientUser(Context application) throws Exception{
        if(clientUser == null)
            clientUser = new TableTopRestClientUser(application);
        return clientUser;
    }
    public Context getContext(){
        return TableTopRestClientUser.application;
    }
    private void setContext(Context application){
        TableTopRestClientUser.application = application;
    }
    private void setClient(TableTopRestClient client){
        TableTopRestClientUser.client = client;
    }
    public void getUsers(User user) throws JSONException {
        client.setBasicAuth(user.getEmail(), user.getPassword());
        client.get("users", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "getUsers", response.toString(2));
                } catch (JSONException e) {
                    Log.e(TAG + "getUsers",
                            String.format("onSuccess(%d, %s, %s)", statusCode, headers, response), e);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                try {
                    Log.d(TAG + "getUsers", array.toString(2));
                } catch (JSONException e) {
                    Log.e(TAG + "getUsers",
                            String.format("onSuccess(%d, %s, %s)", statusCode, headers, array), e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "getUsers",
                        String.format("onFailure(%d, %s, %s)",
                                statusCode, throwable, errorResponse), throwable);
                for (Header header : headers)
                    Log.e(TAG + "getUsers", "header: " + header.getValue());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG + "getUsers",
                        String.format("onFailure(%d, %s, %s)", statusCode, throwable, responseString), throwable);
                for (Header header : headers)
                    Log.e(TAG + "getUsers", "header: " + header.getValue());

            }
        });
        Log.d(TAG, "blurb");
    }
    public void postUsers(String username, String password){
        RequestParams params = new RequestParams();
        params.put(USERNAME, username);
        params.put(PASSWORD, password);
        client.post("users", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "postUsers", response.toString(2));
                } catch (JSONException e) {
                    Log.e(TAG + "postUsers",
                            String.format("onSuccess(%d, %s", statusCode, response), e);
                    for (Header header : headers)
                        Log.e(TAG + "postUsers", "header: " + header.getValue());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "postUsers",
                        String.format("onFailure(%d, %s, %s", statusCode, throwable, errorResponse), throwable);
                for (Header header : headers)
                    Log.e(TAG + "postUsers", "header: " + header.getValue());
            }
        });
    }
    public void getCharacters(User user, final Handler.Callback callback) throws JSONException {
        client.setBasicAuth(user.getEmail(), user.getPassword());
        client.get("characters", null, new JsonHttpResponseHandler() {
            Message mMessage = new Message();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "getCharacters", response.toString(2));
                    if(!(response.length() == 0)){
                        //do something
                    }
                    setSuccessMessage();
                } catch (JSONException e) {
                    Log.e(TAG + "getCharacters",
                            String.format("onSuccess(%d, %s, %s)", statusCode, headers, response), e);
                    setFailMessage();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                try {
                    Log.d(TAG + "getCharacters", array.toString(2));
                    setSuccessMessage();
                } catch (JSONException e) {
                    Log.e(TAG + "getCharacters",
                            String.format("onSuccess(%d, %s, %s)", statusCode, headers, array), e);
                    setFailMessage();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "getCharacters",
                        String.format("onFailure(%d, %s, %s)",
                                statusCode, throwable, errorResponse), throwable);
                for (Header header : headers)
                    Log.e(TAG + "getCharacters", "header: " + header.getValue());
                setFailMessage();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG + "getCharacters",
                        String.format("onFailure(%d, %s, %s)", statusCode, throwable, responseString), throwable);
                for (Header header : headers)
                    Log.e(TAG + "getCharacters", "header: " + header.getValue());
                setFailMessage();
            }

            private void setSuccessMessage() {
                mMessage.what = TableTopRestClientUser.SUCCESS_MESSAGE;
                callback.handleMessage(mMessage);
            }

            private void setFailMessage() {
                mMessage.what = TableTopRestClientUser.FAILURE_MESSAGE;
                callback.handleMessage(mMessage);
            }
        });
    }
    public void postCharacters(Character character){
        RequestParams params = new RequestParams();
        params.add(Character.NAME, character.getName());
        params.add(Character.RACE, character.getRace());
        params.add(Character.CLASS, character.getCharacterClass());
        params.add(Character.STRENGTH, String.valueOf(character.getStrength()));
        params.add(Character.DEXTERITY, String.valueOf(character.getDexterity()));
        params.add(Character.CONSTITUTION, String.valueOf(character.getConstitution()));
        params.add(Character.INTELLIGENCE, String.valueOf(character.getIntelligence()));
        params.add(Character.WISDOM, String.valueOf(character.getWisdom()));
        params.add(Character.CHARISMA, String.valueOf(character.getCharisma()));
        params.add(Character.LEVEL, String.valueOf(character.getLevel()));
        params.add(Character.EXPERIENCE, String.valueOf(character.getExperience()));
        client.post("characters", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "postCharacters", response.toString(2));
                } catch (JSONException e) {
                    Log.e(TAG + "postCharacters",
                            String.format("onSuccess(%d, %s", statusCode, response), e);
                    for (Header header : headers)
                        Log.e(TAG + "postCharacters", "header: " + header.getValue());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "postCharacters",
                        String.format("onFailure(%d, %s, %s", statusCode, throwable, errorResponse), throwable);
                for (Header header : headers)
                    Log.e(TAG + "postCharacters", "header: " + header.getValue());
            }
        });
    }

}