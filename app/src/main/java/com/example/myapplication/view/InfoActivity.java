package com.example.myapplication.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Apply;
import com.example.myapplication.model.ApplyAdapter;
import com.example.myapplication.model.InfoAdapter;
import com.example.myapplication.model.User;
import com.example.myapplication.studentView.StMainActivity;
import com.example.myapplication.studentView.StStartApplyActivity;
import com.example.myapplication.teacherView.TcMainActivity;
import com.example.myapplication.teacherView.TcReadActivity;

import java.util.List;

//通知界面
public class InfoActivity extends AppCompatActivity {
    private List<Apply> infoList;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private ImageButton queryBtn;
    private ImageButton mainBtn;
    private ImageButton userBtn;
    private Button readAll;
    private ListView listView;
    private int userId;
    private int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        databaseCreator.createDb(InfoActivity.this);////
        status = dataProcessor.findUserById(userId,databaseCreator).getStatus();
        initInfoView();
        initView();
        initEvent();
    }

    private void initInfoView() {
        infoList = dataProcessor.getInfoList(databaseCreator,userId,status);
    }

    private void toUserView(){
        Intent intent = new Intent(InfoActivity.this, UserActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toQueryView(){
        Intent intent = new Intent(InfoActivity.this, QueryActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toMainView(User user){
        Intent intent;
        if(user.getStatus() == 1){
            intent = new Intent(InfoActivity.this, StMainActivity.class);
        }else {
            intent = new Intent(InfoActivity.this, TcMainActivity.class);
        }
        intent.putExtra("user_id",user.getId());
        startActivity(intent);
        finish();
    }

    private void initEvent() {
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toQueryView();
            }
        });
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainView(dataProcessor.findUserById(userId,databaseCreator));
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserView();
            }
        });
        InfoAdapter infoAdapter = new InfoAdapter(InfoActivity.this,R.layout.info_list,infoList,databaseCreator,userId);
        infoAdapter.notifyDataSetChanged();
        listView.setAdapter(infoAdapter);
        if(status == 1){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Apply apply = infoList.get(position);
                formDialog(apply);
                ContentValues values = new ContentValues();
                values.put("confirm",1);
                dataProcessor.updateAppleData(databaseCreator,values,apply.getId());
                infoList.remove(position);
                infoAdapter.notifyDataSetChanged();
            }
        });
        }else {
            toTcReadActivity();
        }
        readAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status == 1){
                    for (int i = 0; i < infoList.size(); i++) {
                        ContentValues values = new ContentValues();
                        values.put("confirm",1);
                        dataProcessor.updateAppleData(databaseCreator,values,infoList.get(i).getId());
                    }
                }
                for (int i = infoList.size()-1; i > -1; i--) {
                    infoList.remove(i);
                }
                infoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void toTcReadActivity() {
        Intent intent = new Intent(InfoActivity.this, TcReadActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
    }

    private void formDialog(Apply apply) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(InfoActivity.this);
        dialog.setTitle(dataProcessor.findClassById(apply.getClassID(),databaseCreator).getName());
        dialog.setMessage(apply.getStringState());
        dialog.setCancelable(false);
        dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    private void initView() {
        queryBtn = findViewById(R.id.queryView);
        mainBtn = findViewById(R.id.mainView);
        userBtn = findViewById(R.id.userView);
        listView = findViewById(R.id.info_list);
        readAll = findViewById(R.id.read_all_info);
    }

}