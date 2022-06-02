package com.example.bulatdnev.ui.mark.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bulatdnev.R;
import com.example.bulatdnev.ui.home.activity.NewsActivity;
import com.example.bulatdnev.ui.mark.modal.MarkModal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MarkAdapter extends ArrayAdapter<MarkModal> {

    // constructor for our list view adapter.
    public MarkAdapter(@NonNull Context context, ArrayList<MarkModal> ModalCircleArrayList) {
        super(context, 0, ModalCircleArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem_View = convertView;
        if (listitem_View == null) {
            listitem_View = LayoutInflater.from(getContext()).inflate(R.layout.maket_mark, parent, false);
        }

        MarkModal modal = getItem(position);


        TextView lesson = listitem_View.findViewById(R.id.mLesson);
        TextView time = listitem_View.findViewById(R.id.mTime);
        TextView teacher = listitem_View.findViewById(R.id.mTeacher);
        TextView mark = listitem_View.findViewById(R.id.mMark);


        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        time.setText(sdf.format(modal.getTime()));
        lesson.setText(modal.getLesson());
        teacher.setText(modal.getTeacher());
        mark.setText(modal.getMark());


//        Picasso.with(getContext()).load(modalCircle.getImg()).into(img_circle);



//        listitem_View.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent news = new Intent(getContext(), NewsActivity.class);
//                news.putExtra("id", modal.getId());
//                getContext().startActivity(news);
//            }
//        });



        return listitem_View;
    }
}
