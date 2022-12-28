package com.tekka.myfirstbot.model.weather;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "forecast")
public class Forecast {
    @JsonProperty(value = "sunrise")
    private String sunrise;
    @JsonProperty(value = "sunset")
    private String sunset;
    @JsonProperty(value = "parts")
    private Part[] partsOfDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forecast forecast)) return false;
        return sunrise.equals(forecast.sunrise) && sunset.equals(forecast.sunset) && Arrays.equals(partsOfDay, forecast.partsOfDay);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sunrise, sunset);
        result = 31 * result + Arrays.hashCode(partsOfDay);
        return result;
    }
}
