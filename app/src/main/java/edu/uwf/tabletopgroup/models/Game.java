package edu.uwf.tabletopgroup.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * TableTopSquire
 * Game.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/16/16
 */
public class Game implements Parcelable{
    private String gameId;
    private String socketId;
    private ArrayList<Player> players;
    public Game(String gameId, String socketId){
        this.gameId = gameId;
        this.socketId = socketId;
        this.players = new ArrayList<>();
    }

    public Game(Parcel in){
        String[] strings = new String[2];
        in.readStringArray(strings);
        Player[] playerArray = in.createTypedArray(Player.CREATOR);
        this.gameId = strings[0];
        this.socketId = strings[1];
        this.players = new ArrayList<>(Arrays.asList(playerArray));
//        Bundle bundle = in.readBundle();
//        this.gameId = bundle.getString(TableTopKeys.KEY_GAME_ID);
//        this.socketId = bundle.getString(TableTopKeys.KEY_SOCKET_ID);
//        if(bundle.containsKey(TableTopKeys.KEY_PLAYER_LIST))
//            this.players = bundle.getParcelableArrayList(TableTopKeys.KEY_PLAYER_LIST);
//        else
//            this.players = new ArrayList<>();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] strings = new String[]{gameId, socketId};
        Player[] playerArray = players.toArray(new Player[players.size()]);
        dest.writeStringArray(strings);
        dest.writeTypedArray(playerArray, 0);
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>(){

        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
}
