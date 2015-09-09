package com.example.diego.inicio2.vistas;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.ImageLoad;
import com.example.diego.inicio2.Entidades.Video;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.Manejadores.ManejadorVideo;
import com.example.diego.inicio2.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by diego on 8/13/2015.
 */
public class Inicio extends Fragment {
    public ArrayList<String> url = new ArrayList<String>();
    private ArrayList<Video> lista = new ArrayList<Video>();
    ListView lv;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lv_inicion, container, false);
        // PANTALLA EN VERTICAL
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        lv = (ListView) rootView.findViewById(R.id.lv);

        cargarVideos();
        cargarvistas();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                intent = new Intent(rootView.getContext(), ReproducirVideo.class);
                intent.putExtra("url", url.get(position));
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void cargarVideos()
    {
        //Log.i("saf", "PUTAAA funcion cargar video");
        lista = ManejadorVideo.getVideosInicio();
        //Log.i("saf", "PUTAAA termine funcion cargar video" + lista.toString());
        //lista.add(new Video(1,null,null,null,null,null,"hola","hola"));
        //lista.add(new Video(1,null,null,null,null,null,"hola2","hola2"));
    }

    public void cargarvistas()
    {
        ArrayAdapter<Video> saveAdapter = new MyAdapter();
        lv.setAdapter(saveAdapter);
        //ArrayAdapter<Video> aa = new MyAdapter();
        //lv = (ListView) (rootView).findViewById(R.id.lv);
        //lv.setAdapter(aa);
    }


    private class MyAdapter extends ArrayAdapter<Video>
    {
        public MyAdapter()
        {
            super(getActivity(),R.layout.lay_inicio,lista);
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent)
        {
            View itenview = convertView;
            if(itenview==null)
            {
                itenview = getActivity().getLayoutInflater().inflate(R.layout.lay_inicio, parent, false);
            }

            final Video videoActual =lista.get(position);
            url.add(Conexion.MI_IP + "Video/" + videoActual.getIdVideo() + ".mp4");
            ImageView img = (ImageView)itenview.findViewById(R.id.inicio_foto_perfil);
            new ImageLoad(Conexion.MI_IP+"FotosPerfil/"+videoActual.getUsuario().getIdUsuario()+".jpg", img).execute();

            ImageView img2 = (ImageView)itenview.findViewById(R.id.inicio_imagenVideo);
            new ImageLoad(Conexion.MI_IP+"VideoMin/"+videoActual.getIdVideo()+".jpg", img2).execute();

            TextView texto = (TextView) itenview.findViewById(R.id.inicio_descripcion_descripcion);
            texto.setText(videoActual.getDescripcion());

            TextView texto1 = (TextView) itenview.findViewById(R.id.inicio_descripcion_titulo);
            texto1.setText(videoActual.getTitulo());

            TextView texto2 = (TextView) itenview.findViewById(R.id.inicio_perfil_nombre);
            texto2.setText(videoActual.getUsuario().getNombre());

            TextView texto3 = (TextView) itenview.findViewById(R.id.inicio_perfil_fecha);

            Date actual = new java.util.Date();
            Date deVideo = videoActual.getFechaDesbloqueo();
            texto3.setText(calculaTiempo(actual, deVideo));

            TextView btnCancelar = (TextView)itenview.findViewById(R.id.inicio_perfil_ubicacion);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    intent = new Intent(rootView.getContext(), ActivityUbicacion.class);
                    Log.e("asdasd","TUTU"+videoActual.getTitulo());
                    Log.e("asdasd","TUTU"+Double.toString(videoActual.getUbicacion().getLatitud()));
                    Log.e("asdasd","TUTU"+Double.toString(videoActual.getUbicacion().getLonguitud()));
                    intent.putExtra("titulo", videoActual.getTitulo());
                    intent.putExtra("latitud", Double.toString(videoActual.getUbicacion().getLatitud()));
                    intent.putExtra("longitud", Double.toString(videoActual.getUbicacion().getLonguitud()));
                    startActivity(intent);
                }
            });

            /*
            //PROBANDO SIN CONEXION
            TextView texto1 = (TextView) itenview.findViewById(R.id.inicio_descripcion_titulo);
            texto1.setText(videoActual.getTitulo());*/

            return itenview;

        }
    }


    /*private String calculaTiempo(Date actual,Date vieja){

        if(vieja == null){
            return "???";
        }

        //los milisegundos
        long diferenciaMils = actual.getTime() - vieja.getTime();

        //obtenemos los segundos
        long segundos = diferenciaMils / 1000;

        if(segundos>50){
            //obtenemos los minutos
            long minutos = segundos /60;
            if(minutos>58){
                //obtenemos las horas
                long horas = minutos / 60;
                if(minutos> 1390){
                    //obtenemos las dias
                    long dias = horas / 24;
                    if(dias>29){
                        //obtenemos los mes

                        return "very old";
                    }else
                    {
                        return Long.toString(dias)+" dias";
                    }

                }else {
                    return Long.toString(horas)+" hs";
                }
            }else{
                return Long.toString(minutos)+" min";
            }
        }else
        {
            return Long.toString(segundos)+" seg";
        }

    }*/
    private String calculaTiempo(Date actual,Date vieja){

        if(vieja == null){
            return "???";
        }

        //los milisegundos
        long diferenciaMils = actual.getTime() - vieja.getTime();

        //obtenemos los segundos
        long segundos = diferenciaMils / 1000;

        if(segundos>50){
            //obtenemos los minutos
            long minutos = segundos /60;
            if(minutos>58){
                //obtenemos las horas
                long horas = minutos / 60;
                if(minutos> 1390){
                    //obtenemos las dias
                    long dias = horas / 24;
                    if(dias>29){
                        //obtenemos los mes

                        return "very old";
                    }else
                    {
                        return Long.toString(dias)+" dias";
                    }

                }else {
                    return "Hoy";
                }
            }else{
                return "Hoy";
            }
        }else
        {
            return "Hoy";
        }

    }

}
