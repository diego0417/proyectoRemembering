package com.example.diego.inicio2.Manejadores;

import android.util.Log;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;
import com.example.diego.inicio2.Entidades.Ubicacion;
import com.example.diego.inicio2.Entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    static public Ubicacion ubicacionIdVideo(int id)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call getUbicacion(" + id + ");");
        request.getServerData();
        Ubicacion ubicacion = null;
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                ubicacion = armarUbicacion(data);
            } catch (Exception e) {
                ubicacion = null;
            }

        }
        return ubicacion;
    }

    static public Ubicacion armarUbicacion(JSONObject data) throws JSONException {
        int id_ubicacion = Integer.parseInt(data.getString("ID_UBICACION"));
        double latitud = Double.parseDouble(data.getString("LATITUD"));
        double longuitud = Double.parseDouble(data.getString("LONGUITUD"));
        return new Ubicacion(latitud,longuitud,id_ubicacion);
    }
}
