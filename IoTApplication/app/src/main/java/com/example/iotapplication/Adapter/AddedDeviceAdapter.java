package com.example.iotapplication.Adapter;

import com.google.gson.annotations.SerializedName;

public class AddedDeviceAdapter {
    private String userID;
    private int id;

    public AddedDeviceAdapter(String userID, int id) {
        this.userID = userID;
        this.id = id;
    }

    @SerializedName("body")

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
