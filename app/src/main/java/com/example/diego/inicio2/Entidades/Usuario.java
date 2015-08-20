package com.example.diego.inicio2.Entidades;

import java.util.Date;

/**
 * Created by maxim on 11/08/2015.
 */
public class Usuario{

    private int idUsuario;
    private String mail;
    private String nombre;
    private String apellido;
    private Date fecha;
    private Boolean sexo;
    private String pass;

    public Usuario(int idUsuario, String mail, String nombre, String apellido, Date fecha, Boolean sexo, String pass)
    {
        this.idUsuario = idUsuario;
        this.mail=mail;
        this.nombre=nombre;
        this.apellido=apellido;
        this.fecha=fecha;
        this.sexo=sexo;
        this.pass=pass;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.idUsuario = id_usuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }



}
