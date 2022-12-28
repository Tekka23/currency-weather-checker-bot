package com.tekka.myfirstbot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherItem {
    @JsonProperty(value = "fact")
    private FactWeather factWeather;
    @JsonProperty(value = "forecast")
    private Forecast forecast;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherItem that)) return false;
        return factWeather.equals(that.factWeather) && forecast.equals(that.forecast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factWeather, forecast);
    }
}
