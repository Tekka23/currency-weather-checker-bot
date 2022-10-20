package com.tekka.myfirstbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tekka.myfirstbot.service.exceptions.GeneralException;
import com.tekka.myfirstbot.service.exceptions.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Component
public class HttpWrapper {
    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(Duration.ofMinutes(1))
            .build();
    private final ObjectMapper mapper;
    @Autowired
    public HttpWrapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T getFromUrl(String url, Class<T> clazz, String headerName, String headerValue) {
        try {
            HttpResponse<String> response = getResponseFromUrl(url, HttpResponse.BodyHandlers.ofString(), headerName, headerValue);
            if (response.statusCode() != HttpStatus.OK.value()) {
                log.warn("Unable to get data rom url");
                throw new HttpException("Could not retrieve response from " + url + ", status code is " + response.statusCode());
            }
            log.info(response.body());
            return mapper.readValue(response.body(), clazz);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    public <T> HttpResponse<T> getResponseFromUrl(String url, HttpResponse.BodyHandler<T> bodyHandler, String headerName, String headerValue) throws Exception {
        log.info(url);
        HttpRequest request;
        if (headerName.isEmpty() || headerValue.isEmpty()) {
             request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
        }
        else {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header(headerName, headerValue)
                    .GET()
                    .build();
        }
        return httpClient.send(request, bodyHandler);
    }
}
