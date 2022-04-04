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

public class ViewUploadedResources extends AppCompatActivity {

    RecyclerView rcUploadList;
    DatabaseReference db;
    UploadModel uploadModel;
    ArrayList<UploadModel> uploadModelArrayList;
    UploadAdapter uploadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploaded_resources);

        rcUploadList = findViewById(R.id.rvResources);
        uploadModelArrayList = new ArrayList<>();
        rcUploadList.setLayoutManager(
                new LinearLayoutManager(ViewUploadedResources.this, LinearLayoutManager.VERTICAL, false)
        );

        db = FirebaseDatabase.getInstance().getReference().child("Uploads").child("Govn sector").child("pdf");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot s: snapshot.getChildren())
                {
                    uploadModel = new UploadModel();
                    uploadModel.setName(s.child("name").getValue().toString());
                    uploadModel.setDesc(s.child("desc").getValue().toString());
                    uploadModel.setLink(s.child("url").getValue().toString());
                    uploadModel.setUploadedBy(s.child("uploaded by").getValue().toString());

                    uploadModelArrayList.add(uploadModel);
                    uploadAdapter = new UploadAdapter(ViewUploadedResources.this, uploadModelArrayList);
                    rcUploadList.setAdapter(uploadAdapter);
                    uploadAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewUploadedResources.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}