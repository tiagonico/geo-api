package com.tiago.geoapi.dto;

public record GeolocationResponse(
        double latitude,
        double longitude,
        String uf,
        String estado
) {
}