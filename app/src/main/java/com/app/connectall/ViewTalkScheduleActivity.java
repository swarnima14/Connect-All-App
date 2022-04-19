package com.app.connectall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewTalkScheduleActivity extends AppCompatActivity {

    RecyclerView rcTalkList;
    DatabaseReference db;
    TalkScheduleModel talkScheduleModel;
    ArrayList<TalkScheduleModel> talkScheduleModelArrayList;
    TalkScheduleAdapter talkScheduleAdapter;
    String domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_talk_schedule);

        domain = getIntent().getStringExtra("domain");

        rcTalkList = findViewById(R.id.rvSchedule);
        talkScheduleModelArrayList = new ArrayList<>();
        rcTalkList.setLayoutManager(
                new LinearLayoutManager(ViewTalkScheduleActivity.this, LinearLayoutManager.VERTICAL, false)
        );

        db = FirebaseDatabase.getInstance().getReference().child("Talk Schedules").child(domain);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot s: snapshot.getChildren())
                {
                    talkScheduleModel = new TalkScheduleModel();

                    talkScheduleModel.setTopic(s.child("Topic").getValue().toString());
                    talkScheduleModel.setHost(s.child("Scheduled by").getValue().toString());
                    talkScheduleModel.setPlatform(s.child("Platform").getValue().toString());
                    talkScheduleModel.setDate(s.child("Date").getValue().toString());
                    talkScheduleModel.setTime(s.child("Time").getValue().toString());
                    talkScheduleModel.setLink(s.child("Meet Link").getValue().toString());

                    talkScheduleModelArrayList.add(talkScheduleModel);
                    talkScheduleAdapter = new TalkScheduleAdapter(ViewTalkScheduleActivity.this, talkScheduleModelArrayList);
                    rcTalkList.setAdapter(talkScheduleAdapter);
                    talkScheduleAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewTalkScheduleActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}