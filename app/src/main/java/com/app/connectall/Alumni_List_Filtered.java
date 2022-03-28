package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Alumni_List_Filtered extends AppCompatActivity {

    TextView tv1;
    RecyclerView rcAlmListFiltered;
    DatabaseReference database;
    AlumniModelClass alumniList;
    ArrayList<AlumniModelClass> list;
    AlumniModelAdapter alumniModelAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni__list__filtered);

        String domain = getIntent().getStringExtra("domain");

        tv1 = findViewById(R.id.tv1);
        tv1.setText("ALUMNI LIST: " + domain);

        rcAlmListFiltered = findViewById(R.id.rcAlmListFiltered);
        list = new ArrayList<>();
        rcAlmListFiltered.setLayoutManager(new LinearLayoutManager(Alumni_List_Filtered.this, LinearLayoutManager.VERTICAL, false));

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Alumni").child(domain);

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren())
                {

                        alumniList = new AlumniModelClass();

                        alumniList.setName(s.child("Name").getValue().toString());
                        alumniList.setMail(s.child("Mail").getValue().toString());
                        alumniList.setBranch(s.child("Branch").getValue().toString());
                        alumniList.setCompany(s.child("Company").getValue().toString());
                        alumniList.setGradYear(s.child("Graduation year").getValue().toString());
                        alumniList.setLinkedIn(s.child("LinkedIn").getValue().toString());
                        alumniList.setExp(s.child("Work Experience").getValue().toString());
                        alumniList.setExpNum(s.child("Experience").getValue().toString());
                        alumniList.setExpanded(false);


                        list.add(alumniList);
                        alumniModelAdapter = new AlumniModelAdapter(Alumni_List_Filtered.this, list);
                        rcAlmListFiltered.setAdapter(alumniModelAdapter);
                        //Toast.makeText(Alumni_List.this, alumniList.getName(), Toast.LENGTH_SHORT).show();
                        alumniModelAdapter.notifyDataSetChanged();




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}