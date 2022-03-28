package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act1_launcher);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(LauncherActivity.this, RegisterActivity.class));
                finish();
            }
        }, 2000 );

    }
}