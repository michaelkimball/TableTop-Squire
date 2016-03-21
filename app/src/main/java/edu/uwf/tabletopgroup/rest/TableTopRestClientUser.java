package edu.uwf.tabletopgroup.rest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.models.User;

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
    public static Context getContext(){
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
     * Sets authentication then grabs the current user from the database
     * @throws JSONException - is thrown if access is
     * unauthorized
     */
    public static void getUser() throws JSONException {
        client.setBasicAuth(User.getUsername(), User.getPassword());
        client.get("user", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "getUser", response.toString(2));
                } catch (JSONException e) {
                    Log.e(TAG + "getUser",
                            String.format("JSONObject onSuccess(%d, %s, %s)", statusCode, headers, response), e);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                try {
                    Log.d(TAG + "getUser", array.toString(2));
                } catch (JSONException e) {
                    Log.e(TAG + "getUser",
                            String.format("JSONArray onSuccess(%d, %s, %s)", statusCode, headers, array), e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TAG + "getUser",
                        String.format("JSONObject onFailure(%d, %s, %s)",
                                statusCode, throwable, errorResponse), throwable);
                for (Header header : headers)
                    Log.e(TAG + "getUser", "header: " + header.getValue());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG + "getUser",
                        String.format("String onFailure(%d, %s, %s)", statusCode, throwable, responseString), throwable);
                for (Header header : headers)
                    Log.e(TAG + "getUser", "header: " + header.getValue());

            }
        });
    }

    /**
     * Creates user
     * @param username - username of the user
     * @param email - email of the user
     * @param password - password of the user to be hashed
     */
    public static void postUsers(final String username, String email, final String password, final Handler.Callback callback){
        RequestParams params = new RequestParams();
        params.put(TableTopKeys.KEY_USERNAME, username);
        params.put(TableTopKeys.KEY_EMAIL, email);
        params.put(TableTopKeys.KEY_PASSWORD, password);
        client.post("users", params, new JsonHttpResponseHandler() {
            Message message = new Message();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d(TAG + "postUsers", response.toString(2));
                    User.setUser(username, password);
                    message.what = TableTopRestClientUser.SUCCESS_MESSAGE;
                    callback.handleMessage(message);
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
                message.what = TableTopRestClientUser.FAILURE_MESSAGE;
                callback.handleMessage(message);
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
    public static void getCharacters(final Handler.Callback callback) throws JSONException {
        client.setBasicAuth(User.getUsername(), User.getPassword());
        client.get("characters", null, new JsonHttpResponseHandler() {
            Message message = new Message();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray array) {
                try {
                    Log.d(TAG + "getCharacters", array.toString(2));
                    Log.d(TAG + "getCharacters", "JSONArray response");
                    ArrayList<Character> characters = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = (JSONObject)array.get(i);
                        String id = jsonObject.getString(TableTopKeys.KEY_ID);
                        String name = jsonObject.getString(TableTopKeys.KEY_NAME);
                        String race = jsonObject.getString(TableTopKeys.KEY_RACE);
                        String characterClass = jsonObject.getString(TableTopKeys.KEY_CLASS);
                        int strength = jsonObject.getInt(TableTopKeys.KEY_STRENGTH);
                        int dexterity = jsonObject.getInt(TableTopKeys.KEY_DEXTERITY);
                        int constitution = jsonObject.getInt(TableTopKeys.KEY_CONSTITUTION);
                        int intelligence = jsonObject.getInt(TableTopKeys.KEY_INTELLIGENCE);
                        int wisdom = jsonObject.getInt(TableTopKeys.KEY_WISDOM);
                        int charisma = jsonObject.getInt(TableTopKeys.KEY_CHARISMA);
                        int level = jsonObject.getInt(TableTopKeys.KEY_LEVEL);
                        int experience = jsonObject.getInt(TableTopKeys.KEY_EXPERIENCE);
                        Character character = new Character(name, race, characterClass, id);
                        character.setStats(strength, dexterity, constitution, intelligence, wisdom, charisma);
                        character.setLevel(level);
                        character.setExperience(experience);
                        characters.add(character);
                    }
                    User.setCharacters(characters);
                    setSuccessMessage();
                } catch (JSONException e) {
                    Log.e(TAG + "getCharacters",
                            String.format("onSuccess(%d, %s, %s)", statusCode, headers, array), e);
                    setFailMessage();
                }
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
                message.what = TableTopRestClientUser.SUCCESS_MESSAGE;
                callback.handleMessage(message);
            }

            private void setFailMessage() {
                message.what = TableTopRestClientUser.FAILURE_MESSAGE;
                callback.handleMessage(message);
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
        params.add(TableTopKeys.KEY_NAME, character.getName());
        params.add(TableTopKeys.KEY_RACE, character.getRace());
        params.add(TableTopKeys.KEY_CLASS, character.getCharacterClass());
        params.add(TableTopKeys.KEY_STRENGTH, String.valueOf(character.getStrength()));
        params.add(TableTopKeys.KEY_DEXTERITY, String.valueOf(character.getDexterity()));
        params.add(TableTopKeys.KEY_CONSTITUTION, String.valueOf(character.getConstitution()));
        params.add(TableTopKeys.KEY_INTELLIGENCE, String.valueOf(character.getIntelligence()));
        params.add(TableTopKeys.KEY_WISDOM, String.valueOf(character.getWisdom()));
        params.add(TableTopKeys.KEY_CHARISMA, String.valueOf(character.getCharisma()));
        params.add(TableTopKeys.KEY_LEVEL, String.valueOf(character.getLevel()));
        params.add(TableTopKeys.KEY_EXPERIENCE, String.valueOf(character.getExperience()));

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