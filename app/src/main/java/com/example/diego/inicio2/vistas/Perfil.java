package com.example.diego.inicio2.vistas;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.ImageLoad;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.R;

/**
 * Created by diego on 8/14/2015.
 */
public class Perfil extends Fragment {
    TextView mail;
    TextView nombreCompleto;
    TextView sexo;
    TextView fecha;
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_perfil, container, false);

        Drawable originalDrawable = getResources().getDrawable(R.drawable.pipi);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, 1000, 1000);

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getWidth());

        imageView = (ImageView)(rootView).findViewById(R.id.imgUsuario_MiPerfil);

        imageView.setImageDrawable(roundedDrawable);

        return rootView;
    }
}
