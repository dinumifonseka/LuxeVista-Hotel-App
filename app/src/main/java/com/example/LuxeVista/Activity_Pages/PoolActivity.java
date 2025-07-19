package com.example.LuxeVista.Activity_Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LuxeVista.R;

public class PoolActivity extends AppCompatActivity {

    private ImageView poolback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pool);

        poolback = findViewById(R.id.poolback);
        poolback.setOnClickListener(v -> {
            Intent intent = new Intent(PoolActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}