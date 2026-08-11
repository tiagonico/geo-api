package com.tiago.geoapi.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record GeolocationRequest(

        @NotNull
        @DecimalMin(value = "-90.0", message = "Latitude deve ser maior ou igual a -90")
        @DecimalMax(value = "90.0", message = "Latitude deve ser menor ou igual a 90")
        Double latitude,

        @NotNull
        @DecimalMin(value = "-180.0", message = "Longitude deve ser maior ou igual a -180")
        @DecimalMax(value = "180.0", message = "Longitude deve ser menor ou igual a 180")
        Double longitude

) {
}