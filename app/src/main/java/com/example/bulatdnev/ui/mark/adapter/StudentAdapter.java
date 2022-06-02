package com.example.bulatdnev.ui.mark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bulatdnev.R;
import com.example.bulatdnev.ui.mark.modal.StudentModal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<StudentModal> {

    // constructor for our list view adapter.
    public StudentAdapter(@NonNull Context context, ArrayList<StudentModal> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem_View = convertView;
        if (listitem_View == null) {
            listitem_View = LayoutInflater.from(getContext()).inflate(R.layout.student_maket, parent, false);
        }


        StudentModal modal = getItem(position);


        TextView fullname = listitem_View.findViewById(R.id.studentFullname);
        fullname.setText(modal.getSurname()+" "+modal.getSurname());


        return listitem_View;
    }

}
