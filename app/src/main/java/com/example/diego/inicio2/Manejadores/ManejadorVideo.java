package com.example.diego.inicio2.Manejadores;

import android.util.Log;


import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.GPS;
import com.example.diego.inicio2.Conexion.MYSQL_Request;
import com.example.diego.inicio2.Entidades.Ubicacion;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Entidades.Video;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matias Sosa on 8/14/2015.
 */
public class ManejadorVideo {
    private static MYSQL_Request request = Conexion.nuevaConexion();


    static public ArrayList<Video> getAllVideos()
    {
        ArrayList<Video> lista = new ArrayList<Video>();
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

    static public ArrayList<Video> getVideosInicio()
    {
        ArrayList<Video> lista = new ArrayList<Video>();
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call videosInicio("+ManejadorUsuario.usuario.getIdUsuario()+");");
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


    static public ArrayList<Video> getMisVideos()
    {
        ArrayList<Video> lista = new ArrayList<Video>();
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call misVideos("+ManejadorUsuario.usuario.getIdUsuario()+");");
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

    static public Boolean descripcionVideo(Video video)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        HashMap<String, String> values = new HashMap<String, String>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        values.put("FECHA_DESBLOQUEO", format.format(video.getFechaDesbloqueo()));
        values.put("ID_PERMISO", Integer.toString(video.getPermiso().getIdPermiso()));
        values.put("ID_UBICACION", Integer.toString(video.getUbicacion().getIdUbicacion()));
        values.put("TITULO", video.getTitulo());
        values.put("DESCRIPCION", video.getDescripcion());
        Boolean res= true;
        try
        {
            request.setRequestUpdate("video",values,"ID_VIDEO = '"+video.getIdVideo()+"'");
            request.executeRequest();
        }catch (Exception e){
            res = false;
        }
        return res;
    }

    static private Video armarVideo(JSONObject data) throws JSONException {
        String titulo = data.getString("TITULO");
        String descripcion = data.getString("DESCRIPCION");
        int idVideo = Integer.parseInt(data.getString("ID_VIDEO"));
        Usuario usuario = ManejadorUsuario.clienteId(Integer.parseInt(data.getString("ID_USUARIO")));
        return new Video(idVideo,usuario,null,null,null,null,titulo,descripcion);
    }
}
