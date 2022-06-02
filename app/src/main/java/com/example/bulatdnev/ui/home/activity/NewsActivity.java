package com.example.bulatdnev.ui.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulatdnev.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;


public class NewsActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id;
    ImageButton Back;
    TextView title,time,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Back=findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title=findViewById(R.id.nTitle);
        time=findViewById(R.id.nTime);
        content=findViewById(R.id.nContet);

        loadNews();

    }

    private void loadNews() {
        db.collection("News").document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                title.setText(document.getString("title"));
                                content.setText(document.getString("content"));
                                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                                time.setText(sdf.format(document.getLong("time")));



                            } else {
                                Toast.makeText(NewsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(NewsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}