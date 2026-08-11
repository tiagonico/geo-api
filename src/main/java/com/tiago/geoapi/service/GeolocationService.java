package com.tiago.geoapi.service;

import com.tiago.geoapi.model.Estado;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeolocationService {

    private static final GeometryFactory GEOMETRY_FACTORY =
            new GeometryFactory();

    private final List<Estado> estados;

    public GeolocationService(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado encontrarEstado(
            double latitude,
            double longitude
    ) {

        Point ponto = criarPonto(
                latitude,
                longitude
        );

        for (Estado estado : estados) {

            // Primeiro verifica o Bounding Box
            if (!estado.getEnvelope()
                    .contains(longitude, latitude)) {
                continue;
            }

            // Depois verifica a geometria real
            if (estado.getGeometria()
                    .contains(ponto)) {
                return estado;
            }
        }

        return null;
    }

    private Point criarPonto(
            double latitude,
            double longitude
    ) {
        return GEOMETRY_FACTORY.createPoint(
                new Coordinate(
                        longitude,
                        latitude
                )
        );
    }
}