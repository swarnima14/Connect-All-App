package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    TextView txtAlmList, txtResList, txtTalkList;
    String sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sector = getIntent().getStringExtra("sector");

        txtAlmList = findViewById(R.id.txtAlumniList);
        txtResList = findViewById(R.id.txtResList);
        txtTalkList = findViewById(R.id.txtTalkList);

        txtAlmList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, Alumni_List_Filtered.class);
                intent.putExtra("domain", sector);
                startActivity(intent);
            }
        });

        txtResList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ViewUploadedResources.class);
                intent.putExtra("domain", sector);
                startActivity(intent);
            }
        });

        txtTalkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ViewTalkScheduleActivity.class);
                intent.putExtra("domain", sector);
                startActivity(intent);
            }
        });
    }
}