package com.example.diego.inicio2.vistas;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.diego.inicio2.Conexion.GPS;
import com.example.diego.inicio2.Entidades.Permiso;
import com.example.diego.inicio2.Entidades.Ubicacion;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Entidades.Video;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorSugerencia;
import com.example.diego.inicio2.Manejadores.ManejadorUbicacion;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.Manejadores.ManejadorVideo;
import com.example.diego.inicio2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diego on 04/09/2015.
 */
public class Sugerencia extends Fragment {
    Button listo;
    EditText sugerencia;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lay_sugerencia, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listo = (Button)rootView.findViewById(R.id.btnTerminar_Sugerencia);
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sugerencia = (EditText)rootView.findViewById(R.id.txtSugerencia);
                ManejadorSugerencia.insertarUsuario(sugerencia.getText().toString());
                Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
