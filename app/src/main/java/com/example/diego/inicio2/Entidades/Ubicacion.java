package com.example.diego.inicio2.Entidades;

/**
 * Created by maxim on 12/08/2015.
 */
public class Ubicacion {
    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    private int idUbicacion;
    private double latitud;


    public Ubicacion(double latitud, double longuitud,int idUbicacion) {
        this.latitud = latitud;
        this.longuitud = longuitud;
        this.idUbicacion=idUbicacion;
    }

    private double longuitud;

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLonguitud() {
        return longuitud;
    }

    public void setLonguitud(double longuitud) {
        this.longuitud = longuitud;
    }
}
