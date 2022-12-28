package com.tekka.myfirstbot.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseBodyParser {
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    @Autowired
    public ResponseBodyParser(ObjectMapper mapper, XmlMapper xmlMapper) {
        this.objectMapper = mapper;
        this.xmlMapper = xmlMapper;
    }

    public <T> T parse(String objToParse, Class<T> clazz) throws JsonProcessingException {
        if(objToParse.startsWith("<")){
            return xmlMapper.readValue(objToParse, clazz);
        }
        else return objectMapper.readValue(objToParse, clazz);
    }

}
