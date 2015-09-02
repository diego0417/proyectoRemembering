package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.inicio2.Conexion.Conexion;
import com.example.diego.inicio2.Conexion.ImageLoad;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.R;

import java.text.SimpleDateFormat;

public class PerfilAmigo extends Activity {

    Button bloquear;
    Button agregar;
    Button aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amigo);

        final int idAmigo = getIntent().getExtras().getInt("idAmigo");
        Usuario amigo = ManejadorUsuario.usuarioId(idAmigo);

        ImageView img = (ImageView)findViewById(R.id.imgUsuario_PerfilAmigo);
        new ImageLoad(Conexion.MI_IP+"FotosPerfil/"+amigo.getIdUsuario()+".jpg", img).execute();

        TextView nombre = (TextView) findViewById(R.id.nombreCompleto_PerfilAmigo);
        nombre.setText(amigo.getNombre() + " " + amigo.getApellido());

        TextView mail = (TextView) findViewById(R.id.txtEmail_PerfilAmigo);
        mail.setText(amigo.getMail());

        TextView fecha = (TextView) findViewById(R.id.txtFechaNacimiento_PerfilAmigo);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        fecha.setText(format.format(amigo.getFecha()));

        TextView sexo = (TextView) findViewById(R.id.txtSexo_PerfilAmigo);
        if(amigo.getSexo())
        {
            sexo.setText("Masculino");
        }else{
            sexo.setText("Femenino");
        }
        bloquear = (Button)findViewById(R.id.btnbloquear_PerfilAmigo);
        aceptar = (Button)findViewById(R.id.btnAceptarSolicitud_PerfilAmigo);
        agregar = (Button) findViewById(R.id.btnAgregarAmigo_PerfilAmigo);

        //0-NADIE ENVIO SOLICITUD DE AMISTAD, 1-NO ME RESPONDIO LA SOLICITUD,2-SOMOS AMIGOS,3-NO ME ACEPTO LA SOLICITUD
        //4-NO LE RESPONDI LA SOLICITUD,5-NO LE ACEPTE LA SOLICITUD
        int nosConocemos = ManejadorUsuario.nosConocemos(idAmigo);
        switch(nosConocemos) {
            case 0:
                bloquear.setVisibility(Button.INVISIBLE);
                aceptar.setVisibility(Button.INVISIBLE);
                agregar.setVisibility(Button.VISIBLE);
                break;
            case 1:
                bloquear.setVisibility(Button.INVISIBLE);
                aceptar.setVisibility(Button.INVISIBLE);
                agregar.setVisibility(Button.INVISIBLE);
                break;
            case 2:
                bloquear.setVisibility(Button.INVISIBLE);
                aceptar.setVisibility(Button.INVISIBLE);
                agregar.setVisibility(Button.INVISIBLE);
                break;
            case 3:
                bloquear.setVisibility(Button.INVISIBLE);
                aceptar.setVisibility(Button.INVISIBLE);
                agregar.setVisibility(Button.VISIBLE);
                break;
            case 4:
                bloquear.setVisibility(Button.INVISIBLE);
                aceptar.setVisibility(Button.VISIBLE);
                agregar.setVisibility(Button.INVISIBLE);
                break;
            case 5:
                bloquear.setVisibility(Button.INVISIBLE);
                aceptar.setVisibility(Button.INVISIBLE);
                agregar.setVisibility(Button.VISIBLE);
                break;
        }
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManejadorUsuario.aceptarSolicitud(idAmigo);
                finish();
            }
        });
        bloquear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManejadorUsuario.cancelarSolicitud(idAmigo);
                finish();
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManejadorUsuario.agregarSolicitud(idAmigo);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil_amigo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
