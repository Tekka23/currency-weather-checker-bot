package com.tekka.myfirstbot.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tekka.myfirstbot.model.currency.CurrencyCourseList;
import com.tekka.myfirstbot.model.weather.City;
import com.tekka.myfirstbot.model.weather.WeatherItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.tekka.myfirstbot.service.Data.WeatherTestData.WEATHER_TEST_ITEM;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyParserTest {
    private ResponseBodyParser underTest;
    @BeforeEach
    void setUp(){
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectMapper xmlMapper = new XmlMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.underTest = new ResponseBodyParser(objectMapper,(XmlMapper) xmlMapper);
    }

//        Using ISO-8859 because xml files does not supp UTF-8 or UTF-16 (file encoding - windows-1251)
//        So we either should to set CodingErrorAction.REPLACE or change encoding

    @Test
    void isShouldParseToCurrencyObj() throws IOException {
        String content = Files.readString(Path.of("src/main/resources/tests/testCurrency.xml"),
                StandardCharsets.ISO_8859_1);
        CurrencyCourseList example = new CurrencyCourseList();
        example.setDate("15.10.2022");
        example.setDate("Foreign Currency Market");

        CurrencyCourseList actual = underTest.parse(content, CurrencyCourseList.class);
        assertThat(actual)
                .isNotNull();
        assertThat(actual.getDate())
                .isEqualTo("15.10.2022");
        assertThat(actual.getName())
                .isEqualTo("Foreign Currency Market");
    }

    // ...Test for getting whole weather obj without problems
    @Test
    void isShouldParseToWeatherObj() throws IOException {
        String content = Files.readString(Path.of("src/main/resources/tests/testWeather.json"),
                StandardCharsets.ISO_8859_1);

        WeatherItem weatherItem = underTest.parse(content, WeatherItem.class);
        assertThat(weatherItem).isNotNull().isEqualTo(WEATHER_TEST_ITEM).usingRecursiveComparison();

    }
    // ... Test for getting geocode of city without problems
    @Test
    void isShouldParseToCityObj() throws IOException {
        String content = Files.readString(Path.of("src/main/resources/tests/citiesTest.json"),
                StandardCharsets.ISO_8859_1);
        City example = new City("Moscow", 55.7504461, 37.6174943, "RU","Moscow");
        City actual = underTest.parse(content, City[].class)[0];

        assertThat(actual)
                .isNotNull()
                .isEqualTo(example)
                .usingRecursiveComparison();
    }
}