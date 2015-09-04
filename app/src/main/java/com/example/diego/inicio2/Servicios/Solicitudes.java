package com.example.diego.inicio2.Servicios;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.diego.inicio2.Entidades.Usuario;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.Manejadores.ManejadorNotificaciones;
import com.example.diego.inicio2.Manejadores.ManejadorUsuario;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by diego on 03/09/2015.
 */
public class Solicitudes extends Service {

    private Timer temporizador = new Timer();
    private static final long INTERVALO_ACTUALIZACION = 20000; // En ms .. 1 s son 1000 ms
    private Handler handler;
    int idNoti = 1001;
    private ArrayList<Usuario> solicitantes;
    private int cantidad;

    @Override
    public void onCreate() {
        super.onCreate();
        iniciarCronometro();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    //lanza noti
                    ManejadorNotificaciones.notify(solicitantes.get(0).getNombre()+" "+solicitantes.get(0).getApellido()," Quiere comenzar a ser tu amigo.", idNoti++);
                    ManejadorNotificaciones.modificarNotificacionesVisto();
                    Log.i("diegooooooooooo","la cantidad del msj es: ");
                    MainActivity.modificaAmigosSolicitud(ManejadorUsuario.cuentaSolicitudes());
                }
                if(msg.what > 1){
                    //lanza noti
                    ManejadorNotificaciones.notify("Nuevos amigos!", "Varias personas quieren tenerte de amigo.", idNoti++);
                    ManejadorNotificaciones.modificarNotificacionesVisto();
                    Log.i("diegooooooooooo","la cantidad del msj es: ");
                    MainActivity.modificaAmigosSolicitud(ManejadorUsuario.cuentaSolicitudes());
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        Log.i("diegoooooooooooo","paro");
        temporizador.cancel();
        super.onDestroy();
    }

    private void iniciarCronometro() {
        temporizador.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.i("diegoooooooooooo","paaaaaaa");
                solicitantes = ManejadorNotificaciones.cuentaSolicitudes();
                cantidad = solicitantes.size();
                if(cantidad>0){
                    handler.sendEmptyMessage(cantidad);
                }
            }
        }, 0, INTERVALO_ACTUALIZACION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
