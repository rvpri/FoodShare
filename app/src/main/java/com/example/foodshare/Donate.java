package com.example.foodshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Donate extends AppCompatActivity {
    EditText food, date, serves, time;
    Button insert;
    DBHelper DB;
    private String mailid;
    DatePickerDialog.OnDateSetListener setListener;
    int t1Hour, t1Minute;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        food = findViewById(R.id.food);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        serves = findViewById(R.id.serves);
        insert = findViewById(R.id.btnInsert);
        checkBox=findViewById(R.id.checkBox);
        DB = new DBHelper(this);

        Intent intent1 = getIntent();
        mailid = intent1.getStringExtra("message");

        insert.setOnClickListener(view -> {
            String food1 = food.getText().toString();
            String time1 = time.getText().toString();
            String serves1 = serves.getText().toString();
            String  date1= date.getText().toString();


            if (food1.equals("") || time1.equals("") || serves1.equals("") ||date1.equals(""))
                Toast.makeText(Donate.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            else{
                if(checkBox.isChecked())
                {
                Boolean checkinsertdata = DB.insertdonationdata(mailid, food1,date1, time1, serves1);
                if (checkinsertdata) {
                    Toast.makeText(Donate.this, "Thank you for Donating", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Donate.this, Activity5.class);
                    intent.putExtra("message_key", mailid);
                    startActivity(intent);
                }
                else
                    Toast.makeText(Donate.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Donate.this, "Click the checkbox", Toast.LENGTH_SHORT).show();
             }


        });

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(v -> {
            int style = AlertDialog.THEME_HOLO_LIGHT;
            DatePickerDialog datePickerDialog = new DatePickerDialog(Donate.this, style, setListener, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            datePickerDialog.show();
        });

        setListener = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 + 1;
            String Date = dayOfMonth + "/" + month1 + "/" + year1;
            date.setText(Date);
        };

        time.setOnClickListener(v -> {
            int style = AlertDialog.THEME_HOLO_LIGHT;
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    Donate.this,
                    style,
                    (view, hourOfDay, minute) -> {
                        t1Hour = hourOfDay;
                        t1Minute = minute;
                        String Time = t1Hour + ":" + t1Minute;
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat f24Hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24Hours.parse(Time);
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            time.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, 12, 0, false
            );

            timePickerDialog.updateTime(t1Hour, t1Minute);
            timePickerDialog.show();
        });


    }
}




