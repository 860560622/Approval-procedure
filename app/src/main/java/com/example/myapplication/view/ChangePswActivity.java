package com.example.myapplication.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.User;

public class ChangePswActivity extends AppCompatActivity {
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private int userId;
    private EditText beforePsw;
    private EditText newPsw;
    private EditText checkPsw;
    private TextView uAccount;
    private Button changeBtn;
    private ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        databaseCreator.createDb(ChangePswActivity.this);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        initView();
        initEvent();
    }

    private void initEvent() {
        User user = dataProcessor.findUserById(userId,databaseCreator);
        uAccount.setText(user.getAccount());
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bf = beforePsw.getText().toString();
                String nw = newPsw.getText().toString();
                String ck = checkPsw.getText().toString();
                if(!bf.equals(user.getPassword())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChangePswActivity.this);
                    dialog.setTitle("原密码错误！");
                    dialog.setMessage("请检查原输入密码是否错误！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            beforePsw.setText("");
                        }
                    });
                    dialog.show();
                }else if(!ck.equals(nw)){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChangePswActivity.this);
                    dialog.setTitle("新密码与确认密码不一致！");
                    dialog.setMessage("请检查新密码和确认密码是否错误！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkPsw.setText("");
                        }
                    });
                    dialog.show();
                }else { //修改密码
                    Toast.makeText(ChangePswActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("password",ck);
                    dataProcessor.changeUserInfoById(values,userId,databaseCreator);
                    toLoginActivity();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserActivity();
            }
        });
    }

    private void toUserActivity() {
        Intent intent = new Intent(ChangePswActivity.this,UserActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }

    private void toLoginActivity() {
     //   Log.d("ChangePswActivity","QWQ");
        Intent intent =new Intent(ChangePswActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        beforePsw = findViewById(R.id.user_before_psw);
        newPsw = findViewById(R.id.user_new_psw);
        checkPsw = findViewById(R.id.user_check_psw);
        uAccount = findViewById(R.id.user_account);
        changeBtn = findViewById(R.id.change_btn);
        backBtn = findViewById(R.id.backBtn);
    }
}