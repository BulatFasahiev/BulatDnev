package com.example.bulatdnev.ui.mark;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bulatdnev.R;
import com.example.bulatdnev.ui.home.adapter.HomeAdapter;
import com.example.bulatdnev.ui.home.modal.HomeModal;
import com.example.bulatdnev.ui.mark.activity.AddMarkActivty;
import com.example.bulatdnev.ui.mark.adapter.MarkAdapter;
import com.example.bulatdnev.ui.mark.modal.MarkModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MarkFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Uid = FirebaseAuth.getInstance().getUid();
    ArrayList<MarkModal> Amodals;
    ListView list;
    ImageButton addMark;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mark, container, false);

        list=v.findViewById(R.id.markList);
        Amodals = new ArrayList<>();

        addMark=v.findViewById(R.id.addMark);
        addMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getContext(), AddMarkActivty.class);
                startActivity(add);
                Amodals.clear();
                loadList();
            }
        });

        loadList();
        return v;
    }
    private void loadList() {
        db.collection("Mark")
                .whereEqualTo("CUid", Uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                MarkModal modal = d.toObject(MarkModal.class);
                                Amodals.add(modal);
                            }
                            if (getActivity()!=null) {
                                MarkAdapter adapter = new MarkAdapter(getActivity(), Amodals);
                                list.setAdapter(adapter);
                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}