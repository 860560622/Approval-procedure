package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.controller.Upload;
import com.example.myapplication.model.User;
import com.example.myapplication.teacherView.TcMainActivity;

import java.io.IOException;

//闪屏界面
public class SplashActivity extends AppCompatActivity {
    private Button skipBtn;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);
        initView();
        initEvent();
        handler.postDelayed(runnable,3000);
        DatabaseCreator databaseCreator = new DatabaseCreator();
        databaseCreator.createDb(SplashActivity.this);
        databaseCreator.addData();
    }

    private void initEvent() {
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SplashActivity.this,"欢迎来到申请与审批系统",Toast.LENGTH_SHORT).show();
                toLoginActivity();
                handler.removeCallbacks(runnable);
            }
        });
    }

    private void initView() {
        skipBtn = findViewById(R.id.skip_btn);
    }

    public void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}