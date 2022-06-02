package com.example.bulatdnev.ui.mark.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bulatdnev.R;
import com.example.bulatdnev.ui.mark.adapter.StudentAdapter;
import com.example.bulatdnev.ui.mark.modal.StudentModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMarkActivty extends AppCompatActivity {

    EditText lesson;
    private TextView teacher, time;
    private String CUid = FirebaseAuth.getInstance().getUid();
    ImageButton edit;
    Button save;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Uid = FirebaseAuth.getInstance().getUid();

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;

    TextView amStudent;
    private String st = "";
    Spinner listStudents;
    private List<String> subjects;
    private List<String> subjects_id;
    private ArrayAdapter<String> adapter;
    Spinner corurse,special,mark;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark_activty);

        initElements();
    }

    private void getTeacher() {
        db.collection("Users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(document.getString("rang").equals("admin")) {
                            teacher.setText(document.getString("name") + " " + document.getString("patronymic"));
                        }
                    } else {
                        Toast.makeText(AddMarkActivty.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddMarkActivty.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initElements() {
        lesson=findViewById(R.id.amLesson);
        mark=findViewById(R.id.amMark);
        teacher=findViewById(R.id.amTeacher);
        time=findViewById(R.id.amTime);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        time.setText(sdf.format(System.currentTimeMillis()));

        edit=findViewById(R.id.amTimeBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerStart();
            }
        });

        amStudent=findViewById(R.id.amStudent);
        listStudents=findViewById(R.id.listStudents);
        subjects = new ArrayList<>();
        subjects_id = new ArrayList<>();
        subjects.add("Выберите ученика/студента");
        subjects_id.add("");
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listStudents.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                amStudent.setText(item);
                st=subjects_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        listStudents.setOnItemSelectedListener(itemSelectedListener);

        corurse=findViewById(R.id.sCourse);
        special=findViewById(R.id.sSpec);

        search=findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjects.clear();
                subjects_id.clear();
                subjects.add("Выберите ученика/студента");
                subjects_id.add("");
                st="";
                amStudent.setText(st);
                getStudets(corurse.getSelectedItem().toString(), special.getSelectedItem().toString());
            }
        });

        save=findViewById(R.id.amSaveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lesson.getText().toString().isEmpty()) {
                    lesson.setError("Пусто");
                } else if(teacher.getText().toString().isEmpty()){
                    Toast.makeText(AddMarkActivty.this, "Ошибка", Toast.LENGTH_LONG).show();
                } else if(st.isEmpty()) {
                    Toast.makeText(AddMarkActivty.this, "Выберите ученика/студента", Toast.LENGTH_LONG).show();
                } else{
                    saveMark();
                }
            }
        });
        getTeacher();
    }


    private void getStudets(String c, String s) {
        db.collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                String Sc = d.get("selected_c").toString();
                                String Ss = d.get("selected_s").toString();
                                String rang = d.get("rang").toString();
                                if (Sc.equals(c) && Ss.equals(s) && rang.isEmpty()) {
                                    StudentModal homeModal = d.toObject(StudentModal.class);
                                    homeModal.setId(d.getId());

                                    String subject = homeModal.getSurname()+" "+homeModal.getName();
                                    subjects.add(subject);
                                    subjects_id.add(homeModal.getId());
                                }

                            }

                            listStudents.setSelection(0);

                            adapter.notifyDataSetChanged();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMarkActivty.this, "onFailure", Toast.LENGTH_LONG).show();

                    }
                });



    }

    private Date dateMilli;
    private void saveMark() {
        String Uid = FirebaseAuth.getInstance().getUid();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        try {
            dateMilli = sdf.parse(time.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Map<String, Object> marks = new HashMap<>();
        marks.put("CUid", Uid);
        marks.put("teacher", teacher.getText().toString());
        marks.put("lesson", lesson.getText().toString());
        marks.put("mark", mark.getSelectedItem());
        marks.put("Uid", st);
        marks.put("time", dateMilli.getTime());
        marks.put("CUid", CUid);
        db.collection("Mark").add(marks).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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