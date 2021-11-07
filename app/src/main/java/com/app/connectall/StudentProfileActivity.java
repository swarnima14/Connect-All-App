package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class StudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_student_profile);

        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();
    }
}