package com.example.bulatdnev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SpalshScreen extends AppCompatActivity {

    private String Uid= FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        if (Uid==null) {
            Intent log = new Intent(SpalshScreen.this, LogInActivity.class);
            startActivity(log);
        } else {
            Intent main = new Intent(SpalshScreen.this, MainActivity.class);
            startActivity(main);
        }
    }
}