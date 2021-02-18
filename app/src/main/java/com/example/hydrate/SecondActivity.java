package com.example.hydrate;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //String item;
    int mValueOne;
    String item;
    String[] categories = {"Every 30 min","Every 1 hr","Every 2 hrs"};
    private static final int NOTIFICATION_ID = 1;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        EditText newEditText =(EditText)findViewById(R.id.newEditText);
        mValueOne = Integer.parseInt(newEditText.getText()+"");
        // Spinner element

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        ToggleButton Done = findViewById(R.id.done);

        // Set up the Notification Broadcast Intent.
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        Done.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Set the click listener for the toggle button.
        Done.setOnCheckedChangeListener
                ((buttonView, isChecked) -> {
                    Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                    intent.putExtra("data", String.valueOf(spinner.getSelectedItem()));
                    startService(intent);
                    String toastMessage;
                    long repeatInterval=AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    if (isChecked) {
                        if (item.equals("Every 30 min")) {
                            repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                        }
                        if (item.equals("Every 1 hr")) {
                            repeatInterval = AlarmManager.INTERVAL_HALF_HOUR;
                        }
                        if (item.equals("Every 2 hr")) {
                            repeatInterval = AlarmManager.INTERVAL_HOUR;
                        }
                        long triggerTime = SystemClock.elapsedRealtime() ;
                        //+ repeatInterval;
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval,
                                        notifyPendingIntent);

                        // Set the toast message for the "on" case.
                        toastMessage = getString(R.string.alarm_on_toast);
                        createNotificationChannel();
                    }
                    else {
                        // Cancel notification if the alarm is turned off.
                        mNotificationManager.cancelAll();

                        if (alarmManager != null) {
                            alarmManager.cancel(notifyPendingIntent);
                        }
                        // Set the toast message for the "off" case.
                        toastMessage = getString(R.string.alarm_off_toast);

                    }

                    // Show a toast to say the alarm is turned on or off.
                    Toast.makeText(SecondActivity.this, toastMessage,
                            Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),categories[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Hydrate notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies to " +
                    "drink water");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}