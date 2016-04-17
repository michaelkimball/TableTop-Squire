package edu.uwf.tabletopgroup.models;

/**
 * TableTopSquire
 * Invite.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/13/16
 */
public class Invite {
    private String playerName;
    private String gameName;
    private String gameId;
    public Invite(){};
    public Invite(String playerName, String gameName, String gameId){
        this.playerName = playerName;
        this.gameName = gameName;
        this.gameId =gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
