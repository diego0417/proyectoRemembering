package com.example.diego.inicio2.vistas;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.example.diego.inicio2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityUbicacion   extends FragmentActivity {

    private LatLng marcador;
    private GoogleMap mapa;
    String titulo ="maxi";
    double latitud = -33.1506714;
    double longitud = -66.3091404;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        mapa = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        titulo = getIntent().getStringExtra("titulo");
        latitud = Double.parseDouble(getIntent().getStringExtra("latitud"));
        longitud = Double.parseDouble(getIntent().getStringExtra("longitud"));
        marcador = new LatLng(latitud, longitud);

        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 15));
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(false);
        mapa.getUiSettings().setCompassEnabled(true);
        mapa.addMarker(new MarkerOptions()
                .position(marcador)
                .title(titulo)
                .anchor(0.5f, 0.5f));
    }
}