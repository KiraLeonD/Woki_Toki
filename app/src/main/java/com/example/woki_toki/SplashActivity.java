package com.example.woki_toki;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sp;
    ImageView logonotxtdark, logonotxtlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the ActionBar/Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash);
        initializeViews();

        // Find the ConstraintLayout
        ConstraintLayout splashLayout = findViewById(R.id.splashLayout);

        // Set a click listener for the ConstraintLayout
        splashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new Intent to navigate to the next activity
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                // Finish the current activity (optional)
                finish();
            }
        });

        // Handle night mode and visibility
        handleNightMode();
    }

    private void initializeViews() {
        logonotxtdark = findViewById(R.id.imageView);
        logonotxtlight = findViewById(R.id.imageView1);
    }

    private void handleNightMode() {
        sp = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        boolean darkMODE = sp.getBoolean("dark", false);

        if (darkMODE){
            logonotxtlight.setVisibility(View.VISIBLE);
            logonotxtdark.setVisibility(View.INVISIBLE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            logonotxtlight.setVisibility(View.INVISIBLE);
            logonotxtdark.setVisibility(View.VISIBLE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
