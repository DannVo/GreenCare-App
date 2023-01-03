package com.example.iotapplication.Adapter.AsyncPostDataIoT;

public class PostDataIoTResponse {

    private Integer code;
    private String content;
    private DataIoTResponse dataIoTResponse;

    public PostDataIoTResponse(Integer code, String content, DataIoTResponse dataIoTResponse) {
        this.code = code;
        this.content = content;
        this.dataIoTResponse = dataIoTResponse;
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

    public DataIoTResponse getDataIoT() {
        return dataIoTResponse;
    }

    public void setDataIoT(DataIoTResponse dataIoTResponse) {
        this.dataIoTResponse = dataIoTResponse;
    }
}
