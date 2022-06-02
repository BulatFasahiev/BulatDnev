package com.example.bulatdnev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ProgressDialog dialog;

    EditText mail,pass;
    Button login,reg;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Авторизация");
        dialog.setMessage("Обработка...");

        initAll();
    }
    private void initAll() {
        mAuth = FirebaseAuth.getInstance();

        mail=findViewById(R.id.editMail);
        pass=findViewById(R.id.editPass);

        login=findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mail.getText().toString();
                password = pass.getText().toString();
                if(email.trim().equalsIgnoreCase("")) {
                    mail.setError("Введите почту");
                    mail.requestFocus();
                }else if(password.trim().equalsIgnoreCase("")){
                    pass.setError("Введите пароль");
                    pass.requestFocus();
                }else if(password.length()<5){
                    pass.setError("Короткий пароль");
                    pass.requestFocus();
                }else{
                    authentication();
                }
            }
        });

    }

    private void authentication() {
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LogFire", "signInWithEmail:success");
                            Intent main = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(main);
                            finish();
                            dialog.dismiss();
                        } else {
                            Log.w("LogFire", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

    }


}