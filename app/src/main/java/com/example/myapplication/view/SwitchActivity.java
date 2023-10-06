package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Remember;
import com.example.myapplication.model.User;
import com.example.myapplication.studentView.StMainActivity;
import com.example.myapplication.teacherView.TcMainActivity;

import java.util.ArrayList;
import java.util.List;

public class SwitchActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private List<Remember> remembers = new ArrayList<>();
    private ListView listView;
    private Button change;

    private ImageButton back;
    private boolean isDelete = false;
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        databaseCreator.createDb(SwitchActivity.this);
        initView();
        initList();
        initEvent();

    }

    private void initList() {
        remembers = dataProcessor.getRmAccount(databaseCreator);
        for (int i = 0; i < remembers.size(); i++) {
            list.add(dataProcessor.findUserById(remembers.get(i).getUserId(),databaseCreator).getName());
        }
    }

    private void initEvent() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SwitchActivity.this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isDelete){
                    Remember remember = remembers.get(position);
                    dataProcessor.deleteRemember(databaseCreator,remember);
                    list.remove(position);
                    remembers.remove(position);
                   adapter.notifyDataSetChanged();
                }else {
                    User user = dataProcessor.findUserById(remembers.get(position).getUserId(),databaseCreator);
                    toMainActivity(user);
                }


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return",0);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDelete = !isDelete;
                if(isDelete){
                    change.setText("删除");
                }else {
                    change.setText("编辑");
                }
            }
        });
    }

    private void initView() {
        listView = findViewById(R.id.account);
        change = findViewById(R.id.change);
        back = findViewById(R.id.backBtn);
    }

    private void toMainActivity(User user) {
        Intent intent;
        if(user.getStatus() == 1){
            intent = new Intent(SwitchActivity.this, StMainActivity.class);
            intent.putExtra("user_id",user.getId());
        }
        else {
            intent = new Intent(SwitchActivity.this, TcMainActivity.class);
            intent.putExtra("user_id",user.getId());
        }
        Intent intent1 = new Intent();
        intent.putExtra("data_return",1);
        setResult(RESULT_OK,intent1);
        startActivity(intent);
        finish();
    }

}