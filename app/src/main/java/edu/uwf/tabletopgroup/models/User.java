package edu.uwf.tabletopgroup.models;

import java.util.ArrayList;

/**
 * Models the single user object
 * @author Michael Kimball
 */
public class User {

    private static User user;
    private static String email;
    private static String password;
    private static String username;
    private static ArrayList<Character> characters;

    private User(){}

    /**
     * Initializes user
     * @param email - email of user
     * @param password - password of user
     * @return - new User object
     */
    public static User createUser(String email, String password){
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    /**
     * Returns User reference
     * @return User reference
     */
    public static User getUser(){
        return user;
    }

    /**
     * Returns User email
     * @return User email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets User email
     * @param email - User's email
     */
    public void setEmail(String email) {
        User.email = email;
    }

    /**
     * Returns User password
     * @return User password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of User
     * @param password - New password
     */
    public void setPassword(String password) {
        User.password = password;
    }

    /**
     * Returns User username
     * @return User username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of User
     * @param username - New username
     */
    public void setUsername(String username){
        user.username = username;
    }

    /**
     * Add character to the roster
     * @param character - New character
     */
    public void addCharacter(Character character){
        if(characters == null)
            characters = new ArrayList<>();
        characters.add(character);
    }

    /**
     * Returns User characters
     * @return User characters
     */
    public ArrayList<Character> getCharacters(){
        return characters;
    }

}
