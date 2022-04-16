package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileActivity extends AppCompatActivity {

    MaterialButton btnViewList, btnSearchAlm, btnResources, btnGovSel, btnTechSel, btnHigherSel;
    RadioGroup radioGroup;
    String uid;

    TextView tvStuName, tvStuBranch;
    CircleImageView cvStuImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_student_profile);

        /*SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        String type = preferences.getString("type", null);
        Toast.makeText(this, "type: "+type, Toast.LENGTH_SHORT).show();*/



        initialise();

        /*btnViewList.setOnClickListener(new View.OnClickListener() {
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
        });*/

        /*btnResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this, ViewUploadedResources.class));
            }
        });*/

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

    }

    private void initialise() {

        /*btnViewList = findViewById(R.id.btnViewList);
        btnSearchAlm = findViewById(R.id.btnSearchAlm);
        btnResources = findViewById(R.id.btnViewResources);*/
        btnGovSel = findViewById(R.id.btnGovSelection);
        btnTechSel = findViewById(R.id.btnTechSelection);
        btnHigherSel = findViewById(R.id.btnHigherSelection);
        //radioGroup = findViewById(R.id.radioGroup);

        tvStuName = findViewById(R.id.tvStuName);
        tvStuBranch = findViewById(R.id.tvStuBranch);
        cvStuImg = findViewById(R.id.cvStuImg);

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
}