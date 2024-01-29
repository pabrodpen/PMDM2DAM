package com.example.miapppablorodriguez;

public class GeoPunto {
    private double longitud;
    private double latitud;

    public GeoPunto(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }

}
