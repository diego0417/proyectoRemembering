package com.example.diego.inicio2.Manejadores;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;

/**
 * Created by maxim on 01/09/2015.
 */
public class ManejadorUbicacion {

    static public int insertarUbicacion(double latitud,double longitud)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("INSERT INTO `ubicacion`(`LATITUD`, `LONGUITUD`) VALUES ("+latitud+","+longitud+");");
        request.executeRequestWithID();
        int id = Integer.parseInt(request.getResultID());
        return id;
    }
}
