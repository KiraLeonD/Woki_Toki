package com.example.woki_toki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.VideoTrack;


public class HomeActivity extends AppCompatActivity {

    String[] channel = {"Channel 1","Channel 2","Channel 3","Channel 4","Channel 5"};

    AutoCompleteTextView autoCompleteTextView;
    ImageButton speakerbtn, bigwhitemic;
    TextView talkstatus;
    boolean isMuted,isTalking,darkMODE;
    ArrayAdapter<String> adapterItems;
    Switch darkMode;
    SharedPreferences sp; SharedPreferences.Editor edit;
    ImageView wokilogonavwhite, wokilogonav, navbg, navbgwhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the ActionBar/Toolbar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        darkMode = findViewById(R.id.switch3);
        wokilogonavwhite = findViewById(R.id.imageView9);
        wokilogonav = findViewById(R.id.imageView8);
        navbgwhite = findViewById(R.id.navbgwhite);
        navbg = findViewById(R.id.navbg);
        sp = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        darkMODE = sp.getBoolean("dark", false);

        if (darkMODE) {
            wokilogonavwhite.setVisibility(View.VISIBLE);
            navbgwhite.setVisibility(View.VISIBLE);
            wokilogonav.setVisibility(View.INVISIBLE);
            navbg.setVisibility(View.INVISIBLE);
            darkMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            wokilogonavwhite.setVisibility(View.INVISIBLE);
            navbgwhite.setVisibility(View.INVISIBLE);
            wokilogonav.setVisibility(View.VISIBLE);
            navbg.setVisibility(View.VISIBLE);
            darkMode.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sp.edit();
                if (darkMode.isChecked()) {
                    // Set night mode to yes if the Switch is checked
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    wokilogonavwhite.setVisibility(View.VISIBLE);
                    navbgwhite.setVisibility(View.VISIBLE);
                    wokilogonav.setVisibility(View.INVISIBLE);
                    navbg.setVisibility(View.INVISIBLE);
                    edit.putBoolean("dark", true);
                } else {
                    // Set night mode to no if the Switch is not checked
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    wokilogonavwhite.setVisibility(View.INVISIBLE);
                    navbgwhite.setVisibility(View.INVISIBLE);
                    wokilogonav.setVisibility(View.VISIBLE);
                    navbg.setVisibility(View.VISIBLE);
                    edit.putBoolean("dark", false);
                }
                edit.apply();
            }
        });

        speakerbtn = findViewById(R.id.speakerbtn);
        bigwhitemic = findViewById(R.id.bigwhitemic);
        talkstatus = findViewById(R.id.ttttext);
        SeekBar seekBar = findViewById(R.id.seekBar2);
        seekBar.setProgress(50);


        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, channel);

        autoCompleteTextView.setAdapter(adapterItems);
        //CHANNEL SELECTION
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(HomeActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });
        //END OF CHANNEL SELECTION
        bigwhitemic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent. ACTION_DOWN) {
                    bigwhitemic.setImageDrawable(getResources().getDrawable(R.drawable.bigwhitemictalk));
                    talkstatus.setText("Talk Now");
                } else if (action == MotionEvent. ACTION_UP || action == MotionEvent. ACTION_CANCEL ){
                    bigwhitemic.setImageDrawable(getResources().getDrawable(R.drawable.micbtn));
                    talkstatus.setText("Tap to talk");
                }
                return false;
            }
        });
        /*
        //MIC BUTTON
        bigwhitemic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isTalking){
                    isTalking=true;
                    bigwhitemic.setImageDrawable(getResources().getDrawable(R.drawable.bigwhitemictalk));
                    talkstatus.setText("Talk Now");
                }
                else {
                    isTalking=false;
                    bigwhitemic.setImageDrawable(getResources().getDrawable(R.drawable.micbtn));
                    talkstatus.setText("Tap to talk");
                }
            }
        });
        //END MIC BUTTON

         */
        //MUTE BUTTON
        speakerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMuted){
                    isMuted=true;
                    seekBar.setProgress(0);
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.volmute));
                }
                else {
                    isMuted=false;
                    seekBar.setProgress(50);
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.volup));
                }
            }
        });
        //END MUTE BUTTON
        //VOL BAR
// Add an OnSeekBarChangeListener to the SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    isMuted = true;
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.volmute));
                } else {
                    isMuted = false;
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.volup));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This method is called when the user starts dragging the SeekBar.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // This method is called when the user stops dragging the SeekBar.
            }
        });

        //END VOL BAR


    }

}