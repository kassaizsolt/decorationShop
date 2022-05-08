package com.example.decorationshop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private NotificationManager manager;
    private Context context;
    private static final String CHANNEL_ID = "notification_channel";
    private final int NOTIFICATION_ID = 0;

    public NotificationHandler(Context context) {
        this.context = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Értesítés", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.YELLOW);
        channel.setDescription("Értesítés új termékről");
        this.manager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Értesítés")
                .setContentText("Új termék érhető el a boltban: " + message)
                .setSmallIcon(R.drawable.newprod);

        this.manager.notify(NOTIFICATION_ID, builder.build());
    }
}
