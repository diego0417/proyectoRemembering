package com.example.diego.inicio2.vistas.amigos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.GPS;
import com.example.diego.inicio2.Conexion.ImageLoad;
import com.example.diego.inicio2.Entidades.Permiso;
import com.example.diego.inicio2.Entidades.Ubicacion;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Entidades.Video;
import com.example.diego.inicio2.Manejadores.ManejadorUbicacion;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.Manejadores.ManejadorVideo;
import com.example.diego.inicio2.R;
import com.example.diego.inicio2.vistas.PerfilAmigo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by diego on 20/08/2015.
 */
public class Solicitudes extends Fragment {
    View rootView;
    public ArrayList<String> url = new ArrayList<String>();
    private ArrayList<Usuario> lista = new ArrayList<Usuario>();
    ListView lv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lay_lv_solicitud_amistad, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        lv = (ListView) rootView.findViewById(R.id.lv_solicitud_amistad);

        cargarSolicitudes();
        cargarvistas();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                intent = new Intent(rootView.getContext(), PerfilAmigo.class);
                intent.putExtra("idAmigo", lista.get(position).getIdUsuario());
                startActivity(intent);
            }
        });



        return rootView;
    }

    private void cargarSolicitudes()
    {
        lista = ManejadorUsuario.getMisSolicitudes();
    }

    public void cargarvistas()
    {
        ArrayAdapter<Usuario> saveAdapter = new MyAdapter();
        lv.setAdapter(saveAdapter);
    }


    private class MyAdapter extends ArrayAdapter<Usuario>
    {
        public MyAdapter()
        {
            super(getActivity(),R.layout.lay_amigo_solicitud,lista);
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent)
        {
            View itenview = convertView;
            if(itenview==null)
            {
                itenview = getActivity().getLayoutInflater().inflate(R.layout.lay_amigo_solicitud, parent, false);
            }

            final Usuario usuarioActual =lista.get(position);

            ImageView img2 = (ImageView)itenview.findViewById(R.id.contactos_foto_perfil);
            new ImageLoad(Conexion.MI_IP+"FotosPerfil/"+usuarioActual.getIdUsuario()+".jpg", img2).execute();

            TextView texto = (TextView) itenview.findViewById(R.id.amigos_solicitud_nombre);
            texto.setText(usuarioActual.getNombre() + " " + usuarioActual.getApellido());

            Button btnAceptar = (Button)itenview.findViewById(R.id.amigos_solicitud_btnAceptar);
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManejadorUsuario.aceptarSolicitud(usuarioActual.getIdUsuario());
                    cargarSolicitudes();
                    cargarvistas();
                }
            });

            Button btnCancelar = (Button)itenview.findViewById(R.id.amigos_solicitud_btnNegar);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManejadorUsuario.cancelarSolicitud(usuarioActual.getIdUsuario());
                    cargarSolicitudes();
                    cargarvistas();
                }
            });

            return itenview;
        }
    }
}
