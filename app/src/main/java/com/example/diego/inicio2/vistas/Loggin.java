package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.inicio2.Conexion.GPS;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.R;

public class Loggin extends Activity {

    TextView link;
    Button btnIniciar;
    EditText etMail;
    EditText etPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // PANTALLA EN VERTICAL
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);


        /*GPS gps = new GPS(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        */

        link = (TextView) findViewById(R.id.linkAReg_Log);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loggin.this, CrearCuenta.class);
                startActivity(intent);
            }
        });

        btnIniciar = (Button) findViewById(R.id.btnEntrar_Log);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar pro = (ProgressBar) findViewById(R.id.progressBar_Log);
                pro.setVisibility(View.VISIBLE);
                etMail = (EditText) findViewById(R.id.mail);
                etPass = (EditText) findViewById(R.id.pass);
                ManejadorUsuario.login(etMail.getText().toString(), etPass.getText().toString());
                if (ManejadorUsuario.usuario == null) {
                    Toast.makeText(Loggin.this, "El mail y/o pass son invalidos", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Loggin.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.moveTaskToBack(true);
        return true;
    }
}
