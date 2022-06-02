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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bulatdnev.LogInActivity;
import com.example.bulatdnev.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private String Uid= FirebaseAuth.getInstance().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView name,surname,patronymic,mail,s,c;
    private Button exitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name=v.findViewById(R.id.pName);
        surname=v.findViewById(R.id.pSurname);
        patronymic=v.findViewById(R.id.pPatronymic);
        mail=v.findViewById(R.id.pMail);
        s=v.findViewById(R.id.pS);
        c=v.findViewById(R.id.pC);

        exitBtn=v.findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(getContext(), LogInActivity.class);
                startActivity(log);
                getActivity().finish();
            }
        });
        loadProfile();
        return v;
    }

    private void loadProfile() {
        db.collection("Users").document(Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                name.setText(document.getString("name"));
                                surname.setText(document.getString("surname"));
                                patronymic.setText(document.getString("patronymic"));
                                mail.setText(document.getString("email"));
                                s.setText(document.getString("selected_s"));
                                c.setText(document.getString("selected_c"));



                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}