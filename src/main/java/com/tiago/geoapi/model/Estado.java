package com.tiago.geoapi.model;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class Estado {

    private final String uf;
    private final String nome;
    private final Geometry geometria;
    private final Envelope envelope;

    public Estado(
            String uf,
            String nome,
            Geometry geometria
    ) {
        this.uf = uf;
        this.nome = nome;
        this.geometria = geometria;
        this.envelope =
                geometria.getEnvelopeInternal();
    }

    public String getUf() {
        return uf;
    }

    public String getNome() {
        return nome;
    }

    public Geometry getGeometria() {
        return geometria;
    }

    public Envelope getEnvelope() {
        return envelope;
    }
}