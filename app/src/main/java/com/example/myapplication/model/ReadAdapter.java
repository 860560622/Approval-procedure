package com.example.myapplication.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;

import java.util.List;

public class ReadAdapter extends ArrayAdapter<Apply> {
    private int resourceId;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator;

    public ReadAdapter(Context context, int textViewResourceId, List<Apply> object, DatabaseCreator databaseCreate) {
        super(context, textViewResourceId, object);
        resourceId = textViewResourceId;
        databaseCreator = databaseCreate;
    }

    public View getView(int position, View coverView, ViewGroup parent) {
        Apply apply = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView imageView = view.findViewById(R.id.apply_state);
        TextView textView = view.findViewById(R.id.apply_name);
        //imageView.setImageResource(android.R.drawable.btn_star_big_on);
        textView.setText(dataProcessor.findClassById(apply.getClassID(), databaseCreator).getName());
        return view;
    }
}
