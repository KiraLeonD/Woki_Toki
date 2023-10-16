package com.example.woki_toki;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    String[] channel = {"Channel 1","Channel 2","Channel 3","Channel 4","Channel 5"};

    AutoCompleteTextView autoCompleteTextView;
    ImageButton speakerbtn, bigwhitemic;
    TextView talkstatus;
    boolean isMuted,isTalking;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the ActionBar/Toolbar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        speakerbtn = findViewById(R.id.speakerbtn);
        bigwhitemic = findViewById(R.id.bigwhitemic);
        talkstatus = findViewById(R.id.ttttext);


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
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.volmute));
                }
                else {
                    isMuted=false;
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.volup));
                }
            }
        });
        //END MUTE BUTTON
    }
}