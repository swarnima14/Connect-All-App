package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentFormActivity extends AppCompatActivity {

    private static final int STORAGE_REQUEST = 100;
    CircleImageView stuImg;
    TextInputEditText stuName, stuBranch, stuYear;
    MaterialButton btnStuSubmit;
    String storagePermission;
    Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        initialise();

        storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        stuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkStoragePermission()){
                    requestStoragePermission();
                }
                else {
                    pickFromGallery();
                }
            }
        });

        btnStuSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = stuName.getText().toString().trim();
                String branch = stuBranch.getText().toString().toUpperCase().trim();
                String year = stuYear.getText().toString().trim();

                if(name.isEmpty() || branch.isEmpty() || year.isEmpty() || resultUri == null)
                {
                    Toast.makeText(StudentFormActivity.this, "All fields required.", Toast.LENGTH_SHORT).show();
                }
                else {
                    upload(resultUri, name, branch, year);

                }

            }
        });
    }

    private void upload(Uri resultUri, String name, String branch, String year) {

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
                        String imgAlmUri = uri.toString();

                        HashMap<String, String> hashMap;
                        hashMap = new HashMap<>();
                        hashMap.put("Name", name);
                        hashMap.put("Branch", branch);
                        hashMap.put("Year", year);
                        hashMap.put("Student Image URL", imgAlmUri);

                        SharedPreferences pref = getSharedPreferences("Student Status", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();


                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Students");
                        String uid = FirebaseAuth.getInstance().getUid();
                        db.child(uid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(StudentFormActivity.this, "Profile created.", Toast.LENGTH_SHORT).show();
                                editor.putString("Status","completed");
                                editor.apply();
                                editor.commit();
                                Intent intent = new Intent(StudentFormActivity.this, StudentProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentFormActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initialise() {

        stuImg = findViewById(R.id.stuImg);
        stuName = findViewById(R.id.etStuName);
        stuBranch = findViewById(R.id.etStuBranch);
        stuYear = findViewById(R.id.etStuYear);
        btnStuSubmit = findViewById(R.id.btnStuSubmit);

    }

    private Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {

        requestPermissions(new String[]{storagePermission}, STORAGE_REQUEST);
    }

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

    private void pickFromGallery() {
        CropImage.activity().start(StudentFormActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                Picasso.with(this).load(resultUri).into(stuImg);
            }
        }
    }
}