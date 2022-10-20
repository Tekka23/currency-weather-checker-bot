package com.tekka.myfirstbot.model.weather;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "forecast")
public class Forecast {
    @JsonProperty(value = "sunrise")
    private String sunrise;
    @JsonProperty(value = "sunset")
    private String sunset;
    @JsonProperty(value = "parts")
    private Part[] partsOfDay;

    public Forecast() {
    }

    public Forecast(String sunrise, String sunset, Part[] partsOfDay) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.partsOfDay = partsOfDay;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Part[] getPartsOfDay() {
        return partsOfDay;
    }

    public void setPartsOfDay(Part[] partsOfDay) {
        this.partsOfDay = partsOfDay;
    }
}
