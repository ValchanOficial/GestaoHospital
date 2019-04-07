package br.com.codenation.hospital.domain;

public class LocationBuilder {
    private Double lat;
    private Double lon;
    private String name;

    public LocationBuilder setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public LocationBuilder setLon(Double lon) {
        this.lon = lon;
        return this;
    }

    public LocationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Location build() {
        return new Location(lat, lon, name);
    }
}