package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    MaterialButton alumni, student;
    Toolbar toolbar;
    AppBarLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act4_selection);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.appBar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("CONNECT-ALL");
        tabLayout.setGravity(TabLayout.GRAVITY_FILL);

        alumni = findViewById(R.id.btnSelAlumni);
        student = findViewById(R.id.btnSelStudent);
        SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        alumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*editor.putString("type", "alumni");
                editor.apply();*/
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                //finish();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*editor.putString("type", "student");
                editor.apply();*/
                startActivity(new Intent(MainActivity.this, StudentProfileActivity.class));
                //finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuSignOut: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*SharedPreferences preferences = getSharedPreferences("TYPE_PREF",MODE_PRIVATE);

        String type = preferences.getString("type","not defined");
        if(type.equals("alumni"))
        {
            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
            finish();
        }
        if(type.equals("student"))
        {
            startActivity(new Intent(MainActivity.this, StudentProfileActivity.class));
            finish();
        }*/
    }
}