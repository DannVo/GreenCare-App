package com.example.iotapplication;

import java.io.Serializable;
import java.util.Date;

public class SampleModel implements Serializable {
    private int image;
    private String header, desc;
    private String applyAt;

    public SampleModel(int image, String header, String desc, String applyAt) {
        this.image = image;
        this.header = header;
        this.desc = desc;
        this.applyAt = applyAt;
    }
    public String getApplyAt() {
        return applyAt;
    }

    public void setApplyAt(String applyAt) {
        this.applyAt = applyAt;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
