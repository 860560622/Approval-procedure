package com.example.myapplication.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.User;
import com.example.myapplication.studentView.StMainActivity;
import com.example.myapplication.teacherView.TcMainActivity;

import java.util.ArrayList;
import java.util.List;

//用户界面
public class UserActivity extends AppCompatActivity {
    List<String> setItems = new ArrayList<>();
    private TextView userName;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private ListView listView;
    private ImageButton mainBtn;
    private ImageButton queryBtn;
    private ImageButton infoBtn;
    private int userId;
    private int chooseStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        databaseCreator.createDb(UserActivity.this);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        initView();
        initEvent();
    }

    private void initEvent() {
        userName.setText(dataProcessor.findUserById(userId,databaseCreator).getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_list_item_1,setItems);
        initItems();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = setItems.get(position);
                // Toast.makeText(UserActivity.this, s, Toast.LENGTH_SHORT).show();
                getSet(s);//功能实现
            }
        });
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainView(dataProcessor.findUserById(userId,databaseCreator));
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
    }

    private void initView() {
        userName = findViewById(R.id.user_name);
        listView = findViewById(R.id.list_view);
        mainBtn = findViewById(R.id.mainView);
        queryBtn = findViewById(R.id.queryView);
        infoBtn = findViewById(R.id.infoView);
    }
    private void toMainView(User user){
        Intent intent;
        if(user.getStatus() == 1){
            intent = new Intent(UserActivity.this, StMainActivity.class);
        }else {
            intent = new Intent(UserActivity.this, TcMainActivity.class);
        }
        intent.putExtra("user_id",user.getId());
        startActivity(intent);
        finish();
    }
    private void toInfoView(){
        Intent intent = new Intent(UserActivity.this,InfoActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toQueryView(){
        Intent intent = new Intent(UserActivity.this,QueryActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void getSet(String s) {
        switch (s){
            case "个人信息":{
               // Toast.makeText(UserActivity.this, s, Toast.LENGTH_SHORT).show();
                toChangeUserInfoActivity();
                //dataProcessor.findUserById(userId,databaseCreator);
                break;
            }

            case "修改密码":{
                //Toast.makeText(UserActivity.this, s, Toast.LENGTH_SHORT).show();
                toChangePswActivity();
                break;
            }

            case "风格":
            {
                final String [] style = new String[]{"日间模式","夜间模式"};
                AlertDialog.Builder builder3 = new AlertDialog.Builder(UserActivity.this);
                builder3.setSingleChoiceItems(style, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chooseStyle = which;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(chooseStyle == 0){}
                        else {
                            //
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
                break;

            }
            case "切换账号":{
                toSwitchActivity();
                break;
            }
            case "退出":{
                Toast.makeText(UserActivity.this, s, Toast.LENGTH_SHORT).show();
                toLoginActivity();
                break;
            }
            default:break;
        }
    }

    private void toSwitchActivity() {
        Intent intent = new Intent(UserActivity.this,SwitchActivity.class);
        startActivityForResult(intent,4);
    }

    private void toLoginActivity() {
        Intent intent =new Intent(UserActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toChangePswActivity() {
        Intent intent = new Intent(UserActivity.this,ChangePswActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }

    private void toChangeUserInfoActivity() {
        Intent intent = new Intent(UserActivity.this,ChangeUserInfoActivity.class);
        intent.putExtra("user_id",userId);
        startActivityForResult(intent,3);
    }

    private void initItems() {
         setItems.add("个人信息");
         setItems.add("修改密码");
        // setItems.add("风格");
         setItems.add("切换账号");
         setItems.add("退出");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 && resultCode == RESULT_OK){
            String name = data.getStringExtra("data_return");
            userName.setText(name);
        }
        if(requestCode == 4 && resultCode == RESULT_OK){
            int doit = data.getIntExtra("data_return",-1);
            if(doit == 1){finish();}
        }
    }
}