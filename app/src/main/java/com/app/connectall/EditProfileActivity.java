package com.app.connectall;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    CircleImageView almImg;
    MaterialButton btnSubmit;
    TextView tvBack, tvHeading;
    ConstraintLayout layIni, layCont;
    RadioGroup radioGroup;
    TextInputEditText almName, almGrad, almBranch, almLinkedin, almWorkExp, almCompany, almExp, almMail;
    String name, grad, branch, domain, linkedIn, mail, company, exp, workExp;
    RadioButton selectedRadioButton;
    HashMap<String, String> hashMapFirst, hashMapSec;
    String storagePermission;
    private static final int STORAGE_REQUEST = 2;
    ProgressDialog progressDialog;
    String imgAlmUri;
    Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_alumni_details);

        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        //Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();

        storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        initialise();

        almImg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(!checkStoragePermission()){
                    requestStoragePermission();
                }
                else {
                    pickFromGallery();
                }
                //chooseImage();
            }
        });




                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        name = almName.getText().toString().trim();
                        grad = almGrad.getText().toString().trim();
                        branch = almBranch.getText().toString().trim();
                        linkedIn = almLinkedin.getText().toString().trim();
                        mail = almMail.getText().toString().trim();
                        exp = almExp.getText().toString().trim();
                        workExp = almWorkExp.getText().toString().trim();
                        company = almCompany.getText().toString().trim();

                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            selectedRadioButton = findViewById(selectedRadioButtonId);
                            String selectedRbText = selectedRadioButton.getText().toString();
                            domain = selectedRbText;
                        } else {
                            // Toast.makeText(EditProfileActivity.this, "Nothing selected from the radio group", Toast.LENGTH_SHORT).show();
                        }

                        if (name.isEmpty() || grad.isEmpty() || branch.isEmpty() || linkedIn.isEmpty() || selectedRadioButtonId == -1
                                || mail.isEmpty() || exp.isEmpty() || workExp.isEmpty() || company.isEmpty())
                            Toast.makeText(EditProfileActivity.this, "All fields required", Toast.LENGTH_SHORT).show();


                        else {
                            String time = "" + System.currentTimeMillis();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            String pushId = time;

                            StorageReference path = storageReference.child(pushId + "." + "jpeg");
                            path.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //String pushId = databaseReference.push().getKey();
                                            imgAlmUri = uri.toString();

                                            hashMapFirst = new HashMap<>();
                                            hashMapFirst.put("Name", name);
                                            hashMapFirst.put("Graduation year", grad);
                                            hashMapFirst.put("Branch", branch);
                                            hashMapFirst.put("LinkedIn", linkedIn);
                                            hashMapFirst.put("Alumni Image URL", imgAlmUri);
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

                                                    SharedPreferences preferences = getSharedPreferences("Alumni status", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("Alumni status", "profile complete");
                                                    editor.apply();
                                                    Toast.makeText(EditProfileActivity.this, "Profile created successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(EditProfileActivity.this, AlumniMainProfile.class));
                                                    finish();
                                                }
                                            });
                                        }
                                    });
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }

                    }
                });
    }

    private void initialise() {

        btnSubmit = findViewById(R.id.bt_submit);
        tvHeading = findViewById(R.id.tv_heading);

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

    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // Requesting  gallery permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {

        requestPermissions(new String[]{storagePermission}, STORAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (writeStorageaccepted) {
                pickFromGallery();
            } else {
                Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
            }
        }

    }

    // Here we will pick image from gallery or camera
    private void pickFromGallery() {
        CropImage.activity().start(EditProfileActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                Picasso.with(this).load(resultUri).into(almImg);
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
