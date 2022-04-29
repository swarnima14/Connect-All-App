package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlumniMainProfile extends AppCompatActivity {

    TextView tvAlmName, tvAlmBatch;
    MaterialButton btnAlmEdit;
    CircleImageView cvAlmImg;
    String uid;
    String name, domain;
    FloatingActionButton fbUpload, fbTalk;
    BottomNavigationView bottomNavAlm;
    ImageButton logoutAlm;

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

        logoutAlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AlumniMainProfile.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void initialise() {
        tvAlmName = findViewById(R.id.tvAlmName);
        tvAlmBatch = findViewById(R.id.tvAlmBatch);
        logoutAlm = findViewById(R.id.btnLogoutAlm);
        fbTalk = findViewById(R.id.fbTalk);
        fbUpload = findViewById(R.id.fbUpload);
        cvAlmImg = findViewById(R.id.cvAlmImg);
        uid = FirebaseAuth.getInstance().getUid();

        bottomNavAlm = findViewById(R.id.bottom_navigation_alm);
        bottomNavAlm.setOnNavigationItemSelectedListener(navListener);

        bottomNavAlm.setSelected(false);
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
                        Picasso.with(getApplicationContext()).load(s.child("Alumni Image URL").getValue().toString()).into(cvAlmImg);
                        name = s.child("Name").getValue().toString();
                        domain = "Corporate or Technical Sector";
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
                        Picasso.with(getApplicationContext()).load(s.child("Alumni Image URL").getValue().toString()).into(cvAlmImg);
                        name = s.child("Name").getValue().toString();
                        domain = "Higher Studies";
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
                        Picasso.with(getApplicationContext()).load(s.child("Alumni Image URL").getValue().toString()).into(cvAlmImg);
                        name = s.child("Name").getValue().toString();
                        domain = "Government Sector";
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.cllgSite: {
                    String url = "https://iiitu.ac.in/";
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                }

                case R.id.placements: {
                    String url = "https://iiitu.ac.in/placement/";
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                }

                case R.id.gallery: {
                    String url = "https://iiitu.ac.in/gallery/";
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                }

                case R.id.academics: {
                    String url = "https://iiitu.ac.in/academics/";
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                }

                default:
                {
                    bottomNavAlm.setSelected(false);
                    break;
                }

            }
            return true;
        }
    };
}