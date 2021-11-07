package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton alumni, student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act4_selection);

        alumni = findViewById(R.id.btnSelAlumni);
        student = findViewById(R.id.btnSelStudent);
        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        alumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("type", "alumni");
                editor.apply();
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                finish();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("type", "student");
                editor.apply();
                startActivity(new Intent(MainActivity.this, StudentProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);

        String type = preferences.getString("type","not defined");
        if(type.equals("alumni"))
        {
            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
            finish();
        }
        if(type.equals("student"))
        {
            startActivity(new Intent(MainActivity.this, StudentProfileActivity.class));
            finish();
        }
    }
}