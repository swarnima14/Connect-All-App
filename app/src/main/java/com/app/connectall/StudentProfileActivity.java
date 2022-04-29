package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileActivity extends AppCompatActivity {

    MaterialButton btnViewList, btnSearchAlm, btnResources, btnGovSel, btnTechSel, btnHigherSel;
    RadioGroup radioGroup;
    String uid;
    BottomNavigationView bottomNav;

    TextView tvStuName, tvStuBranch;
    CircleImageView cvStuImg;
    ImageButton logoutStu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_student_profile);

        /*SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();*/

        initialise();

        btnGovSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentProfileActivity.this, MenuActivity.class);
                intent.putExtra("sector","Government Sector");
                startActivity(intent);
            }
        });

        btnTechSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentProfileActivity.this, MenuActivity.class);
                intent.putExtra("sector","Corporate or Technical Sector");
                startActivity(intent);
            }
        });

        btnHigherSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentProfileActivity.this, MenuActivity.class);
                intent.putExtra("sector","Higher Studies");
                startActivity(intent);
            }
        });

        logoutStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void initialise() {

        btnGovSel = findViewById(R.id.btnGovSelection);
        btnTechSel = findViewById(R.id.btnTechSelection);
        btnHigherSel = findViewById(R.id.btnHigherSelection);

        tvStuName = findViewById(R.id.tvStuName);
        tvStuBranch = findViewById(R.id.tvStuBranch);
        cvStuImg = findViewById(R.id.cvStuImg);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        bottomNav.setSelected(false);

        logoutStu = findViewById(R.id.btnLogoutStu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Students");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot s: snapshot.getChildren()) {

                    if (uid.equals(s.getRef().getKey().toString())) {

                        Picasso.with(getApplicationContext()).load(s.child("Student Image URL").getValue().toString()).into(cvStuImg);
                        tvStuName.setText(s.child("Name").getValue().toString());
                        tvStuBranch.setText(s.child("Branch").getValue().toString());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    bottomNav.setSelected(false);
                    break;
                }

            }
            return true;
        }
    };

}