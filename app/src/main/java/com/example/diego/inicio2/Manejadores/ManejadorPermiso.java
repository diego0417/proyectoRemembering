package com.example.diego.inicio2.Manejadores;

import android.util.Log;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;
import com.example.diego.inicio2.Entidades.Permiso;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxim on 31/08/2015.
 */
public class ManejadorPermiso {
    private static MYSQL_Request request = Conexion.nuevaConexion();

    static public ArrayList<Permiso> getAll()
    {
        ArrayList<Permiso> lista = new ArrayList<Permiso>();
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("SELECT * FROM permiso;");
        request.getServerData();
        Permiso permiso = null;
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                lista.add(armarPermiso(data));
            } catch (Exception e) {
            }
        }
        return lista;
    }

    static private Permiso armarPermiso(JSONObject data) throws JSONException {
        String descripcion = data.getString("DESCRIPCION");
        String descripcion_corta = data.getString("DESCRIPCION_CORTA");
        int idPermiso = Integer.parseInt(data.getString("ID_PERMISO"));
        return new Permiso(idPermiso,descripcion,descripcion_corta);
    }

}
