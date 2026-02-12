package com.challenge.Literatura.Servicios;

public interface IConversionDatos {
    <T> T convertirDatos(String json, Class<T> clase);
}
