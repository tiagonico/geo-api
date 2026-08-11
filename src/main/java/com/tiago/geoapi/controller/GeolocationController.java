package com.tiago.geoapi.controller;

import com.tiago.geoapi.dto.GeolocationNotFoundResponse;
import com.tiago.geoapi.dto.GeolocationResponse;
import com.tiago.geoapi.model.Estado;
import com.tiago.geoapi.service.GeolocationService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geolocation")
@Validated
public class GeolocationController {

    private final GeolocationService service;

    public GeolocationController(
            GeolocationService service
    ) {
        this.service = service;
    }

    @GetMapping("/uf")
    public ResponseEntity<?> obterUF(

            @RequestParam
            @DecimalMin(
                    value = "-90.0",
                    message = "Latitude deve ser maior ou igual a -90"
            )
            @DecimalMax(
                    value = "90.0",
                    message = "Latitude deve ser menor ou igual a 90"
            )
            double latitude,

            @RequestParam
            @DecimalMin(
                    value = "-180.0",
                    message = "Longitude deve ser maior ou igual a -180"
            )
            @DecimalMax(
                    value = "180.0",
                    message = "Longitude deve ser menor ou igual a 180"
            )
            double longitude

    ) {

        Estado estado =
                service.encontrarEstado(
                        latitude,
                        longitude
                );

        if (estado == null) {

            return ResponseEntity
                    .status(404)
                    .body(
                            new GeolocationNotFoundResponse(
                                    "A coordenada informada não pertence ao território brasileiro."
                            )
                    );
        }

        return ResponseEntity.ok(
                new GeolocationResponse(
                        estado.getUf(),
                        estado.getNome()
                )
        );
    }
}