package com.example.bulatdnev.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulatdnev.LogInActivity;
import com.example.bulatdnev.R;
import com.example.bulatdnev.SpalshScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText mail,surname,name,patron,pass,repass;
    Spinner c,s;
    Button create,exitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);

        mAuth = FirebaseAuth.getInstance();



        mail=v.findViewById(R.id.apMail);
        surname=v.findViewById(R.id.apSurname);
        name=v.findViewById(R.id.apName);
        patron=v.findViewById(R.id.apPatronymic);
        pass=v.findViewById(R.id.apPass);
        repass=v.findViewById(R.id.apRepass);

        c=v.findViewById(R.id.apC);
        s=v.findViewById(R.id.apS);


        create=v.findViewById(R.id.apCreate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mail.getText().toString().isEmpty() &&
                        surname.getText().toString().isEmpty() &&
                        name.getText().toString().isEmpty() &&
                        patron.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_LONG).show();

                }else if(pass.getText().length()  <= 6 && pass.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Пароли не совподают", Toast.LENGTH_LONG).show();
                } else if(pass.getText().toString().equals(repass.getText().toString())) {
                    createAcc();
                }
            }
        });
        exitBtn=v.findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getContext(), LogInActivity.class);
                mAuth.signOut();
                startActivity(login);
                getActivity().finish();
            }
        });

        return v;
    }

    private void createAcc() {
        Map<String, Object> user = new HashMap<>();
        user.put("Uid", "");
        user.put("email", mail.getText().toString());
        user.put("name", name.getText().toString());
        user.put("surname", surname.getText().toString());
        user.put("patronymic", patron.getText().toString());
        user.put("selected_c", c.getSelectedItem().toString());
        user.put("selected_s", s.getSelectedItem().toString());
        user.put("rang", "");
        user.put("pass", pass.getText().toString());


        db.collection("UserTime").document(mail.getText().toString()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                mail.setText("");
                name.setText("");
                surname.setText("");
                patron.setText("");
                pass.setText("");
                repass.setText("");
            }
        });



    }


}