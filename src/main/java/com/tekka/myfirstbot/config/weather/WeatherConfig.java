package com.tekka.myfirstbot.config.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class WeatherConfig {

    @Value("${weather.url}")
    private String weatherURL;
    @Value("${weather.yandex.header.name}")
    private String yandexHeaderName;
    @Value("${weather.yandex.header.value}")
    private String yandexHeaderValue;
    @Value("${geocoding.url}")
    private String geocodingURL;
    @Value("${geocoding.header.name}")
    private String geocodingHeaderName;
    @Value("${geocoding.header.value}")
    private String geocodingHeaderValue;

    public String getWeatherURL() {
        return weatherURL;
    }

    public String getYandexHeaderName() {
        return yandexHeaderName;
    }

    public String getYandexHeaderValue() {
        return yandexHeaderValue;
    }

    public String getGeocodingHeaderName() {
        return geocodingHeaderName;
    }

    public String getGeocodingHeaderValue() {
        return geocodingHeaderValue;
    }

    public String getGeocodingURL() {
        return geocodingURL;
    }
}
