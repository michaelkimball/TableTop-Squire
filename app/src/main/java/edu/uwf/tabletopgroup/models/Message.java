package edu.uwf.tabletopgroup.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TableTopSquire
 * Message.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/17/16
 */
public class Message implements Parcelable{
    private String name;
    private String message;
    private String gameId;
    private boolean isUser;

    public Message() {}

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
        this.isUser = false;
    }

    public Message(String name, String message, boolean isUser) {
        this.name = name;
        this.message = message;
        this.isUser = isUser;
    }

    public Message(Parcel in){
        String[] strings = new String[3];
        in.readStringArray(strings);
        this.name = strings[0];
        this.message = strings[1];
        this.gameId = strings[2];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public void setGameId(String gameId){
        this.gameId = gameId;
    }

    public String getGameId(){
        return gameId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] strings = new String[]{name, message, gameId};
        dest.writeStringArray(strings);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>(){

        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public JSONObject toJSON() throws JSONException{
        JSONObject object = new JSONObject();
        object.put("playerName", name);
        object.put("message", message);
        object.put("gameID", gameId);
        return object;
    }
}
