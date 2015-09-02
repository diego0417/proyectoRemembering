package com.example.diego.inicio2.vistas.amigos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.ImageLoad;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.R;

import java.util.ArrayList;

/**
 * Created by diego on 20/08/2015.
 */
public class AgregarAmigo extends Fragment{
    private View rootView;
    public ArrayList<String> url = new ArrayList<String>();
    private ArrayList<Usuario> lista = new ArrayList<Usuario>();
    private ListView lv;
    private EditText buscar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lay_amigo_agregar, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        lv = (ListView) rootView.findViewById(R.id.lv_buscar_amigos);

        cargarAmigos();
        cargarvistas();

        buscar = (EditText)rootView.findViewById(R.id.amigos_buscar_tbBuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lv.setAdapter(null);
                lista = ManejadorUsuario.getBuscarAmigos(buscar.getText().toString());
                cargarvistas();
            }
        });

        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                intent = new Intent(rootView.getContext(), ReproducirVideo.class);

                intent.putExtra("url", url.get(position));
                startActivity(intent);
            }
        });*/

        return rootView;
    }

    private void cargarAmigos()
    {
        //Log.i("saf", "PUTAAA funcion cargar video");
        lista = ManejadorUsuario.getMisAmigos();
        //Log.i("saf", "PUTAAA termine funcion cargar video" + lista.toString());
        //lista.add(new Video(1,null,null,null,null,null,"hola","hola"));
        //lista.add(new Video(1,null,null,null,null,null,"hola2","hola2"));
    }

    public void cargarvistas()
    {
        ArrayAdapter<Usuario> saveAdapter = new MyAdapter();
        lv.setAdapter(saveAdapter);
        //ArrayAdapter<Video> aa = new MyAdapter();
        //lv = (ListView) (rootView).findViewById(R.id.lv);
        //lv.setAdapter(aa);
    }


    private class MyAdapter extends ArrayAdapter<Usuario>
    {
        public MyAdapter()
        {
            super(getActivity(),R.layout.lay_amigo_amigos,lista);
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent)
        {
            View itenview = convertView;
            if(itenview==null)
            {
                itenview = getActivity().getLayoutInflater().inflate(R.layout.lay_amigo_amigos, parent, false);
            }

            Usuario usuarioActual =lista.get(position);

            ImageView img2 = (ImageView)itenview.findViewById(R.id.contactos_foto_perfil);
            new ImageLoad(Conexion.MI_IP+"FotosPerfil/"+usuarioActual.getIdUsuario()+".jpg", img2).execute();

            TextView texto = (TextView) itenview.findViewById(R.id.contactos_perfil_nombre);
            texto.setText(usuarioActual.getNombre()+" "+usuarioActual.getApellido());

            /*
            //PROBANDO SIN CONEXION
            TextView texto1 = (TextView) itenview.findViewById(R.id.inicio_descripcion_titulo);
            texto1.setText(videoActual.getTitulo());*/

            return itenview;
        }
    }

}
