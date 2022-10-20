package com.tekka.myfirstbot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "fact")
public class FactWeather {
    @JsonProperty(value = "temp")
    private int temperature;
    @JsonProperty(value = "feels_like")
    private int feelsLike;
    @JsonProperty(value = "condition")
    private String condition;
    @JsonProperty(value = "wind_speed")
    private int windSpeed;

    public FactWeather(int temperature, int feelsLike, String condition, int windSpeed) {
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.windSpeed = windSpeed;
    }

    public FactWeather() {
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
}
