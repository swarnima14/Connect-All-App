package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class MenuActivity extends AppCompatActivity {

    TextView txtAlmList, txtResList, txtTalkList;
    MaterialCardView mt1, mt2, mt3;
    String sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sector = getIntent().getStringExtra("sector");

        txtAlmList = findViewById(R.id.txtAlumniList);
        txtResList = findViewById(R.id.txtResList);
        txtTalkList = findViewById(R.id.txtTalkList);
        mt1 = findViewById(R.id.mt1);
        mt2 = findViewById(R.id.mt2);
        mt3 = findViewById(R.id.mt3);


        mt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, Alumni_List_Filtered.class);
                intent.putExtra("domain", sector);
                startActivity(intent);
            }
        });


        mt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ViewUploadedResources.class);
                intent.putExtra("domain", sector);
                startActivity(intent);
            }
        });

        mt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ViewTalkScheduleActivity.class);
                intent.putExtra("domain", sector);
                startActivity(intent);
            }
        });
    }
}