package com.example.myapplication.teacherView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Apply;
import com.example.myapplication.model.ApplyAdapter;
import com.example.myapplication.model.ReadAdapter;
import com.example.myapplication.view.QueryActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TcReadActivity extends AppCompatActivity {
    private int userId;
    private int index;
    private List<Apply> applies = new ArrayList<>();
    private List<String> appName = new ArrayList<>();
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private TextView className;
    private ImageButton back;
    private View detail;
    private TextView stName;
    private TextView appReason;
    private EditText reason;
    private ListView listView;
    private View br;
    private RadioButton no;
    private RadioButton yes;
    private Button done;
    private ReadAdapter RA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc_read);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        databaseCreator.createDb(TcReadActivity.this);////
        initView();
        initEvent();

    }

    private void initEvent() {
        //detail.setVisibility(View.INVISIBLE);
        br.setVisibility(View.INVISIBLE);
        applies = dataProcessor.getReadList(databaseCreator,userId);
        int s = applies.size();
        for (int i = 0; i < applies.size(); i++) {
            String name =dataProcessor.findClassById(applies.get(i).getClassID(),databaseCreator).getName();
           appName.add(name);
        }

        ReadAdapter readAdapter = new ReadAdapter(TcReadActivity.this,R.layout.read_list,applies,databaseCreator);
        listView.setAdapter(readAdapter);
        RA = readAdapter;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //detail.setVisibility(View.VISIBLE);
                index = position;
                Apply apply = applies.get(position);
                className.setText(appName.get(position));
                stName.setText(dataProcessor.findUserById(apply.getUserID(),databaseCreator).getName());
                appReason.setText(apply.getReason());

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br.setVisibility(View.VISIBLE);
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br.setVisibility(View.INVISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TcReadActivity.this,"yes:"+yes.isChecked()+"\nno:"+no.isChecked(),Toast.LENGTH_SHORT).show();
                if (!yes.isChecked() && !no.isChecked()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TcReadActivity.this);
                    dialog.setMessage("您还未选择审批结果！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
                else {
                String a = reason.getText().toString();
                if (a.length() < 1 && no.isChecked()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TcReadActivity.this);
                    dialog.setMessage("您还未输入驳回理由！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                } else {
                    if (yes.isChecked()) {
                        changeList(1, applies.get(index));
                    } else {
                        changeList(0, applies.get(index));
                    }
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TcReadActivity.this);
                    dialog.setMessage("审批成功！");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //detail.setVisibility(View.INVISIBLE);
                        }
                    });
                    dialog.show();
                }
            }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void  changeList(int result,Apply apply){
        appName.remove(index);
        applies.remove(index);
        ContentValues values = new ContentValues();
        if(result == 1){
           int state = apply.getState()+1;
            values.put("state",state);
            values.put("confirm",0);
           dataProcessor.updateAppleData(databaseCreator,values,apply.getId());
           values.clear();
        }else {
            values.put("state",5);
            values.put("confirm",0);
            dataProcessor.updateAppleData(databaseCreator,values,apply.getId());
            values.clear();
        }
        className.setText("");
        stName.setText("");
        appReason.setText("");
        RA.notifyDataSetChanged();
    }
    private void initView() {
        reason = findViewById(R.id.back_reason);
        br = findViewById(R.id.bb);
        no = findViewById(R.id.no);
        yes = findViewById(R.id.yes);
        listView = findViewById(R.id.read_list);
        className = findViewById(R.id.class_name);
        stName = findViewById(R.id.apply_st_name);
        appReason = findViewById(R.id.apply_reason);
        done = findViewById(R.id.done);
        detail = findViewById(R.id.detail);
        back = findViewById(R.id.backBtn);
    }
}