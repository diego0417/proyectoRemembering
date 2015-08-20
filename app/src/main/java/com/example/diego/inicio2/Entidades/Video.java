package com.example.diego.inicio2.Entidades;

import java.util.Date;

/**
 * Created by maxim on 11/08/2015.
 */
public class Video {

    private int idVideo;
    private Usuario Usuario;
    private Date fechaSubida;
    private Date fechaDesbloqueo;
    private Permiso permiso;
    private Ubicacion ubicacion;
    private String titulo;
    private String descripcion;

    public Video(int idVideo, Usuario usuario, Date fechaSubida, Date fechaDesbloqueo, Permiso permiso, Ubicacion ubicacion, String titulo, String descripcion) {
        this.idVideo = idVideo;
        Usuario = usuario;
        this.fechaSubida = fechaSubida;
        this.fechaDesbloqueo = fechaDesbloqueo;
        this.permiso = permiso;
        this.ubicacion = ubicacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Video(){}



    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario usuario) {
        Usuario = usuario;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Date getFechaDesbloqueo() {
        return fechaDesbloqueo;
    }

    public void setFechaDesbloqueo(Date fechaDesbloqueo) {
        this.fechaDesbloqueo = fechaDesbloqueo;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
