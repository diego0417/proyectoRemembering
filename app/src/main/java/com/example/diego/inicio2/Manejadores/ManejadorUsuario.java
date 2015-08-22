package com.example.diego.inicio2.Manejadores;



import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;
import com.example.diego.inicio2.Entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Matias Sosa on 8/13/2015.
 */
public class ManejadorUsuario {

    private static MYSQL_Request request = Conexion.nuevaConexion();
    public static Usuario usuario = null;

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

    static public Usuario clienteId(int id)
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
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = null;
        //try {
          //  fecha = formatter.parse(data.getString("FECHA"));
        //} catch (ParseException e) {
          //  e.printStackTrace();
        //}

        String nombre = data.getString("NOMBRE");
        String apellido = data.getString("APELLIDO");
        String mail = data.getString("MAIL");
        int id = Integer.parseInt(data.getString("ID_USUARIO"));
        Boolean sexo = Boolean.parseBoolean(data.getString("SEXO"));
        String pass = data.getString("PASS");
        return new Usuario(id,mail,nombre,apellido,fecha,sexo,pass);
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

}
