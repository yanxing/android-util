package com.yanxing.model;

/**
 * Created by lishuangxiang on 2016/1/21.
*/
public class User {

    private String userID;
    private String username;

    public User() {
    }

    public User(String username, String userID) {
        this.username = username;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
