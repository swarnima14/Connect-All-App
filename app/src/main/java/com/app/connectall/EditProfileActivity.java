package com.app.connectall;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;

public class EditProfileActivity extends AppCompatActivity {

    Button btnNext, btnSubmit;
    TextView tvBack, tvHeading;
    ConstraintLayout layIni, layCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_alumni_details);

        /*SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();*/

        initialise();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layIni.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
                tvHeading.setVisibility(View.GONE);

                layCont.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                tvBack.setVisibility(View.VISIBLE);
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layIni.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                tvHeading.setVisibility(View.VISIBLE);

                layCont.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                tvBack.setVisibility(View.GONE);
            }
        });
    }

    private void initialise() {
        btnNext = findViewById(R.id.bt_register);
        btnSubmit = findViewById(R.id.bt_submit);
        tvBack = findViewById(R.id.tvBack);
        tvHeading = findViewById(R.id.tv_heading);
        layIni = findViewById(R.id.layInitial);
        layCont = findViewById(R.id.layContinue);
    }


}
