package com.example.hydrate;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button exit=(Button)findViewById(R.id.exitButton);
        exit.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
    }
}