package com.example.LuxeVista.Activity_Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LuxeVista.R;

public class ExploreActivity extends AppCompatActivity {

    private ImageView exploreback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore);

        exploreback = findViewById(R.id.exploreback);
        exploreback.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}