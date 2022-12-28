package com.tekka.myfirstbot.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "fact")
public class FactWeather {
    @JsonProperty(value = "temp")
    private int temperature;
    @JsonProperty(value = "feels_like")
    private int feelsLike;
    @JsonProperty(value = "condition")
    private String condition;
    @JsonProperty(value = "wind_speed")
     int windSpeed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactWeather that)) return false;
        return temperature == that.temperature && feelsLike == that.feelsLike && windSpeed == that.windSpeed && condition.equals(that.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, feelsLike, condition, windSpeed);
    }
}
