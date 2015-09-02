package com.example.diego.inicio2.Manejadores;



import android.net.ParseException;
import android.util.Log;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Entidades.Video;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Matias Sosa on 8/13/2015.
 */
public class ManejadorUsuario {

    private static MYSQL_Request request = Conexion.nuevaConexion();
    public static Usuario usuario = null;



    static public int cuentaSolicitudes()
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call cuentaSolicitudes("+ManejadorUsuario.usuario.getIdUsuario()+")");
        request.getServerData();
        int cant = 0;
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                cant = armarCantidad(data);
            } catch (Exception e) {
            }
        }
        return cant;
    }



    static private int armarCantidad(JSONObject data) throws JSONException {

        int cant = Integer.parseInt(data.getString("cant"));

        return cant;
    }




    static public Boolean login(String mailEnviar,String passEnviar)
    {
        Boolean res = false;
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("SELECT * FROM usuario WHERE mail='"+mailEnviar+"'and pass='"+passEnviar+"';");
        request.getServerData();
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            res = true;
            try {
                usuario = armarUsuario(data);
            } catch (Exception e) {
            }
        }
        return res;
    }

    static public ArrayList<Usuario> getMisAmigos()
    {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call misAmigos("+ManejadorUsuario.usuario.getIdUsuario()+");");
        request.getServerData();
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                lista.add(armarAmigos(data));
            } catch (Exception e) {}
        }
        return lista;
    }

    static public ArrayList<Usuario> getBuscarAmigos(String buscar)
    {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call buscarAmigos('"+buscar+"');");
        request.getServerData();
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                lista.add(armarAmigos(data));
            } catch (Exception e) {}
        }
        return lista;
    }

    static public ArrayList<Usuario> getMisSolicitudes()
    {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("call misSolicitudes("+ManejadorUsuario.usuario.getIdUsuario()+");");
        request.getServerData();
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                lista.add(armarAmigos(data));
            } catch (Exception e) {}
        }
        return lista;
    }

    static public Usuario usuarioId(int id)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("SELECT * FROM usuario WHERE id_usuario='"+id+"';");
        request.getServerData();
        Usuario usuario = null;
        while (request.getNextEntry()) {
            JSONObject data = request.getJsonValue();
            try {
                usuario = armarUsuario(data);
            } catch (Exception e) {
            }
        }
        return usuario;
    }

    static private Usuario armarUsuario(JSONObject data) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatter.parse(data.getString("FECHA"));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        String nombre = data.getString("NOMBRE");
        String apellido = data.getString("APELLIDO");
        String mail = data.getString("MAIL");
        int id = Integer.parseInt(data.getString("ID_USUARIO"));
        Boolean sexo = Boolean.parseBoolean(data.getString("SEXO"));
        String pass = data.getString("PASS");
        return new Usuario(id,mail,nombre,apellido,fecha,sexo,pass);
    }

    static private Usuario armarAmigos(JSONObject data) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formatter.parse(data.getString("FECHA"));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String nombre = data.getString("NOMBRE");
        String apellido = data.getString("APELLIDO");
        String mail = data.getString("MAIL");
        int id = Integer.parseInt(data.getString("ID_USUARIO"));
        Boolean sexo = Boolean.parseBoolean(data.getString("SEXO"));
        return new Usuario(id,mail,nombre,apellido,fecha,sexo,"");
    }


    static public Boolean insertarUsuario(String nombre,String apellido,String mail,String pass, Boolean sexo,String fecha)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("INSERT INTO usuario (nombre,apellido,mail,pass,sexo,fecha) VALUES ('"+nombre+"','"+apellido+"','"+mail+"','"+pass+"',"+sexo+",'"+fecha+"');");

        request.executeRequestWithID();

        int id = Integer.parseInt(request.getResultID());
        if(id==0)
        {
            return false;
        }else
        {
            return true;
        }
    }

    static public Boolean aceptarSolicitud(int id)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("ESTADO", "1");
        Boolean res= true;
        try
        {
            request.setRequestUpdate("amigos",values,"ID_USUARIO_PEDIDO = '"+id+"' and ID_USUARIO_RES="+ManejadorUsuario.usuario.getIdUsuario());
            request.executeRequest();
        }catch (Exception e){
            res = false;
        }
        return res;
    }

    static public Boolean cancelarSolicitud(int id)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        HashMap<String, String> values = new HashMap<String, String>();

        values.put("ESTADO", "2");
        Boolean res= true;
        try
        {
            request.setRequestUpdate("amigos",values,"ID_USUARIO_PEDIDO = '"+id+"' and ID_USUARIO_RES="+ManejadorUsuario.usuario.getIdUsuario());
            request.executeRequest();
        }catch (Exception e){
            res = false;
        }
        return res;
    }

}
