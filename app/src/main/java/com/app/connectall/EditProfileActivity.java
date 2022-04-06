package com.app.connectall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    CircleImageView almImg;
    Button btnNext, btnSubmit;
    TextView tvBack, tvHeading;
    ConstraintLayout layIni, layCont;
    RadioGroup radioGroup;
    TextInputEditText almName, almGrad, almBranch, almLinkedin, almWorkExp, almCompany, almExp, almMail;
    String name, grad, branch, domain, linkedIn, mail, company, exp, workExp;
    RadioButton selectedRadioButton;
    HashMap<String, String> hashMapFirst, hashMapSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_alumni_details);

        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        //Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();

        initialise();

        almImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chooseImage();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = almName.getText().toString().trim();
                grad = almGrad.getText().toString().trim();
                branch = almBranch.getText().toString().trim();
                linkedIn = almLinkedin.getText().toString().trim();

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedRbText = selectedRadioButton.getText().toString();
                    domain = selectedRbText;
                } else {
                   // Toast.makeText(EditProfileActivity.this, "Nothing selected from the radio group", Toast.LENGTH_SHORT).show();
                }


                hashMapFirst = new HashMap<>();
                hashMapFirst.put("Name", name);
                hashMapFirst.put("Graduation year", grad);
                hashMapFirst.put("Branch", branch);
                hashMapFirst.put("LinkedIn", linkedIn);

                if(name.isEmpty() || grad.isEmpty() || branch.isEmpty() || linkedIn.isEmpty() || selectedRadioButtonId == -1)
                    Toast.makeText(EditProfileActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                else {
                    layIni.setVisibility(View.GONE);
                    btnNext.setVisibility(View.GONE);
                    tvHeading.setVisibility(View.GONE);

                    layCont.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    tvBack.setVisibility(View.VISIBLE);
                }
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
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = almMail.getText().toString().trim();
                exp = almExp.getText().toString().trim();
                workExp = almWorkExp.getText().toString().trim();
                company = almCompany.getText().toString().trim();

                if(mail.isEmpty() || exp.isEmpty() || workExp.isEmpty() || company.isEmpty())
                    Toast.makeText(EditProfileActivity.this, "All fields required", Toast.LENGTH_SHORT).show();

                else {
                    hashMapSec = new HashMap<>();
                    hashMapSec.putAll(hashMapFirst);
                    hashMapSec.put("Mail", mail);
                    hashMapSec.put("Experience", exp);
                    hashMapSec.put("Work Experience", workExp);
                    hashMapSec.put("Company", company);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Alumni");
                    String uid = FirebaseAuth.getInstance().getUid();
                    databaseReference.child(domain).child(uid).setValue(hashMapSec).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            SharedPreferences preferences = getSharedPreferences("Alumni status",MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Alumni status", "profile complete");
                            editor.apply();
                            Toast.makeText(EditProfileActivity.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditProfileActivity.this, AlumniMainProfile.class));
                            finish();
                        }
                    });
                }
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
        almImg = findViewById(R.id.almImg);

        almName = findViewById(R.id.almName);
        almGrad = findViewById(R.id.almGrad);
        almBranch = findViewById(R.id.almBranch);
        almCompany = findViewById(R.id.almCompany);
        almExp = findViewById(R.id.almExp);
        almLinkedin = findViewById(R.id.almLinkedin);
        almMail = findViewById(R.id.almMail);
        almWorkExp = findViewById(R.id.almWorkExp);
        radioGroup = findViewById(R.id.radioGrp);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                almImg.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("Alumni status",MODE_PRIVATE);

        String type = preferences.getString("Alumni status","not defined");
        if(type.equals("profile complete"))
        {
            startActivity(new Intent(EditProfileActivity.this, AlumniMainProfile.class));
            finish();
        }

    }
}
