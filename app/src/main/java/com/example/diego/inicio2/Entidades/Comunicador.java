package com.example.diego.inicio2.Entidades;

/**
 * Created by Matias Sosa on 8/14/2015.
 */
public class Comunicador {
    private static Object objeto = null;

    public static Object getObjeto(){
        return objeto;
    }

    public static void setObjeto(Object newObjeto){
        objeto=newObjeto;
    }
}
