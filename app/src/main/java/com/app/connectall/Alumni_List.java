package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Alumni_List extends AppCompatActivity {

    RecyclerView rcAlmList;
    DatabaseReference database;
    AlumniModelClass alumniList;
    ArrayList<AlumniModelClass> list;
    AlumniModelAdapter alumniModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni__list);

        rcAlmList = findViewById(R.id.rcAlmList);

        list = new ArrayList<>();

        rcAlmList.setLayoutManager(new LinearLayoutManager(Alumni_List.this, LinearLayoutManager.VERTICAL, false));

        database = FirebaseDatabase.getInstance().getReference().child("Alumni");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren())
                {

                    for(DataSnapshot s1 : s.getChildren())
                    {

                        alumniList = new AlumniModelClass();

                        alumniList.setName(s1.child("Name").getValue().toString());
                        alumniList.setMail(s1.child("Mail").getValue().toString());
                        alumniList.setBranch(s1.child("Branch").getValue().toString());
                        alumniList.setCompany(s1.child("Company").getValue().toString());
                        alumniList.setGradYear(s1.child("Graduation year").getValue().toString());
                        alumniList.setLinkedIn(s1.child("LinkedIn").getValue().toString());
                        alumniList.setExp(s1.child("Work Experience").getValue().toString());
                        alumniList.setExpNum(s1.child("Experience").getValue().toString());
                        alumniList.setExpanded(false);


                        list.add(alumniList);
                        alumniModelAdapter = new AlumniModelAdapter(Alumni_List.this, list);
                        rcAlmList.setAdapter(alumniModelAdapter);
                        //Toast.makeText(Alumni_List.this, alumniList.getName(), Toast.LENGTH_SHORT).show();
                        alumniModelAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* database.child("Corporate or Technical sector").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot s1 : snapshot.getChildren())
                {

                    alumniList = new AlumniModelClass();

                    alumniList.setName(s1.child("Name").getValue().toString());
                    alumniList.setMail(s1.child("Mail").getValue().toString());
                    alumniList.setBranch(s1.child("Branch").getValue().toString());
                    alumniList.setCompany(s1.child("Company").getValue().toString());
                    alumniList.setGradYear(s1.child("Graduation year").getValue().toString());
                    alumniList.setLinkedIn(s1.child("LinkedIn").getValue().toString());
                    alumniList.setExp(s1.child("Work Experience").getValue().toString());
                    alumniList.setExpNum(s1.child("Experience").getValue().toString());

                    list = new ArrayList<>();
                    list.add(alumniList);
                    Toast.makeText(Alumni_List.this,  alumniList.getName(), Toast.LENGTH_SHORT).show();
                    alumniModelAdapter = new AlumniModelAdapter(Alumni_List.this, list);
                    rcAlmList.setAdapter(alumniModelAdapter);
                    alumniModelAdapter.notifyDataSetChanged();
                    rcAlmList.setLayoutManager(new LinearLayoutManager(Alumni_List.this, LinearLayoutManager.VERTICAL, false));

                }

                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        /*database.child("Higher Studies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot s1 : snapshot.getChildren())
                {

                    alumniList = new AlumniModelClass();

                    alumniList.setName(s1.child("Name").getValue().toString());
                    alumniList.setMail(s1.child("Mail").getValue().toString());
                    alumniList.setBranch(s1.child("Branch").getValue().toString());
                    alumniList.setCompany(s1.child("Company").getValue().toString());
                    alumniList.setGradYear(s1.child("Graduation year").getValue().toString());
                    alumniList.setLinkedIn(s1.child("LinkedIn").getValue().toString());
                    alumniList.setExp(s1.child("Work Experience").getValue().toString());
                    alumniList.setExpNum(s1.child("Experience").getValue().toString());

                    list = new ArrayList<>();
                    list.add(alumniList);
                    Toast.makeText(Alumni_List.this,  alumniList.getName(), Toast.LENGTH_SHORT).show();
                    alumniModelAdapter = new AlumniModelAdapter(Alumni_List.this, list);
                    rcAlmList.setAdapter(alumniModelAdapter);
                    alumniModelAdapter.notifyDataSetChanged();
                    rcAlmList.setLayoutManager(new LinearLayoutManager(Alumni_List.this, LinearLayoutManager.VERTICAL, false));

                }

                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}