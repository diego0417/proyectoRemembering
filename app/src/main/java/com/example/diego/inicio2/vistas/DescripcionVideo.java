package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diego.inicio2.Entidades.Permiso;
import com.example.diego.inicio2.Manejadores.ManejadorPermiso;
import com.example.diego.inicio2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DescripcionVideo extends Activity {

    Button listo,selecFecha;
    EditText titulo,descripcion,fecha_desb;


    Spinner spinner;

    int ano_x,mes_x,dia_x;
    static final int DIALOG_ID=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_descripcion_video);
        // PANTALLA EN VERTICAL
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//

        listo = (Button)findViewById(R.id.btnTerminar_DescVideo);


        final Calendar cal= Calendar.getInstance();
        ano_x= cal.get(Calendar.YEAR);
        mes_x = cal.get(Calendar.MONTH);
        dia_x= cal.get(Calendar.DAY_OF_MONTH);
        fecha_desb = (EditText) findViewById(R.id.txtFechaDesbloqueo_DescVideo);
        fecha_desb.setEnabled(false);
        showDialogOnButtonClick();



        spinner=(Spinner) findViewById(R.id.spinnerVisibilidad_DescVideo);


        ArrayList<Permiso> per = ManejadorPermiso.getAll();
        String[] permisos= new String[per.size()];
        Log.e("Entre","Entre");
        int i=-1;
        for(Permiso x: per)
        {
            Log.e("SEEEEEE","SEEEEEE");
            i++;
            Log.e("Valor 1","trola  "+x.getDescripcionCorta().toString());
            permisos[i]=x.getDescripcionCorta().toString();

        }
        Log.e("Sali","Sali");
        ArrayAdapter<String> permi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,permisos);
        spinner.setAdapter(permi);


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




    private DatePickerDialog.OnDateSetListener dpickerListner= new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
        {
            ano_x=year;
            mes_x=monthOfYear;
            dia_x=dayOfMonth;
            //Date fec = ParseFecha(ano_x + "/" + mes_x + "/" + dia_x);
            fecha_desb.setText(ano_x + "/" + mes_x + "/" + dia_x);

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
