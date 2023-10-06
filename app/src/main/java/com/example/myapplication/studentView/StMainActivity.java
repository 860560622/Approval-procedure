package com.example.myapplication.studentView;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Apply;
import com.example.myapplication.teacherView.TcMainActivity;
import com.example.myapplication.teacherView.TcReadActivity;
import com.example.myapplication.view.InfoActivity;
import com.example.myapplication.view.LoginActivity;
import com.example.myapplication.view.QueryActivity;
import com.example.myapplication.view.UserActivity;

import java.util.List;

//学生账号主页面
public class StMainActivity extends AppCompatActivity {
    private ImageButton quickApply;
    private ImageButton quickSearch;
    private ImageButton queryBtn;
    private ImageButton infoBtn;
    private ImageButton userBtn;
    private int userId;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private PendingIntent pendingIntent;
    private NotificationManager manager;
    private Notification notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_main);
        Intent intent = getIntent();
        databaseCreator.createDb(StMainActivity.this);
        userId = intent.getIntExtra("user_id",-1);
      //  Log.d("StMainActivity","enter StMainActivity");
        initView();
        initEvent();
    }
    private void getInfo(){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //NotificationChannel里面有三个参数，第一id要和Notification的一致，第二个就是他的名字，可以随意设置，第三个是重要程度，需要通过NotificationManager类来设置
            NotificationChannel channel = new NotificationChannel("ST", "ApplyInfo", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(StMainActivity.this, InfoActivity.class);
        intent.putExtra("user_id",userId);
        pendingIntent = PendingIntent.getActivities(StMainActivity.this, 0, new Intent[]{intent}, PendingIntent.FLAG_IMMUTABLE);
        notification = new NotificationCompat.Builder(this,"ST")
                .setContentTitle("申请通知")
                .setContentText("您的申请有新的进度！点击即可查看通知")
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
        manager.notify(2,notification);
    }
    private void toUserView(){  //用户界面
        Intent intent = new Intent(StMainActivity.this, UserActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toQueryView(int way){ //查询界面
        Intent intent = new Intent(StMainActivity.this, QueryActivity.class);
        intent.putExtra("user_id",userId);
        intent.putExtra("queryWay",way);
        startActivity(intent);
        finish();
    }
    private void toInfoView(){ //通知界面
        Intent intent = new Intent(StMainActivity.this, InfoActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toStartApplyView(){   //开始新申请
        Intent intent = new Intent(StMainActivity.this, StStartApplyActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
    }

    private void initEvent() {
        quickApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              toStartApplyView();
            }
        });
        quickSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toQueryView(1);
            }
        });
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toQueryView(0);
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
        List<Apply> applies =dataProcessor.getInfoList(databaseCreator,userId,1);
        int x = applies.size();
        if (applies.size() != 0){
            getInfo();
        }
    }

    private void initView() {
        quickApply = findViewById(R.id.quick_apply_btn);
        quickSearch = findViewById(R.id.quick_search_btn);
        queryBtn = findViewById(R.id.queryView);
        infoBtn = findViewById(R.id.infoView);
        userBtn = findViewById(R.id.userView);
    }
}