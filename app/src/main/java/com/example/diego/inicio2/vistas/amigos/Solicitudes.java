package com.example.diego.inicio2.vistas.amigos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.inicio2.R;

/**
 * Created by diego on 20/08/2015.
 */
public class Solicitudes extends Fragment {
    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.lay_amigo_solicitud, container, false);
        return rootView;
    }

}
