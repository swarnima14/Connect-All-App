package com.app.connectall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class TalkActivity extends AppCompatActivity {

    TextInputEditText etTopic, etPlatform, etDate, etTime, etLink;
    MaterialButton btnSchedule;
    DatePickerDialog datePickerDialog;
    TimePickerDialog picker;
    String uid, name, domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        initialise();
        name = getIntent().getStringExtra("name");
        domain = getIntent().getStringExtra("domain");

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }

        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = etTopic.getText().toString().trim();
                String platform  = etPlatform.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                String time = etTime.getText().toString().trim();
                String link = etLink.getText().toString().trim();

                if(topic.isEmpty() || platform.isEmpty() || date.isEmpty() || time.isEmpty() || link.isEmpty())
                    Toast.makeText(TalkActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                else
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Talk Schedules").child(domain);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Topic", topic);
                    map.put("Platform", platform);
                    map.put("Date", date);
                    map.put("Time", time);
                    map.put("Meet Link", link);
                    map.put("Scheduled by", name);

                    ref.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(TalkActivity.this, "Talk scheduled successfully", Toast.LENGTH_SHORT).show();
                            etTopic.setText("");
                            etPlatform.setText("");
                            etDate.setText("");
                            etTime.setText("");
                            etLink.setText("");
                        }
                    });
                }
            }
        });

    }

    private void setTime() {
        Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(TalkActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        updateTime(sHour, sMinute);
                    }
                }, hour, minutes, false);
        picker.show();
    }

    private void initialise() {
        etTopic = findViewById(R.id.etTopic);
        etPlatform = findViewById(R.id.etPlatform);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etLink = findViewById(R.id.etMeetLink);
        btnSchedule = findViewById(R.id.btnSchedule);
    }

    private void setDate(){
        // calender class's instance and get current date , month and year from calender
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(TalkActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        etDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        etTime.setText(aTime);
    }
}