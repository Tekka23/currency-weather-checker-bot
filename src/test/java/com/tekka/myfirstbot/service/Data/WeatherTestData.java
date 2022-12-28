package com.tekka.myfirstbot.service.Data;

import com.tekka.myfirstbot.model.weather.FactWeather;
import com.tekka.myfirstbot.model.weather.Forecast;
import com.tekka.myfirstbot.model.weather.Part;
import com.tekka.myfirstbot.model.weather.WeatherItem;

public class WeatherTestData {
    public static final WeatherItem WEATHER_TEST_ITEM =
            new WeatherItem(new FactWeather(0, -4, "overcast", 3),
                            new Forecast("07:41", "16:44", new Part[]
                                    {
                                            new Part("day", 0, 1, 3, "overcast"),
                                            new Part("evening", 0, 1, 3, "overcast")
                                    }));

}
