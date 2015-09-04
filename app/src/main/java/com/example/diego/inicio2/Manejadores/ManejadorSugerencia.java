package com.example.diego.inicio2.Manejadores;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.MYSQL_Request;

/**
 * Created by maxim on 04/09/2015.
 */
public class ManejadorSugerencia {

    static public Boolean insertarUsuario(String sugerencia)
    {
        MYSQL_Request request = Conexion.nuevaConexion();
        request.setRequest("INSERT INTO sugerencia (id_usuario,sugerencia) VALUES ("+ManejadorUsuario.usuario.getIdUsuario()+",'"+sugerencia+"');");

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
