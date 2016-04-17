package edu.uwf.tabletopgroup.models;

import java.util.ArrayList;
import java.util.List;

import edu.uwf.tabletopgroup.rest.SocketService;

/**
 * Models the single user object
 * @author Michael Kimball
 */
public class User {

    public static boolean isLoggedIn = false;

    private static String email;
    private static String password;
    private static String username;
    private static ArrayList<Character> characters;
    private static ArrayList<Invite> invites;
    private static Game currentGame;
    public static void setUser(String username, String pass)
    {
        User.username = username;
        User.password = pass;
    }

    /**
     * Returns User email
     * @return User email
     */
    public static String getEmail() {
        return User.email;
    }

    /**
     * Sets User email
     * @param email - User's email
     */
    public static void setEmail(String email) {
        User.email = email;
    }

    /**
     * Returns User password
     * @return User password
     */
    public static String getPassword() {
        return User.password;
    }

    /**
     * Set the password of User
     * @param password - New password
     */
    public static void setPassword(String password) {
        User.password = password;
    }

    /**
     * Returns User username
     * @return User username
     */
    public static String getUsername() {
        return User.username;
    }

    /**
     * Set the username of User
     * @param username - New username
     */
    public static void setUsername(String username){
        User.username = username;
    }

    /**
     * Add character to the roster
     * @param character - New character
     */
    public static void addCharacter(Character character)
    {
        if(User.characters == null){
            User.characters = new ArrayList<>();
        }
        User.characters.add(character);
    }

    public static void setCharacters(ArrayList<Character> characters){
        User.characters = characters;
    }
    /**
     * Returns User characters
     * @return User characters
     */
    public static ArrayList<Character> getCharacters(){
        return User.characters;
    }

    public static Character getCharacter(String id){
        Character temp = null;
        for(Character character : characters) {
            if (character.getId().equals(id)){
                temp = character;
                break;
            }
        }
        return temp;
    }

    public static Character getCharacter(int position){
        return characters.get(position);
    }

    public static void setInvites(ArrayList<Invite> invites){
        User.invites = invites;
    }

    public static ArrayList<Invite> getInvites(){
        return User.invites;
    }

    public static Invite getInvite(int position){
        return invites.get(position);
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        User.currentGame = currentGame;
    }
}
