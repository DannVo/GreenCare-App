package com.example.iotapplication.Adapter.LoginRegister;

import com.example.iotapplication.Adapter.Data;

public class LoginResponse {
    private int code;
    private String content;
    private Data data;

    public LoginResponse(int code, String content, Data data) {
        this.code = code;
        this.content = content;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
