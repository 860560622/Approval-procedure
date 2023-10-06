package com.example.myapplication.view;



import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Apply;
import com.example.myapplication.model.Classes;

//点击申请可查看详细信息
public class ApplyInfoActivity extends AppCompatActivity {
    private DataProcessor dataProcessor = new DataProcessor();
    private ImageButton backBtn;
    private TextView className;
    private TextView tcName;
    private TextView mgName;
    private TextView applyReason;
    private TextView state;
    private TextView backReason;
    private TextView applicant;
    private Button check;
    private int applyId;
    private int queryWay;
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_info);
        Intent intent = getIntent();
        applyId = intent.getIntExtra("apply_id",-1);
        queryWay = intent.getIntExtra("queryWay",-1);
        databaseCreator.createDb(ApplyInfoActivity.this);
        initView();
        initEvent(dataProcessor.findApplyById(applyId,databaseCreator));
       // Log.d("ApplyInfoActivity",id+"");

    }

    private void initEvent(Apply apply) {
        if(queryWay == 0){
            check.setVisibility(View.INVISIBLE);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return",0);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        Classes classes = dataProcessor.findClassById(apply.getClassID(),databaseCreator);
        className.setText(classes.getName());
        tcName.setText(dataProcessor.findUserById(classes.getTeacherID(),databaseCreator).getName());
        mgName.setText(dataProcessor.findUserById(classes.getManagerID(),databaseCreator).getName());
        applyReason.setText(apply.getReason());
        state.setText(apply.getStringState());
        backReason.setText(apply.getBackReason());
        applicant.setText(dataProcessor.findUserById(apply.getUserID(),databaseCreator).getName());
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(queryWay == 1  && (apply.getState() == 4 || apply.getState() == 5)){
                    ContentValues values = new ContentValues();
                    values.put("confirm",1);
                  dataProcessor.updateAppleData(databaseCreator,values,applyId);
                  intent.putExtra("data_return",1);
                }
                else {
                    intent.putExtra("data_return",0);
                }
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }

    private void initView() {
        className = findViewById(R.id.class_name);
        tcName = findViewById(R.id.teacher_name);
        mgName = findViewById(R.id.manager_name);
        applyReason = findViewById(R.id.apply_reason);
        state = findViewById(R.id.apply_state);
        backReason = findViewById(R.id.read_reason);
        backBtn = findViewById(R.id.backBtn);
        applicant = findViewById(R.id.apply_st_name);
        check = findViewById(R.id.check);
    }
}