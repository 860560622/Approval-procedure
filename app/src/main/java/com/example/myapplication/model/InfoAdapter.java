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

public class InfoAdapter extends ArrayAdapter<Apply> {
    private int resourceId;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator ;
    private int id;
    public InfoAdapter(Context context, int textViewResourceId, List<Apply> object, DatabaseCreator databaseCreate,int userId){
        super(context,textViewResourceId,object);
        resourceId = textViewResourceId;
        databaseCreator =databaseCreate;
        id = userId;
    }
    public View getView(int position, View coverView, ViewGroup parent){
        Apply apply = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView = view.findViewById(R.id.apply_state);
        TextView textView = view.findViewById(R.id.apply_name);
        imageView.setImageResource(android.R.drawable.btn_star_big_on);//
        textView.setText(dataProcessor.findClassById(apply.getClassID(),databaseCreator).getName());
        TextView tip = view.findViewById(R.id.tip);
        int status = dataProcessor.findUserById(id,databaseCreator).getStatus();
        if(status == 1){
            tip.setText("您的申请有新进度，点击查看");
        }else {
            tip.setText("您有新的申请待审批，点击查看");
        }
        return view;
    }
}
