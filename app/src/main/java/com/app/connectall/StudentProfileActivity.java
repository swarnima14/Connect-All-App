package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class StudentProfileActivity extends AppCompatActivity {

    MaterialButton btnViewList, btnSearchAlm, btnResources, btnTalk;
    RadioGroup radioGroup;
    String domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_student_profile);

        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();



        initialise();

        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfileActivity.this, Alumni_List.class));
            }
        });

        btnSearchAlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(StudentProfileActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                int selBtnId = radioGroup.getCheckedRadioButtonId();
                if(selBtnId != -1) {
                    RadioButton selRB = findViewById(selBtnId);
                    domain = selRB.getText().toString().trim();
                    Intent intent = new Intent(StudentProfileActivity.this, Alumni_List_Filtered.class);
                    intent.putExtra("domain", domain);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(StudentProfileActivity.this, "No domain selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initialise() {

        btnViewList = findViewById(R.id.btnViewList);
        btnSearchAlm = findViewById(R.id.btnSearchAlm);
        btnResources = findViewById(R.id.btnViewResources);
        btnTalk = findViewById(R.id.btnViewTalk);
        radioGroup = findViewById(R.id.radioGroup);

    }

}