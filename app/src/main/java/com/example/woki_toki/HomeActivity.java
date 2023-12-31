package com.example.woki_toki;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.widget.Spinner;

import com.example.woki_toki.media.RtcTokenBuilder2;
import com.example.woki_toki.media.RtcTokenBuilder2.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.ChannelMediaOptions;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton speakerbtn, bigwhitemic;
    TextView talkstatus, nickNametv, tvSpeakStat;
    boolean isMuted, isTalking, darkMODE;
    Switch darkMode;
    SharedPreferences sp;
    ImageView logodark, logolight;
    ImageButton unmutedark, unmutelight;

    // Agora channel credentials
    private int uid = 0;
    static int expirationTimeInSeconds = 3600;
    int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
    RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();

    private List<String> speakers = new ArrayList<>(); //Contains speaker list

    private static final String[] adjectives = {"Swift", "Daring", "Sunny", "Fierce", "Gentle", "Brilliant", "Vivid", "Jolly", "Kaliwang"};
    private static final String[] nouns = {"Wipes", "Soap", "Thunder", "Butones", "Rider", "Tsinelas", "Sangay", "Hotdog", "Kanan"};
    private String channelName = "Channel 1";
    private String appId = "a298679016904e1580a88067a2801c42";
    private String appCertificate = "181b21d144fa4b68b82c4ba752e2cc0b";
    private String token = tokenBuilder.buildTokenWithUid(appId, appCertificate,
            channelName, uid, Role.ROLE_PUBLISHER, timestamp, timestamp);

    private String channelName2 = "Channel 2";
    private String appId2 = "9d3f253ab4a1465b8c3ad3822f7dc299";
    private String appCertificate2 = "339d360fa0d5408e9d862db9d79d2fc6";
    private String token2 = tokenBuilder.buildTokenWithUid(appId2, appCertificate2,
            channelName2, uid, Role.ROLE_PUBLISHER, timestamp, timestamp);

    private String channelName3 = "Channel 3";
    private String appId3 = "8eac6b67f49d4fe9ac6af1a3bce0c284";
    private String appCertificate3 = "cfcfa47edf954b17bd45633280f6e53d";
    private String token3 = tokenBuilder.buildTokenWithUid(appId3, appCertificate3,
            channelName3, uid, Role.ROLE_PUBLISHER, timestamp, timestamp);

    private String channelName4 = "Channel 4";
    private String appId4 = "6d4b3ca419e44a33a072ed79833a17  1b";
    private String appCertificate4 = "eca5071971f34d86a057fc377682d169";
    private String token4 = tokenBuilder.buildTokenWithUid(appId4, appCertificate4,
            channelName4, uid, Role.ROLE_PUBLISHER, timestamp, timestamp);

    private String channelName5 = "Channel 5";
    private String appId5 = "11bc9ee499ab45edb5bfec5a00026d07";
    private String appCertificate5 = "f1496a9444224edd944a1b348e8eee0c";
    private String token5 = tokenBuilder.buildTokenWithUid(appId5, appCertificate5,
            channelName5, uid, Role.ROLE_PUBLISHER, timestamp, timestamp);


    // Track the status of your connection
    private boolean isJoined = false;

    // Agora engine instance
    private RtcEngine agoraEngine;

    private void joinChannel(String channelName, String appId, String token) {
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.autoSubscribeAudio = true;
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;
        agoraEngine.joinChannel(token, channelName, uid, options);
    }

    private void setupVoiceSDKEngine() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Check the error.");
        }
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onUserJoined(int uid, int elapsed) {
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            isJoined = true;
        }

        @Override
        public void onUserOffline(int uid, int reason) {
        }

        @Override
        public void onLeaveChannel(RtcStats stats) {
            isJoined = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Woki Toki");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_home);


        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }

        setupVoiceSDKEngine();

        speakerbtn = findViewById(R.id.speakerbtn);
        bigwhitemic = findViewById(R.id.bigwhitemic);
        talkstatus = findViewById(R.id.ttttext);
        SeekBar seekBar = findViewById(R.id.seekBar2);
        seekBar.setProgress(50);

        bigwhitemic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                String selectedChannel = getSelectedChannel(); // Add this method to get the selected channel name

                if (action == MotionEvent.ACTION_DOWN) {
                    if ("Select A Channel".equals(selectedChannel)) {
                        // Show a toast message if "Select A Channel" is selected
                        Toast.makeText(HomeActivity.this, "Please Choose a Channel to Talk.", Toast.LENGTH_SHORT).show();
                    } else {
                        bigwhitemic.setImageDrawable(getResources().getDrawable(R.drawable.bigwhitemictalk1));
                        talkstatus.setText("Talk Now");
                        agoraEngine.enableLocalAudio(true);
                    }
                } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    bigwhitemic.setImageDrawable(getResources().getDrawable(R.drawable.micbtn));
                    talkstatus.setText("Tap to talk");
                    agoraEngine.enableLocalAudio(false);
                }
                return false;
            }
        });

        speakerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMuted) {
                    isMuted = true;
                    seekBar.setProgress(0);
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.mutedark));
                } else {
                    isMuted = false;
                    seekBar.setProgress(50);
                    speakerbtn.setImageDrawable(getResources().getDrawable(R.drawable.unmutedark));
                }
            }
        });

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
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        agoraEngine.leaveChannel();

        // Check which channel is selected and update credentials accordingly
        if (item.equals("Channel 1")) {
            joinChannel(channelName, appId, token);
            Toast.makeText(HomeActivity.this, "You are on " + item, Toast.LENGTH_SHORT).show();
        } else if (item.equals("Channel 2")) {
            joinChannel(channelName2, appId2, token2);
            Toast.makeText(HomeActivity.this, "You are on " + item, Toast.LENGTH_SHORT).show();
        } else if (item.equals("Channel 3")) {
            joinChannel(channelName3, appId3, token3);
            Toast.makeText(HomeActivity.this, "You are on " + item, Toast.LENGTH_SHORT).show();
        } else if (item.equals("Channel 4")) {
            joinChannel(channelName4, appId4, token4);
            Toast.makeText(HomeActivity.this, "You are on " + item, Toast.LENGTH_SHORT).show();
        } else if (item.equals("Channel 5")) {
            joinChannel(channelName5, appId5, token5);
            Toast.makeText(HomeActivity.this, "You are on " + item, Toast.LENGTH_SHORT).show();
        } else if (item.equals("Select A Channel")) {
            Toast.makeText(HomeActivity.this, "Select A Channel", Toast.LENGTH_SHORT).show();
        } else {
            // Handle other channels if needed
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    protected void onDestroy() {
        super.onDestroy();
        agoraEngine.leaveChannel();

        new Thread(() -> {
            RtcEngine.destroy();
            agoraEngine = null;
        }).start();
    }

    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO
    };

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem itemswitch = menu.findItem(R.id.switch_action_bar);
        itemswitch.setActionView(R.layout.use_switch);
        logodark = findViewById(R.id.icon);
        logolight = findViewById(R.id.icon1);
        unmutedark = findViewById(R.id.speakerbtn);
        unmutelight = findViewById(R.id.speakerbtn1);

        final Switch sw = (Switch) menu.findItem(R.id.switch_action_bar).getActionView().findViewById(R.id.switch2);
        darkMode = sw;
        sp = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        darkMODE = sp.getBoolean("dark", false);

        if (darkMODE) {
            darkMode.setChecked(true);
            logolight.setVisibility(View.VISIBLE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            darkMode.setChecked(false);
            logodark.setVisibility(View.VISIBLE);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        darkMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sp.edit();
                if (darkMode.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    edit.putBoolean("dark", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    edit.putBoolean("dark", false);
                }
                edit.apply();
            }
        });
        return true;
    }

    private String getSelectedChannel() {
        Spinner spinner = findViewById(R.id.spinner1);
        String selectedChannel = spinner.getSelectedItem().toString();
        return selectedChannel;
    }

}
