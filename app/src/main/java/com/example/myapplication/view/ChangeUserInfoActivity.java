package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.User;

public class ChangeUserInfoActivity extends AppCompatActivity {
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private int userId;
    private String name;
    private Button changeBtn;
    private EditText userName;
    private EditText userTele;
    private RadioButton female;
    private RadioButton male;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        databaseCreator.createDb(ChangeUserInfoActivity.this);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        initView();
        initEvent();
    }

    private void initEvent() {
        User user = dataProcessor.findUserById(userId,databaseCreator);
        userName.setText(user.getName());
        name = user.getName();
        userTele.setText(user.getTelephone());
        if(user.getSex() == 0){
            female.setChecked(true);
        }else {
            male.setChecked(true);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserActivity();
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeUserInfoActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                ContentValues values = new ContentValues();
                String a = userName.getText().toString();
                name = a;
                values.put("name",a);
                a = userTele.getText().toString();
                values.put("telephone",a);
                if(female.isChecked()){
                    values.put("sex",0);
                }else {
                    values.put("sex",1);
                }
                dataProcessor.changeUserInfoById(values,userId,databaseCreator);
                values.clear();
                toUserActivity();
            }

        });
    }

    private void toUserActivity() {
        Intent intent = new Intent();
        intent.putExtra("data_return",name);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void initView() {
        changeBtn = findViewById(R.id.change_btn);
        userName = findViewById(R.id.user_name);
        userTele = findViewById(R.id.user_telephone);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);
        backBtn = findViewById(R.id.backBtn);
    }
}