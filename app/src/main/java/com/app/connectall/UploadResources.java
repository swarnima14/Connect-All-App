package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadResources extends AppCompatActivity {

    MaterialButton btnUpload;
    RadioGroup grp;
    TextInputEditText etLink, etDesc;
    TextInputLayout layLink;
    Uri pdfUri = null;
    Uri imgUri = null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act6_upload_resources);

        initialise();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUploadOption();
            }
        });


    }

    private void checkUploadOption() {

        if(grp.getCheckedRadioButtonId() == R.id.btnPdf)
        {
            layLink.setVisibility(View.GONE);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, 1);
            Toast.makeText(this, "pdf", Toast.LENGTH_SHORT).show();
        }
        else if(grp.getCheckedRadioButtonId() == R.id.btnLink)
        {
            layLink.setVisibility(View.VISIBLE);
            /*String link = etLink.getText().toString().trim();
            DatabaseReference linkRef = FirebaseDatabase.getInstance().getReference("Link");
            linkRef.push().setValue(link);*/
            Toast.makeText(this, "link", Toast.LENGTH_SHORT).show();
        }
        else if(grp.getCheckedRadioButtonId() == R.id.btnImage)
        {
            layLink.setVisibility(View.GONE);
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            /*intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image");*/
            startActivityForResult(intent, 2);
            Toast.makeText(this, "image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            layLink.setVisibility(View.GONE);
            Toast.makeText(this, "select from above group", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialise() {
        btnUpload = findViewById(R.id.btnUpload);
        grp = findViewById(R.id.uploadGrp);
        layLink = findViewById(R.id.layLink);
        etDesc = findViewById(R.id.etDesc);
        etLink = findViewById(R.id.etLink);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 1)
        {
            pdfUri = data.getData();
            final String time = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PDF");
            final String pushId = time;

            final StorageReference path = storageReference.child(pushId + "." + "pdf");
            path.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String pushId = databaseReference.push().getKey();
                            databaseReference.child(pushId).setValue(uri.toString());
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadResources.this, "Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }

        if(requestCode == 2 && resultCode == RESULT_OK)
        {
            imgUri = data.getData();

            final String time = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Image");
            final String pushId = time;

            final StorageReference path = storageReference.child(pushId + "." + "jpg");
            path.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String pushId = databaseReference.push().getKey();
                            databaseReference.child(pushId).setValue(uri.toString());
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadResources.this, "Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}