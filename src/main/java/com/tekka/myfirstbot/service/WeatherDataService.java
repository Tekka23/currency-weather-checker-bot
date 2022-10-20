package com.tekka.myfirstbot.service;

import com.tekka.myfirstbot.config.weather.WeatherConfig;
import com.tekka.myfirstbot.model.weather.City;
import com.tekka.myfirstbot.model.weather.WeatherItem;
import com.tekka.myfirstbot.service.exceptions.HttpException;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Slf4j
@Component
public class WeatherDataService {
    private String conditionEmoji;
    private final HttpWrapper wrapper;
    private final WeatherConfig config;

    @Autowired
    public WeatherDataService(HttpWrapper wrapper, WeatherConfig config) {
        this.wrapper = wrapper;
        this.config = config;

    }
    public String getWeatherMessage(String cityName){
        WeatherItem weatherItem;
        String message;
        try {
            weatherItem = getWeather(cityName);
            switch (weatherItem.getFactWeather().getCondition()){
                case "rain", "drizzle", "moderate-rain", "light-rain" -> conditionEmoji = ":cloud_with_rain:";
                case "heavy-rain", "continuous-heavy-rain", "showers", "thunderstorm", "thunderstorm-with-rain", "thunderstorm-with-hail" -> conditionEmoji = ":cloud_with_lightning_and_rain:";
                case "sun" -> conditionEmoji = ":sunny:";
                case "cloudy", "overcast", "partly-cloudy" -> conditionEmoji = ":cloud:";
                case "wet-snow", "light-snow", "snow", "snow-showers" -> conditionEmoji = ":cloud_with_snow:";
            }

            String partName = ZonedDateTime.now().getHour() > 12 ? "evening" : "morning";
            int num = ZonedDateTime.now().getHour() > 12 ? 1 : 0;
            String sunriseEmoji = ":sunrise:";
            String MESSAGE_SAMPLE = """
                    It's %s  %s
                    
                    The temperature outside is %d degree and it feels like %d degree
                                
                    The wind speed is %dm/s   %s
                                
                    The time of sunset is %s   %s
                                
                    The time of sunrise is %s   %s
                                
                    Minimal temperature in the %s would be %d degree and outside of your window - %s
                    """;
            String sunsetEmoji = ":city_sunrise:";
            String windEmoji = ":dash:";
            message = EmojiParser.parseToUnicode(String.format(MESSAGE_SAMPLE,
                    weatherItem.getFactWeather().getCondition(),
                    conditionEmoji,
                    weatherItem.getFactWeather().getTemperature(),
                    weatherItem.getFactWeather().getFeelsLike(),
                    weatherItem.getFactWeather().getWindSpeed(),
                    windEmoji,
                    weatherItem.getForecast().getSunset(),
                    sunsetEmoji,
                    weatherItem.getForecast().getSunrise(),
                    sunriseEmoji,
                    partName,
                    weatherItem.getForecast().getPartsOfDay()[num].getMinTemp(),
                    weatherItem.getForecast().getPartsOfDay()[num].getCondition()
                    ));

        }
        catch (Exception e){
            log.warn("Unable to retrieve data from yandex server:" + e.getMessage());
            message = "Sorry, I'm unable to retrieve data from my server";
        }
        return message;
    }
    private WeatherItem getWeather(String cityName) {
        if(cityName.isEmpty()) cityName = "saint-petersburg";
        String yandexURL= getYandexUrl(cityName);
        return wrapper.getFromUrl(yandexURL,
                WeatherItem.class,
                config.getYandexHeaderName(),
                config.getYandexHeaderValue());
    }
    private String getYandexUrl(String cityName){
        String geocodeURL = config.getGeocodingURL() + cityName;

        City[] geocodeItems = wrapper.getFromUrl(geocodeURL, City[].class, config.getGeocodingHeaderName(), config.getGeocodingHeaderValue());
        if(geocodeItems.length <= 0){
            throw new HttpException("This city is not supported - " + cityName);
        }
        log.info(String.format(config.getWeatherURL()+"lat=%s&lon=%s",
                geocodeItems[0].getLatitude(), geocodeItems[0].getLongitude()));
        return String.format(config.getWeatherURL()+"lat=%s&lon=%s",
                geocodeItems[0].getLatitude(), geocodeItems[0].getLongitude());
    }
}
