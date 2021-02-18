package com.example.hydrate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ToggleButton;
import static android.content.Context.NOTIFICATION_SERVICE;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity{
    Button buttonAdd;
    Button buttonSub;

    //String item;
    int mValueOne;
    boolean Addition, mSubtract;

    private static final int NOTIFICATION_ID = 1;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

/*
        // Spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Every 30 min");
        categories.add("Every 1 hr");
        categories.add("Every 2 hrs");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        });
*/
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
                    Intent intent = new Intent(SecondActivity.this, AlarmReceiver.class);
                    intent.putExtra("data", String.valueOf(spinner.getSelectedItem()));
                    startActivity(intent);
                    String toastMessage;
                    if (isChecked) {
                        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                        long triggerTime = SystemClock.elapsedRealtime() ;
                                //+ repeatInterval;
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval,
                                        notifyPendingIntent);
                        // If the Toggle is turned on, set the repeating alarm with
                        // a 15 minute interval.
                        //if (alarmManager != null) {
                            //alarmManager.setInexactRepeating
                                    //(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval,
                                            //notifyPendingIntent);
                        //}
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

        // Create the notification channel.

    }


    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

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




        buttonAdd = (Button) findViewById(R.id.buttonadd);
        buttonSub = (Button) findViewById(R.id.buttonsub);
        EditText newEditText=(EditText)findViewById(R.id.newEditText);
        buttonAdd.setOnClickListener(v -> {

            if (newEditText == null) {
                newEditText.setText("0");
            } else {
                mValueOne = Integer.parseInt(newEditText.getText()+"");
                mValueOne++;
                newEditText.setText(String.valueOf(mValueOne));
            }
        });
        buttonSub.setOnClickListener(v -> {
            mValueOne = Integer.parseInt(newEditText.getText() + "");
            mValueOne--;
            newEditText.setText(String.valueOf(mValueOne));
        }

        );
        //Drop down menu
        //@Override


    }
    }
