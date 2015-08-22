package com.example.diego.inicio2.vistas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.inicio2.Manejadores.ManejadorUsuario;
import com.example.diego.inicio2.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrearCuenta extends Activity {

    /* DATE PICKER*/
    Button btn;
    int ano_x,mes_x,dia_x;
    static final int DIALOG_ID=0;
    TextView fechaNac;
    TextView linkLoggin;
    /*-------*/
    EditText etNombre;
    EditText etApelldo;
    RadioButton rtSexo;
    EditText etMail;
    EditText etPass;
    Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        /* DATE PICKER*/

        final Calendar cal= Calendar.getInstance();
        ano_x= cal.get(Calendar.YEAR);
        mes_x = cal.get(Calendar.MONTH);
        dia_x= cal.get(Calendar.DAY_OF_MONTH);
        fechaNac = (TextView) findViewById(R.id.txtFechaNaci_Reg);
        fechaNac.setEnabled(false);
        showDialogOnButtonClick();
        LocationToLoggin();
        /*-------*/
        btnRegistrarse = (Button) findViewById(R.id.btnRegister_Reg);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNombre = (EditText) findViewById(R.id.txtNombre_Reg);
                etApelldo = (EditText) findViewById(R.id.txtApellido_Reg);
                etMail = (EditText) findViewById(R.id.txtEmail_Reg);
                etPass = (EditText) findViewById(R.id.txtPass_Reg);
                rtSexo = (RadioButton)findViewById(R.id.radio_mascul_Reg);
                String res = validar();
                if(res=="")
                {
                    Boolean x = ManejadorUsuario.insertarUsuario(etNombre.getText().toString(), etApelldo.getText().toString(), etMail.getText().toString(), etPass.getText().toString(), rtSexo.isChecked(), fechaNac.getText().toString());
                    if(x)
                    {
                        Toast.makeText(CrearCuenta.this, "Gracias por crear una cuenta En Remembering", Toast.LENGTH_LONG).show();
                        Intent inten = new Intent(CrearCuenta.this,Loggin.class);
                        startActivity(inten);
                    }else
                    {
                        Toast.makeText(CrearCuenta.this,"Algo salio mal",Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(CrearCuenta.this,res,Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    /* ------------------------------------------DATE PICKER-----------------------------------*/
    public void showDialogOnButtonClick()
    {
        btn=(Button) findViewById(R.id.btnMuestra_Reg);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    public void LocationToLoggin()
    {
        linkLoggin= (TextView) findViewById(R.id.linkALoggin_Reg);
        linkLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(CrearCuenta.this,Loggin.class);
                startActivity(inten);
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

    private DatePickerDialog.OnDateSetListener dpickerListner= new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
        {
            ano_x=year;
            mes_x=monthOfYear;
            dia_x=dayOfMonth;
            fechaNac.setText(ano_x + "/" + mes_x + "/" + dia_x);

        }
    };

    /* --------------------------------------------------------------------------------------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private String validar()
    {
        String res="";
        if(etNombre.getText().toString()==""||etNombre.getText().toString()=="Nombre")
        {
            res+="El nombre no es valido \n";
        }
        if(etApelldo.getText().toString()==""||etApelldo.getText().toString()=="Apellido")
        {
            res+="El Apellido no es valido \n";
        }
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(etMail.getText().toString());
        if(!matcher.matches())
        {
            res+="El Email no es valido \n";
        }
        if(etPass.getText().toString().length()<8 || etPass.getText().toString().length()>20)
        {
            res+="El Pass debe contener entre 8 y 20 caracteres \n";
        }
        return res;
    }


}