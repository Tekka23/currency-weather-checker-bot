package com.tekka.myfirstbot.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HttpWrapperTest {
    @Mock
    private ResponseBodyParser responseBodyParser;
    @InjectMocks
    private HttpWrapper underTest;

    @ParameterizedTest
    @MethodSource("getArgumentsForWrapperTest")
     void isShouldGetDataObjectFromUrl(String url,
                                      String headerName,
                                      String headerValue, String fileExample) throws IOException {
        String example = Files.readString(Path.of(fileExample), Charset.forName("windows-1251"));
        String actual = underTest.getFromUrl(url, headerName, headerValue);

        assertThat(actual)
                .isEqualTo(example);
    }

    static Stream<Arguments> getArgumentsForWrapperTest(){
        return Stream.of(
                Arguments.of("https://cbr.ru/scripts/XML_daily.asp?date_req=15.10.2022","", "","src/main/resources/tests/testCurrency.xml"),
                Arguments.of("https://api.api-ninjas.com/v1/geocoding?city=Moscow","X-Api-Key", "r9pM0DNVQQKiqdrKfIzBgA==BkRn60tDYzyTVxmn", "src/main/resources/tests/citiesTest.json")
        );
    }
}