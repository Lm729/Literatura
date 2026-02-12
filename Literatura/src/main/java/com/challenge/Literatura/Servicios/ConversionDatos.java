package com.challenge.Literatura.Servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionDatos implements IConversionDatos{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convertirDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
