package com.tiago.geoapi.controller;

import com.tiago.geoapi.dto.GeolocationResponse;
import com.tiago.geoapi.model.Estado;
import com.tiago.geoapi.service.GeolocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geolocation")
public class GeolocationController {

    private final GeolocationService service;

    public GeolocationController(
            GeolocationService service
    ) {
        this.service = service;
    }

    @GetMapping("/uf")
    public ResponseEntity<GeolocationResponse> obterUF(
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {

        Estado estado =
                service.encontrarEstado(
                        latitude,
                        longitude
                );

        if (estado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                new GeolocationResponse(
                        latitude,
                        longitude,
                        estado.getUf(),
                        estado.getNome()
                )
        );
    }
}