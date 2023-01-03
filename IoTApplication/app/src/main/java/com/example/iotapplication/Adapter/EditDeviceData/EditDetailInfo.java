package com.example.iotapplication.Adapter.EditDeviceData;

public class EditDetailInfo {

    private Integer code;
    private String content;
    private EditData data;

    public EditDetailInfo(Integer code, String content, EditData data) {
        this.code = code;
        this.content = content;
        this.data = data;
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

    public EditData getData() {
        return data;
    }

    public void setData(EditData data) {
        this.data = data;
    }
}
