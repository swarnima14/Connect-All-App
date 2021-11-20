package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlumniMainProfile extends AppCompatActivity {

    TextView tvAlmName, tvAlmBatch;
    MaterialButton btnAlmEdit;
    CircleImageView cvAlmImg;
    String uid;
    String name, domain;
    FloatingActionButton fbUpload, fbTalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_main_profile);

        initialise();

        fbUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlumniMainProfile.this, UploadResources.class);
                intent.putExtra("name", name);
                intent.putExtra("domain", domain);
                startActivity(intent);
            }
        });
        fbTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlumniMainProfile.this, TalkActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("domain", domain);
                startActivity(intent);
            }
        });
    }

    private void initialise() {
        tvAlmName = findViewById(R.id.tvAlmName);
        tvAlmBatch = findViewById(R.id.tvAlmBatch);
        btnAlmEdit = findViewById(R.id.btnAlmEdit);
        fbTalk = findViewById(R.id.fbTalk);
        fbUpload = findViewById(R.id.fbUpload);
        cvAlmImg = findViewById(R.id.cvAlmImg);
        uid = FirebaseAuth.getInstance().getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Alumni").child("Corporate or Technical Sector");
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Alumni").child("Higher Studies");
        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Alumni").child("Government Sector");
        String uid = FirebaseAuth.getInstance().getUid();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Toast.makeText(AlumniMainProfile.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(AlumniMainProfile.this, snapshot.getRef().getKey().toString(), Toast.LENGTH_SHORT).show();
                for(DataSnapshot s:  snapshot.getChildren())
                {

                    if(uid.equals(s.getRef().getKey().toString()))
                    {
                        name = s.child("Name").getValue().toString();
                        domain = "Tech sector";
                        String batch = s.child("Graduation year").getValue().toString();
                        tvAlmName.setText(name);
                        tvAlmBatch.setText("BATCH OF "+batch);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AlumniMainProfile.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:  snapshot.getChildren())
                {

                    if(uid.equals(s.getRef().getKey().toString()))
                    {
                        name = s.child("Name").getValue().toString();
                        domain = "Higher studies";
                        String batch = s.child("Graduation year").getValue().toString();
                        tvAlmName.setText(name);
                        tvAlmBatch.setText("BATCH OF "+batch);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AlumniMainProfile.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:  snapshot.getChildren())
                {
                    if(uid.equals(s.getRef().getKey().toString()))
                    {
                        name = s.child("Name").getValue().toString();
                        domain = "Govn sector";
                        String batch = s.child("Graduation year").getValue().toString();
                        tvAlmName.setText(name);
                        tvAlmBatch.setText("BATCH OF "+batch);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AlumniMainProfile.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}