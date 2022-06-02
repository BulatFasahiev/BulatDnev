package com.example.bulatdnev.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bulatdnev.R;
import com.example.bulatdnev.ui.home.adapter.HomeAdapter;
import com.example.bulatdnev.ui.home.modal.HomeModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<HomeModal> Amodals;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        list=v.findViewById(R.id.homeList);
        Amodals = new ArrayList<>();




        db.collection("News")
                .orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                HomeModal modal = d.toObject(HomeModal.class);
                                modal.setId(d.getId());
                                Amodals.add(modal);
                            }
                            if (getActivity()!=null) {
                                HomeAdapter adapter = new HomeAdapter(getActivity(), Amodals);
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

        return v;
    }
}