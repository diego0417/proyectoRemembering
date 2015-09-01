package com.example.diego.inicio2.Entidades;

/**
 * Created by maxim on 12/08/2015.
 */
public class Permiso {
    public Permiso(int idPermiso, String descripcion, String descripcionCorta) {
        this.setIdPermiso(idPermiso);
        this.setDescripcion(descripcion);
        this.setDescripcionCorta(descripcionCorta);
    }

    private int idPermiso;
    private String descripcion;
    private String descripcionCorta;

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    @Override
    public String toString() {
        return this.getDescripcionCorta();  }
}
