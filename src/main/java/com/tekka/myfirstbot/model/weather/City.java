package com.tekka.myfirstbot.model.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
   private String name;
   private double latitude;
   private double longitude;
   private String country;
   private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Double.compare(city.latitude, latitude) == 0 && Double.compare(city.longitude, longitude) == 0 && name.equals(city.name) && country.equals(city.country) && state.equals(city.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, country, state);
    }
}
