package com.example.LuxeVista.Activity_Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LuxeVista.R;

public class SpaActivity extends AppCompatActivity {


    private ImageView spaback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spa);

        spaback = findViewById(R.id.spaback);
        spaback.setOnClickListener(v -> {
            Intent intent = new Intent(SpaActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}