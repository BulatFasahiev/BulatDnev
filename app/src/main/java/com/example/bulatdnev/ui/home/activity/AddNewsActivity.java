package com.example.bulatdnev.ui.home.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bulatdnev.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewsActivity extends AppCompatActivity {

    EditText title,content;
    TextView time;
    ImageButton edit;
    Button send;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        initElemetns();
    }

    private void initElemetns() {
        title=findViewById(R.id.addTitle);
        content=findViewById(R.id.addContent);
        time=findViewById(R.id.addTime);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        time.setText(sdf.format(System.currentTimeMillis()));

        edit=findViewById(R.id.addTimeBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerStart();
            }
        });
        send=findViewById(R.id.addSendBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().isEmpty()){
                    title.setError("Пусто");
                }else if (content.getText().toString().isEmpty()) {
                    content.setError("Пусто");
                } else {
                    sendNews();
                }
            }
        });
    }
    private Date dateMilli;
    private void sendNews() {
        String Uid = FirebaseAuth.getInstance().getUid();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        try {
            dateMilli = sdf.parse(time.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Map<String, Object> news = new HashMap<>();
        news.put("CUid", Uid);
        news.put("title", title.getText().toString());
        news.put("content", content.getText().toString());
        news.put("time", dateMilli.getTime());

        db.collection("News").add(news).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                finish();
            }
        });
    }

    private void datePickerStart(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;

                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                        try {
                            Date date = sdf.parse(date_time+" "+hourOfDay + ":" + minute);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        time.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = (monthOfYear+1)+"";
                        if(monthOfYear < 9) {
                            month="0"+(monthOfYear+1);
                        }
                        String day = dayOfMonth+"";
                        if(dayOfMonth < 10) {
                            day="0"+dayOfMonth;
                        }
                        date_time = day + "." + month + "." + year;
                        timePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}