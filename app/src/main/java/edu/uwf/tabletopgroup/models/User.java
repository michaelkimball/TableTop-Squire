package edu.uwf.tabletopgroup.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the single user object
 * @author Michael Kimball
 */
public class User {

    public static boolean isLoggedIn = false;

    private static String email;
    private static String password;
    private static String username;
    private static List<Character> characters = new ArrayList<>();

    public static void setUser(String email, String pass)
    {
        User.email = email;
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
        User.characters.add(character);
    }

    /**
     * Returns User characters
     * @return User characters
     */
    public static List<Character> getCharacters(){
        return User.characters;
    }

}
