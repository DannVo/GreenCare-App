package com.example.iotapplication.Adapter.AsyncPostDataIoT;

public class GetDataIotResponse {

    private Integer device_id;
    private String temperature;
    private String humidity;
    private String soilMoistureValue;

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSoilMoistureValue() {
        return soilMoistureValue;
    }

    public void setSoilMoistureValue(String soilMoistureValue) {
        this.soilMoistureValue = soilMoistureValue;
    }

    public GetDataIotResponse(Integer device_id, String temperature, String humidity, String soilMoistureValue) {
        this.device_id = device_id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.soilMoistureValue = soilMoistureValue;
    }
}
