package com.example.myapplication.teacherView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Apply;
import com.example.myapplication.view.InfoActivity;
import com.example.myapplication.view.QueryActivity;
import com.example.myapplication.view.UserActivity;

import java.util.List;

//老师账号主页面
public class TcMainActivity extends AppCompatActivity {
    private NotificationManager manager;
    private Notification notification;
    private ImageButton quickRead;
    private ImageButton quickSearch;
    private ImageButton queryBtn;
    private ImageButton infoBtn;
    private ImageButton userBtn;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private int userId;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc_main);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        databaseCreator.createDb(TcMainActivity.this);
        int status = dataProcessor.findUserById(userId,databaseCreator).getStatus();
        int k  = status;
        List<Apply> applyList = dataProcessor.findApplyListByIdOfTc(userId,databaseCreator,status);
        if(dataProcessor.getReadList(databaseCreator,userId).size() != 0 ){
             getInfo();
        }
        initView();
        initEvent();
    }
    private void getInfo(){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //NotificationChannel里面有三个参数，第一id要和Notification的一致，第二个就是他的名字，可以随意设置，第三个是重要程度，需要通过NotificationManager类来设置
            NotificationChannel channel = new NotificationChannel("wang", "ReadInfo", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(TcMainActivity.this,TcReadActivity.class);
        intent.putExtra("user_id",userId);
        pendingIntent = PendingIntent.getActivities(TcMainActivity.this, 0, new Intent[]{intent}, PendingIntent.FLAG_IMMUTABLE);
        notification = new NotificationCompat.Builder(this,"wang")
                .setContentTitle("审批通知")
                .setContentText("您有新的申请待审批！")
                //通知图标应该使用纯色的图片，因为Android从5.0系统开始，对于图标设计进行了修改，所有应用程序
                //的通知栏图标，应该使用alpha图层，其实就是没有颜色的图片
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                //给小图标设置颜色
                .setColor(Color.parseColor("#ff0000"))
                //设置大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground))
                //点击通知时跳转
                .setPriority(notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                //设置点击通知后清除通知
                .setAutoCancel(true)
                //设置通知被创建的时间  不设置的话就显示的是当前系统时间
                // .setWhen()
                .build();
        manager.notify(1,notification);
    }
    private void toUserView(){
        Intent intent = new Intent(TcMainActivity.this, UserActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toQueryView(){
        Intent intent = new Intent(TcMainActivity.this, QueryActivity.class);
        intent.putExtra("user_id",userId);
        intent.putExtra("queryWay",0);
        startActivity(intent);
        finish();
    }
    private void toInfoView(){
        Intent intent = new Intent(TcMainActivity.this, InfoActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toTcReadView(){   //开始审批
        Intent intent = new Intent(TcMainActivity.this, TcReadActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
    }

    private void initEvent() {
        quickRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTcReadView();
            }
        });
        quickSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toQueryView();
            }
        });
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toQueryView();
            }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInfoView();
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserView();
            }
        });

    }

    private void initView() {
        quickRead = findViewById(R.id.quick_read_btn);
        quickSearch = findViewById(R.id.quick_search_btn);
        queryBtn = findViewById(R.id.queryView);
        infoBtn = findViewById(R.id.infoView);
        userBtn = findViewById(R.id.userView);
    }

}