package com.tiago.geoapi.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiago.geoapi.model.Estado;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class GeolocationConfig {

    @Bean
    public List<Estado> estados() throws IOException {

        InputStream inputStream =
                getClass()
                        .getResourceAsStream(
                                "/geolocation/estados.geojson"
                        );

        if (inputStream == null) {
            throw new IllegalStateException(
                    "Arquivo estados.geojson não encontrado."
            );
        }

        String geoJson = new String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8
        );

        ObjectMapper objectMapper =
                new ObjectMapper();

        JsonNode root =
                objectMapper.readTree(geoJson);

        JsonNode features =
                root.get("features");

        if (features == null || !features.isArray()) {
            throw new IllegalStateException(
                    "GeoJSON não possui uma lista de features válida."
            );
        }

        GeoJsonReader geoJsonReader =
                new GeoJsonReader();

        List<Estado> estados =
                new ArrayList<>();

        for (JsonNode feature : features) {

            JsonNode properties =
                    feature.get("properties");

            JsonNode geometryNode =
                    feature.get("geometry");

            String uf =
                    properties
                            .get("SIGLA_UF")
                            .asText();

            String nome =
                    properties
                            .get("NM_UF")
                            .asText();

            String geometryJson =
                    geometryNode.toString();

            try {

                Geometry geometria =
                        geoJsonReader.read(
                                geometryJson
                        );

                Estado estado =
                        new Estado(
                                uf,
                                nome,
                                geometria
                        );

                estados.add(estado);

            } catch (ParseException e) {

                throw new IllegalStateException(
                        "Erro ao interpretar a geometria da UF: "
                                + uf,
                        e
                );
            }
        }

        System.out.println(
                "Estados carregados: "
                        + estados.size()
        );

        return estados;
    }
}