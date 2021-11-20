package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;

public class UploadResources extends AppCompatActivity {

    MaterialButton btnUpload, btnUploadLink, btnSel;
    RadioGroup grp;
    TextInputEditText etLink, etDesc, etName, etDescLink;
    TextInputLayout layLink;
    Uri pdfUri = null;
    Uri imgUri = null;
    ProgressDialog progressDialog;
    String uploadedBy, domain;
    ImageView imgUpload;
    Boolean checkImg = false, checkPdf = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act6_upload_resources);

        initialise();
        uploadedBy = getIntent().getStringExtra("name");
        domain = getIntent().getStringExtra("domain");

        btnSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUploadOption();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkImg)
                    upload(imgUri, "jpg");
                if(checkPdf)
                    upload(pdfUri, "pdf");
            }
        });
        btnUploadLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descLink = etDescLink.getText().toString().trim();
                String link = etLink.getText().toString().trim();
                uploadLink(descLink, link);
            }
        });
    }

    private void uploadLink(String descLink, String link) {
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Uploads").child(domain).child("links");
        HashMap<String, String> map = new HashMap<>();
        map.put("desc", descLink);
        map.put("name", "link");
        map.put("uploaded by", uploadedBy);
        map.put("url", link);
        String pushId = databaseReference.push().getKey();
        databaseReference.child(pushId).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UploadResources.this, "Link uploaded successfully", Toast.LENGTH_SHORT).show();
                etDescLink.setText("");
                etLink.setText("");
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadResources.this, "Not uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void upload(Uri uri, String type)
    {
        progressDialog.show();
        String name = etName.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        String time = "" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Uploads").child(domain).child(type);
        String pushId = time;

        StorageReference path = storageReference.child(pushId + "." + type);
        path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String pushId = databaseReference.push().getKey();

                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", name);
                        map.put("desc", desc);
                        map.put("uploaded by", uploadedBy);
                        map.put("url", uri.toString());
                        databaseReference.child(pushId).setValue(map);

                        //databaseReference.child(pushId).setValue(uri.toString());
                    }
                });
                progressDialog.dismiss();
                imgUpload.setVisibility(View.GONE);
                etDesc.setText("");
                etName.setText("");
                grp.clearCheck();
                Toast.makeText(UploadResources.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadResources.this, "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                imgUpload.setVisibility(View.GONE);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressDialog.show();
                imgUpload.setVisibility(View.GONE);
            }
        });
    }

    private void checkUploadOption() {

        if(grp.getCheckedRadioButtonId() == R.id.btnPdf)
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, 1);
            Toast.makeText(this, "pdf", Toast.LENGTH_SHORT).show();
        }
        else if(grp.getCheckedRadioButtonId() == R.id.btnImage)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            /*intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image");*/
            startActivityForResult(intent, 2);
            Toast.makeText(this, "image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "select from above group", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialise() {
        btnUpload = findViewById(R.id.btnUpload);
        btnUploadLink = findViewById(R.id.btnUploadLink);
        btnSel = findViewById(R.id.btnSelect);
        imgUpload = findViewById(R.id.imgUpload);
        grp = findViewById(R.id.uploadGrp);
        layLink = findViewById(R.id.layLink);
        etDesc = findViewById(R.id.etDesc);
        etLink = findViewById(R.id.etLink);
        etName = findViewById(R.id.etName);
        etDescLink = findViewById(R.id.etDescLink);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 1)
        {
            imgUpload.setVisibility(View.VISIBLE);
            pdfUri = data.getData();
            checkImg = false;
            checkPdf = true;
        }

        if(requestCode == 2 && resultCode == RESULT_OK)
        {
            imgUri = data.getData();
            imgUpload.setVisibility(View.VISIBLE);
            checkImg = true;
            checkPdf = false;
        }
    }
}