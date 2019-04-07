package br.com.codenation.hospital.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Location {

    @Id
    private String id;

    private Double lat;

    private Double lon;

    private String name;

    public Location(){}

    public Location(Double lat, Double lon, String name) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
