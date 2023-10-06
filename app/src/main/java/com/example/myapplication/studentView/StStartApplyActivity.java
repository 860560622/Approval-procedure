package com.example.myapplication.studentView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.Classes;
import com.example.myapplication.view.LoginActivity;
import com.example.myapplication.view.QueryActivity;

import java.util.ArrayList;
import java.util.List;

public class StStartApplyActivity extends AppCompatActivity {
    private DataProcessor dataProcessor = new DataProcessor();
    private DatabaseCreator databaseCreator = new DatabaseCreator();
    private List<Classes> classesList;
    private int userId;
    private int fromAlbum = 2;
    private ImageButton imageButton;
    private TextView tcName;
    private TextView mgName;
    private EditText applyReason;
    private Button done;
    private ImageButton backBtn;
    private Spinner spinner;
    private Classes chooseClass;
    private Uri luri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_start_apply);
        databaseCreator.createDb(StStartApplyActivity.this);
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        initView();
        initEvent();
    }

    private void initEvent() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        classesList = dataProcessor.getAllClasses(databaseCreator,userId);
        List<String> className = new ArrayList<>();
        for (int i = 0; i < classesList.size(); i++) {
            className.add(classesList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(StStartApplyActivity.this, android.R.layout.simple_spinner_item,className);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Classes classes = classesList.get(position);
                tcName.setText(dataProcessor.findUserById(classes.getTeacherID(),databaseCreator).getName());
                mgName.setText(dataProcessor.findUserById(classes.getManagerID(),databaseCreator).getName());
                chooseClass = classes;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                String reason = applyReason.getText().toString();
                values.put("userID",userId);
                values.put("classID",chooseClass.getId());
                values.put("reason",reason);
                values.put("state",1);
                values.put("backReason","null");
                values.put("confirm",0);
                values.put("image", String.valueOf(luri));
               if(dataProcessor.addApply(values,databaseCreator)){
                   Toast.makeText(StStartApplyActivity.this, "申请成功！", Toast.LENGTH_SHORT).show();
                   finish();
               }
               else {
                   AlertDialog.Builder dialog = new AlertDialog.Builder(StStartApplyActivity.this);
                   dialog.setTitle("申请失败！");
                   dialog.setMessage("系统错误！请重新申请！");
                   dialog.setCancelable(false);
                   dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                       }
                   });
                   dialog.show();
               }
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,fromAlbum);
            }
        });
    }

    private void initView() {
        tcName = findViewById(R.id.teacher_name);
        mgName = findViewById(R.id.manager_name);
        applyReason = findViewById(R.id.reason);
        done = findViewById(R.id.submit);
        backBtn = findViewById(R.id.backBtn);
        spinner = findViewById(R.id.spinner);
        imageButton = findViewById(R.id.add_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            imageButton.setImageURI(uri);
            luri = uri;


        }
    }
}