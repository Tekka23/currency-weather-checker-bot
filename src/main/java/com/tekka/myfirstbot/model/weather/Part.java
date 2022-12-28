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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Part part)) return false;
        return minTemp == part.minTemp && avgTemp == part.avgTemp && windSpeed == part.windSpeed && partName.equals(part.partName) && condition.equals(part.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partName, minTemp, avgTemp, windSpeed, condition);
    }
}
