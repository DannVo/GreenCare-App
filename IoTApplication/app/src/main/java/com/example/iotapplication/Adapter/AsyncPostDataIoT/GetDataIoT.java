package com.example.iotapplication.Adapter.AsyncPostDataIoT;

public class GetDataIoT {

    private String device_id;
    private String name;
    private float temperature;
    private float humidity;
    private Integer moisture;



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

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public Integer getMoisture() {
        return moisture;
    }

    public void setMoisture(Integer moisture) {
        this.moisture = moisture;
    }

    public GetDataIoT(String device_id, String name, float temperature, float humidity, Integer moisture) {
        this.device_id = device_id;
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.moisture = moisture;
    }
}
