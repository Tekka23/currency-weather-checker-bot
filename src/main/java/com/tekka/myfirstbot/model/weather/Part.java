package com.tekka.myfirstbot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Part {
    @JsonProperty(value = "part_name")
    private String partName;
    @JsonProperty(value = "temp_min")
    private int minTemp;
    @JsonProperty(value = "temp_avg")
    private int avgTemp;
    @JsonProperty(value = "wind_speed")
    private int windSpeed;
    @JsonProperty(value = "condition")
    private String condition;


    public Part() {
    }

    public Part(String partName, int minTemp, int avgTemp, int windSpeed, String condition) {
        this.partName = partName;
        this.minTemp = minTemp;
        this.avgTemp = avgTemp;
        this.windSpeed = windSpeed;
        this.condition = condition;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(int avgTemp) {
        this.avgTemp = avgTemp;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
