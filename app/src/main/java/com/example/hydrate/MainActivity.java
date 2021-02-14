package com.example.hydrate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
//import android.support.v4.app.NotificationCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.support.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

//import java.security.KeyStore;

public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notification = new NotificationCompat.Builder(this, "45612");

        notification.setAutoCancel(true);
    }

    public void clickNotifClicked(View view){
        //Builds the notif
        notification.setSmallIcon(R.drawable.ic_launcher2);
        notification.setTicker("Drink some water!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Here is the title");
        notification.setContentText("You need to drink that glass of water, bro!");

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Build notification and issuing it
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }

}