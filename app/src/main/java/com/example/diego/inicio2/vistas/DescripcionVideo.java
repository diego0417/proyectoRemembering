package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.inicio2.Conexion.GPS;
import com.example.diego.inicio2.Entidades.Permiso;
import com.example.diego.inicio2.Entidades.Ubicacion;
import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.Entidades.Video;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorPermiso;
import com.example.diego.inicio2.Manejadores.ManejadorUbicacion;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.Manejadores.ManejadorVideo;
import com.example.diego.inicio2.R;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DescripcionVideo extends Activity {

    Button listo,selecFecha;
    EditText titulo,descripcion,fecha_desb;
    TextView desc;


    Spinner spinner;

    int ano_x,mes_x,dia_x;
    static final int DIALOG_ID=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_descripcion_video);
        // PANTALLA EN VERTICAL
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//



        final Calendar cal= Calendar.getInstance();
        ano_x= cal.get(Calendar.YEAR);
        mes_x = cal.get(Calendar.MONTH);
        dia_x= cal.get(Calendar.DAY_OF_MONTH);
        fecha_desb = (EditText) findViewById(R.id.txtFechaDesbloqueo_DescVideo);
        fecha_desb.setEnabled(false);
        showDialogOnButtonClick();



        spinner=(Spinner) findViewById(R.id.spinnerVisibilidad_DescVideo);


        ArrayList<Permiso> per = ManejadorPermiso.getAll();
        //String[] permisos= new String[per.size()];
        Log.e("Entre","Entre");
        int i=-1;
        /*for(Permiso x: per)
        {
            Log.e("SEEEEEE","SEEEEEE");
            i++;
            Log.e("Valor 1","trola  "+x.getDescripcionCorta().toString());
            permisos[i]=x.getDescripcionCorta().toString();

        }
        Log.e("Sali","Sali");*/
        ArrayAdapter<Permiso> permi = new ArrayAdapter<Permiso>(this, android.R.layout.simple_spinner_item,per);
        spinner.setAdapter(permi);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View v,
                                       int arg2, long arg3) {
                Permiso pe = (Permiso) ((Spinner) findViewById(R.id.spinnerVisibilidad_DescVideo)).getSelectedItem();
                desc = (TextView) findViewById(R.id.txtDescripcionLargaPermiso_DescVideo);
                desc.setText(pe.getDescripcion());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

        listo = (Button)findViewById(R.id.btnTerminar_DescVideo);
        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar())
                {
                    GPS gps = new GPS(DescripcionVideo.this);
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    int idUbicacion = ManejadorUbicacion.insertarUbicacion(latitude, longitude);
                    Ubicacion ubicacion = new Ubicacion(latitude,longitude,idUbicacion);
                    Permiso permiso = (Permiso) ((Spinner) findViewById(R.id.spinnerVisibilidad_DescVideo)).getSelectedItem();
                    Bundle bundle = getIntent().getExtras();
                    int idVideo =  bundle.getInt("idVideo");
                    titulo = (EditText)findViewById(R.id.txtTitulo_DescVideo);
                    descripcion = (EditText)findViewById(R.id.txtDescripcion_DescVideo);
                    Usuario usuario = ManejadorUsuario.usuario;
                    Date fechaDesbloqueo;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        fechaDesbloqueo = format.parse(fecha_desb.getText().toString());
                        Log.e("asda","NONONO"+fechaDesbloqueo);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        fechaDesbloqueo=null;
                        Log.e("asda","NONONO");
                    }
                    Video video = new Video(idVideo,usuario,null,fechaDesbloqueo,permiso,ubicacion,titulo.getText().toString(),descripcion.getText().toString());
                    Boolean res = ManejadorVideo.descripcionVideo(video);
                    if(res)
                    {
                        Toast.makeText(DescripcionVideo.this,"Se subio el video exitosamente",Toast.LENGTH_LONG).show();
                        finish();
                    }else
                    {
                        Toast.makeText(DescripcionVideo.this,"Lo sentimos ocurrio un error, vuelve a intentarlo mas tarde",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    public void showDialogOnButtonClick()
    {
        selecFecha=(Button) findViewById(R.id.btnFecha_DescVideo);

        selecFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==DIALOG_ID)
        {
            return new DatePickerDialog(this,dpickerListner,ano_x,mes_x,dia_x);
        }else
        {
            return null;
        }
    }

    public static Date ParseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaDate = null;
        try
        {
            fechaDate = formato.parse(fecha);
        }catch(ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    private static boolean compararFechas(Date fec) {
        Date fechaActual = new Date();
        Date fechaelegida = fec;




        return true;

    }

    private DatePickerDialog.OnDateSetListener dpickerListner= new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
        {
            ano_x=year;
            mes_x=monthOfYear;
            dia_x=dayOfMonth;
            Date fec = ParseFecha(ano_x + "/" + mes_x + "/" + dia_x);
            Toast.makeText(DescripcionVideo.this,fec+"",Toast.LENGTH_LONG).show();
            /*if(compararFechas(fec))
            {
                Toast.makeText(DescripcionVideo.this,"ESTA BIEN",Toast.LENGTH_LONG).show();
                fecha_desb.setText(ano_x + "/" + mes_x + "/" + dia_x);
            }else
            {

                Toast.makeText(DescripcionVideo.this,"MALO MALO ERES",Toast.LENGTH_LONG).show();
                //Toast.makeText(DescripcionVideo.this,,Toast.LENGTH_LONG).show();
                //Date fechaActual = new Date();
                //fecha_desb.setText(fechaActual.getYear() + "/" + fechaActual.getMonth() + "/" + fechaActual.getDay());


            }*/


        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_descripcion_video, menu);
        return true;
    }

    private Boolean validar()
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
