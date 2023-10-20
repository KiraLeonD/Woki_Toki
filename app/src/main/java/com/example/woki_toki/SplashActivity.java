package com.example.woki_toki;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the ActionBar/Toolbar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

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
}
