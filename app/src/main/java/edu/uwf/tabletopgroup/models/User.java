package edu.uwf.tabletopgroup.models;

import java.util.ArrayList;

/**
 * Created by michael on 2/6/16.
 */
public class User {

    private static User user;
    private static String email;
    private static String password;
    private static String username;
    private static ArrayList<Character> characters;

    private User(){}

    public static User createUser(String email, String password){
        user = new User();
        user.email = email;
        user.password = password;
        return user;
    }

    public static User getUser(){
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        User.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        user.username = username;
    }

    public void addCharacter(Character character){
        if(characters == null)
            characters = new ArrayList<>();
        characters.add(character);
    }

    public ArrayList<Character> getCharacters(){
        return characters;
    }

}
