package com.tekka.myfirstbot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherItem {
    @JsonProperty(value = "fact")
    private FactWeather factWeather;
    @JsonProperty(value = "forecast")
    private Forecast forecast;


    public FactWeather getFactWeather() {
        return factWeather;
    }

    public void setFactWeather(FactWeather factWeather) {
        this.factWeather = factWeather;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
