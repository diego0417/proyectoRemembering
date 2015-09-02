package com.example.diego.inicio2.Manejadores;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.BassBoost;
import android.provider.Settings;

import com.example.diego.inicio2.ApplicationContextProvider;
import com.example.diego.inicio2.MainActivity;
import com.example.diego.inicio2.R;

/**
 * Created by diego on 02/09/2015.
 */
public class ManejadorNotificaciones {

    private static Context context = ApplicationContextProvider.getContext();

    public static void notify (String title,String mensaje,int notiID,int currentProgress,int maxProgress){
        Bitmap iconLarge = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_diego_web);
        NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_noti)
                .setLargeIcon(iconLarge)
                .setContentTitle(title)
                .setTicker(mensaje)
                .setNumber(currentProgress)
                .setAutoCancel(true)
                .setProgress(maxProgress,currentProgress,false)
                .setContentText(mensaje);
        if(currentProgress==1){
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        }

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification = builder.build();
        manager.notify(notiID,notification);
    }

    public static void notify (String title,String mensaje,int notiID){
        Bitmap iconLarge = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_diego_web);
        NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_noti2)
                .setLargeIcon(iconLarge)
                .setContentTitle(title)
                .setTicker(mensaje)
                .setAutoCancel(true)
                .setContentText(mensaje)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification = builder.build();
        manager.notify(notiID,notification);
    }

}
