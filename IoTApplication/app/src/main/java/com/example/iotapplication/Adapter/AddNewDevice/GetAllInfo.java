package com.example.iotapplication.Adapter.AddNewDevice;

import java.util.List;

public class GetAllInfo {

    private Integer code;
    private String content;
    private List<DataNewInfo> data;

    public GetAllInfo(Integer code, String content, List<DataNewInfo> data) {
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

    public List<DataNewInfo> getData() {
        return data;
    }

    public void setData(List<DataNewInfo> data) {
        this.data = data;
    }
}
