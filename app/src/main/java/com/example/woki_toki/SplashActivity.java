package com.example.woki_toki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the ActionBar/Toolbar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        AppCompatDelegate();

        // Find the ConstraintLayout
        ConstraintLayout splashLayout = findViewById(R.id.splashLayout);

        // Set a click listener for the ConstraintLayout
        splashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new Intent to navigate to the next activity
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
               // Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                // Finish the current activity (optional)
                finish();
            }
        });
    }
    public void AppCompatDelegate(){
        sp = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        boolean darkMODE = sp.getBoolean("dark", false);

        if (darkMODE){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
