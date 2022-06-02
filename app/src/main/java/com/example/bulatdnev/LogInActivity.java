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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText mail,pass;
    Button login,reg;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);




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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LogFire", "signInWithEmail:success");
                            Intent main = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(main);
                            finish();
                        } else {
                            Log.w("LogFire", "signInWithEmail:failure", task.getException());
                            CreateUser();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        CreateUser();
                    }
                });

    }

    private void CreateUser() {
        db.collection("UserTime").document(mail.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if(document.getString("email").equals(mail.getText().toString())&&document.getString("pass").equals(pass.getText().toString())) {
                                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            String Uid = mAuth.getUid();

                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Uid", Uid);
                                            user.put("email", document.getString("email"));
                                            user.put("name", document.getString("name"));
                                            user.put("surname", document.getString("surname"));
                                            user.put("patronymic", document.getString("patronymic"));
                                            user.put("selected_c", document.getString("selected_c"));
                                            user.put("selected_s", document.getString("selected_s"));
                                            user.put("rang", "");

                                            db.collection("Users").document(Uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    db.collection("UserTime").document(mail.getText().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Log.d("LogFire", "signInWithEmail:success");
                                                            Intent main = new Intent(LogInActivity.this, MainActivity.class);
                                                            startActivity(main);
                                                            finish();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }



                            } else {
                                Toast.makeText(LogInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LogInActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}