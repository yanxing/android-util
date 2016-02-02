package com.yanxing.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishuangxiang on 2016/1/21.
*/
public class User implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.username);
    }

    protected User(Parcel in) {
        this.userID = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
