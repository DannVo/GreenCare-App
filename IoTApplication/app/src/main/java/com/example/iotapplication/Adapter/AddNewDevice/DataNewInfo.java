package com.example.iotapplication.Adapter.AddNewDevice;

public class DataNewInfo {
    private String id;
    private String user_id;
    private String device_id;
    private String name;
    private String description;
    private String status;

    public DataNewInfo(String id, String user_id, String device_id, String name, String description, String status) {
        this.id = id;
        this.user_id = user_id;
        this.device_id = device_id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }
}
