package com.example.bulatdnev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SpalshScreen extends AppCompatActivity {

    private String Uid= FirebaseAuth.getInstance().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        if (Uid==null) {
            Intent log = new Intent(SpalshScreen.this, LogInActivity.class);
            startActivity(log);
        } else {
            db.collection("Users").document(Uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    if (document.getString("rang").equals("admin")) {
                                        Intent main = new Intent(SpalshScreen.this, MainActivity.class);
                                        startActivity(main);
                                    }else {
                                        Toast.makeText(SpalshScreen.this, "Вы не являетесь админов", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();
                                        Intent log = new Intent(SpalshScreen.this, LogInActivity.class);
                                        startActivity(log);
                                    }

                                } else {
                                    Toast.makeText(SpalshScreen.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SpalshScreen.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        finish();
    }
}