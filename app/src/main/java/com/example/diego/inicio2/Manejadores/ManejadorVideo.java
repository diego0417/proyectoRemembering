package com.example.diego.inicio2.Manejadores;

import android.util.Log;


import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Entidades.Video;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matias Sosa on 8/14/2015.
 */
public class ManejadorVideo {
    private static MYSQL_Request request = Conexion.nuevaConexion();
    private static ArrayList<Video> lista = new ArrayList<Video>();

    static public ArrayList<Video> getAllVideos()
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("SELECT * FROM video;");
        request.getServerData();
        Usuario usuario = null;
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            Log.i("saf","PUTAAA"+data.toString());
            try {
                lista.add(armarVideo(data));
            } catch (Exception e) {
            }
        }
        return lista;
    }


    static private Video armarVideo(JSONObject data) throws JSONException {
        String titulo = data.getString("TITULO");
        String descripcion = data.getString("DESCRIPCION");
        int idVideo = Integer.parseInt(data.getString("ID_VIDEO"));
        Usuario usuario = ManejadorUsuario.clienteId(Integer.parseInt(data.getString("ID_USUARIO")));

        return new Video(idVideo,usuario,null,null,null,null,titulo,descripcion);
    }
}
