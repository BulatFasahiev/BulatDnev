package com.example.bulatdnev.ui.home.adapter;

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
import com.example.bulatdnev.ui.home.modal.HomeModal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<HomeModal> {

    // constructor for our list view adapter.
    public HomeAdapter(@NonNull Context context, ArrayList<HomeModal> ModalCircleArrayList) {
        super(context, 0, ModalCircleArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem_View = convertView;
        if (listitem_View == null) {
            listitem_View = LayoutInflater.from(getContext()).inflate(R.layout.maket_home, parent, false);
        }

        HomeModal modal = getItem(position);


        TextView title = listitem_View.findViewById(R.id.mLesson);
        TextView time = listitem_View.findViewById(R.id.mTime);


        title.setText(modal.getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        time.setText(sdf.format(modal.getTime()));


//        Picasso.with(getContext()).load(modalCircle.getImg()).into(img_circle);



        listitem_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent news = new Intent(getContext(), NewsActivity.class);
                news.putExtra("id", modal.getId());
                getContext().startActivity(news);
            }
        });



        return listitem_View;
    }
}
