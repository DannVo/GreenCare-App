package com.example.iotapplication.Adapter.AsyncPostDataIoT;

public class DataIoTResponse {

    private String id;
    private String device_id;
    private String name;
    private float temperature;
    private float humidity;
    private float moisture;
    private String predict_status;

    public DataIoTResponse(String id, String device_id, String name, float temperature, float humidity, float moisture, String predict_status) {
        this.id = id;
        this.device_id = device_id;
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.moisture = moisture;
        this.predict_status = predict_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public float getMoisture() {
        return moisture;
    }

    public void setMoisture(float moisture) {
        this.moisture = moisture;
    }

    public String getPredict_status() {
        return predict_status;
    }

    public void setPredict_status(String predict_status) {
        this.predict_status = predict_status;
    }
}
