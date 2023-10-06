package com.example.myapplication.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.User;
import com.example.myapplication.studentView.StMainActivity;
import com.example.myapplication.teacherView.TcMainActivity;

//登录界面
public class LoginActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private Button loginBtn;
    private SharedPreferences preference;
    private CheckBox remember;
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private DataProcessor dataProcessor = new DataProcessor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
        reload();
        
    }

    private void reload() {
        boolean isRemember = preference.getBoolean("remember",false);
        if(isRemember){
            String account = preference.getString("account","");
            editText1.setText(account);
            String psw = preference.getString("psw","");
            editText2.setText(psw);
            remember.setChecked(isRemember);
        }
    }

    private void initView() {
        editText1 =findViewById(R.id.name);
        editText2 = findViewById(R.id.editTextTextPassword2);
        loginBtn=findViewById(R.id.login);
        remember = findViewById(R.id.remember);
    }

    private void initEvent() {
        preference = getSharedPreferences("config", Context.MODE_PRIVATE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText1.getText().toString();
                String psw = editText2.getText().toString();
                databaseCreator.createDb(LoginActivity.this);
                User user=dataProcessor.loginQuery(userName,psw,databaseCreator);
                Toast.makeText(LoginActivity.this,"用户名："+userName+"\n密码："+psw,Toast.LENGTH_LONG).show();
                toMainActivity(user);
            }
        });
    }

    private void toMainActivity(User user){
        //登录功能
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        //progressDialog.setTitle("正在登录中");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在登录中");
        progressDialog.show();

        if(user.getId() == -1){
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
            dialog.setTitle("登录失败！");
            dialog.setMessage("请检查用户名或密码是否错误！");
            dialog.setCancelable(false);
            dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editText2.setText("");
                    progressDialog.hide();
                }
            });
            dialog.show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put("userID",user.getId());
            dataProcessor.rememberAccount(databaseCreator,values);
            if(remember.isChecked()){
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("account",editText1.getText().toString());
                editor.putString("psw",editText2.getText().toString());
                editor.putBoolean("remember",true);
                editor.commit();
            }
            if(user.getStatus() == 1){
                Intent intent = new Intent(LoginActivity.this, StMainActivity.class);
                intent.putExtra("user_id",user.getId());
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(LoginActivity.this, TcMainActivity.class);
                intent.putExtra("user_id",user.getId());
                startActivity(intent);
                finish();
            }
        }

    }
}