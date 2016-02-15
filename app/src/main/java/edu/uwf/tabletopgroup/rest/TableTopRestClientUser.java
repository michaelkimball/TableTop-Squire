package edu.uwf.tabletopgroup.rest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.tabletop_squire.R;

/**
 * Used to perform specific HTTP requests
 * to the TableTop backend
 * @author Michael Kimball
 * TODO: Add put and delete requests for characters and users
 */
public class TableTopRestClientUser {

    public static final String TAG = "TTRCU";

    public static final int SUCCESS_MESSAGE = 0;
    public static final int FAILURE_MESSAGE = 1;
    private static Context application;
    private static TableTopRestClient client;
    private static TableTopRestClientUser clientUser;

    /**
     * Private constructor to maintain singleton
     * @param application - context reference
     * @throws Exception - Thrown if context is null
     */
    private TableTopRestClientUser(Context application) throws Exception{
        if(application == null)
            throw new Exception("Context is null!");
        setContext(application);
        setClient(new TableTopRestClient(getContext()));
    }

    /**
     * Grabs or creates single reference to class
     * @param application - context reference
     * @return reference to TableTopRestClientUser
     * @throws Exception - Thrown if context is null
     */
    public static TableTopRestClientUser getClientUser(Context application) throws Exception{
        if(clientUser == null)
            clientUser = new TableTopRestClientUser(application);
        return clientUser;
    }

    /**
     * Gets context reference
     * @return context reference
     */
    public Context getContext(){
        return TableTopRestClientUser.application;
    }

    /**
     * Sets context reference
     * @param application
     */
    private void setContext(Context application){
        TableTopRestClientUser.application = application;
    }

    /**
     * Used to set the client reference
     * @param client - interacts with the server
     */
    private void setClient(TableTopRestClient client){
        TableTopRestClientUser.client = client;
    }

    /**
     * Sets authentication then grabs all users in the database
     * @param user - the current user for authentication
     * @throws JSONException - is thrown if access is
     * unauthorized
     */
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
    }

    /**
     * Creates user
     * @param email - email of the user
     * @param password - password of the user to be hashed
     */
    public void postUsers(String email, String password, final Looper looper){
        RequestParams params = new RequestParams();

        params.put(getContext().getString(R.string.key_username), email);
        params.put(getContext().getString(R.string.key_password), password);

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
                }finally{
                    looper.quitSafely();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "postUsers",
                        String.format("onFailure(%d, %s, %s", statusCode, throwable, errorResponse), throwable);
                for (Header header : headers)
                    Log.e(TAG + "postUsers", "header: " + header.getValue());

                looper.quitSafely();
            }
        });
    }

    /**
     * Grabs characters from the database

     * @param callback - method to execute at the
     *                 completion of the GET request
     * @throws JSONException - If authorization fails
     * this exception will be thrown
     */
    public void getCharacters(final Handler.Callback callback) throws JSONException {
        client.setBasicAuth(User.getEmail(), User.getPassword());
        client.get("characters", null, new JsonHttpResponseHandler() {
            Message mMessage = new Message();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "getCharacters", response.toString(2));
                    if (!(response.length() == 0)) {
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

    /**
     * Sends {@link edu.uwf.tabletopgroup.models.Character} data to the
     * server for processing.
     * @param character - Character to be added to users roster.
     */
    public void postCharacters(Character character){
        RequestParams params = new RequestParams();

        Context context = getContext();

        params.add(context.getString(R.string.key_name), character.getName());
        params.add(context.getString(R.string.key_race), character.getRace());
        params.add(context.getString(R.string.key_class), character.getCharacterClass());
        params.add(context.getString(R.string.key_stat_strenth), String.valueOf(character.getStrength()));
        params.add(context.getString(R.string.key_stat_dexterity), String.valueOf(character.getDexterity()));
        params.add(context.getString(R.string.key_stat_constitution), String.valueOf(character.getConstitution()));
        params.add(context.getString(R.string.key_stat_intelligence), String.valueOf(character.getIntelligence()));
        params.add(context.getString(R.string.key_stat_wisdom), String.valueOf(character.getWisdom()));
        params.add(context.getString(R.string.key_stat_charisma), String.valueOf(character.getCharisma()));
        params.add(context.getString(R.string.key_level), String.valueOf(character.getLevel()));
        params.add(context.getString(R.string.key_experience), String.valueOf(character.getExperience()));

        client.post("characters", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "postCharacters", response.toString(2)); // what is 2?
                } catch (JSONException e) {
                    Log.e(TAG + "postCharacters", String.format("onSuccess(%d, %s", statusCode, response), e);

                    for (Header header : headers) {
                        Log.e(TAG + "postCharacters", "header: " + header.getValue());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "postCharacters", String.format("onFailure(%d, %s, %s", statusCode, throwable, errorResponse), throwable);

                for (Header header : headers) {
                    Log.e(TAG + "postCharacters", "header: " + header.getValue());
                }
            }
        });
    }

}