package com.example.hydrate;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
import android.widget.Button;
//import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

//import static android.content.Context.NOTIFICATION_SERVICE;


public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Button b1 = (Button) findViewById(R.id.setupbutton);
                b1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SecondActivity.class)));


        }}
