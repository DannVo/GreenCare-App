package com.example.iotapplication.Adapter.AddNewDevice;

import com.example.iotapplication.Adapter.AddNewDevice.DataNewInfo;

public class AddNewInfo {

    private Integer code;
    private String content;
    private DataNewInfo dataNewInfo;

    public AddNewInfo(Integer code, String content, DataNewInfo dataNewInfo) {
        this.code = code;
        this.content = content;
        this.dataNewInfo = dataNewInfo;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DataNewInfo getDataNewInfo() {
        return dataNewInfo;
    }

    public void setDataNewInfo(DataNewInfo dataNewInfo) {
        this.dataNewInfo = dataNewInfo;
    }
}
