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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controller.DataProcessor;
import com.example.myapplication.controller.DatabaseCreator;
import com.example.myapplication.model.User;
import com.example.myapplication.studentView.StMainActivity;
import com.example.myapplication.model.Apply;
import com.example.myapplication.model.ApplyAdapter;
import com.example.myapplication.teacherView.TcMainActivity;

import java.util.ArrayList;
import java.util.List;
//查询界面
public class QueryActivity extends AppCompatActivity {
   private List<Apply> applyList;
   private List<Apply> pageList = new ArrayList<>();
   private List<String> queryList = new ArrayList<>();
   private Spinner spinner;
   private ImageButton before;
   private ImageButton next;
   private TextView page;
   private ImageButton mainBtn;
   private ImageButton infoBtn;
   private ImageButton userBtn;
   private SearchView searchView;
   private DataProcessor dataProcessor = new DataProcessor();
   private DatabaseCreator databaseCreator = new DatabaseCreator();
   private ListView listView;
   private int userId;
   private String condition;
   private int currentPage = 1;
   private int queryWay = 0;
   private int isCheck = 0;
   private ArrayAdapter IA;
   private int getPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        databaseCreator.createDb(QueryActivity.this);////
        Intent intent = getIntent();
        userId = intent.getIntExtra("user_id",-1);
        queryWay = intent.getIntExtra("queryWay",-1);
        initApplies();
        initQueries();
        initView();
        initEvent();
        paginate(applyList);
        ApplyAdapter itemAdapter = new ApplyAdapter(QueryActivity.this,R.layout.apply_list,pageList,databaseCreator);
        IA = itemAdapter;
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getPosition = position;
                Apply apply = pageList.get(position);
                toApplyInfoActivity(apply);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(QueryActivity.this, android.R.layout.simple_spinner_item,queryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initQueries() {
        queryList.add("按课程名称");
        queryList.add("按教师名称");
        queryList.add("按主管教师名称");
        queryList.add("按审批状态");
    }

    private void toApplyInfoActivity(Apply apply) {
        Intent intent = new Intent(QueryActivity.this, ApplyInfoActivity.class);
        intent.putExtra("queryWay",queryWay);
        intent.putExtra("apply_id",apply.getId());
        startActivityForResult(intent,1);
    }

    private void toMainView(User user){
        Intent intent;
        if(user.getStatus() == 1){
            intent = new Intent(QueryActivity.this, StMainActivity.class);
        }else {
            intent = new Intent(QueryActivity.this, TcMainActivity.class);
        }
        intent.putExtra("user_id",user.getId());
        startActivity(intent);
        finish();
    }
    private void toInfoView(){
        Intent intent = new Intent(QueryActivity.this,InfoActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void toUserView(){
        Intent intent = new Intent(QueryActivity.this,UserActivity.class);
        intent.putExtra("user_id",userId);
        startActivity(intent);
        finish();
    }
    private void initEvent() {
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
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInfoView();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   condition = queryList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       searchView.setQueryHint("请输入查找信息");
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               List<Apply> queryApply=dataProcessor.queryApply(query,condition,databaseCreator,applyList,userId);
               if(queryApply.size() == 0){
                   AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);
                   dialog.setTitle("查询失败！");
                   dialog.setMessage("查询结果为空！请检测输入条件是否正确！");
                   dialog.setCancelable(false);
                   dialog.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                       }
                   });
                   dialog.show();
               }else {
                   paginate(queryApply);
                   ApplyAdapter itemAdapter = new ApplyAdapter(QueryActivity.this,R.layout.apply_list,pageList,databaseCreator);
                   listView.setAdapter(itemAdapter);
               }
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               ApplyAdapter itemAdapter = new ApplyAdapter(QueryActivity.this,R.layout.apply_list,applyList,databaseCreator);
               listView.setAdapter(itemAdapter);
               return false;
           }
       });
       before.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               --currentPage;
               paginate(applyList);
               if(currentPage == 1){
                   before.setVisibility(View.INVISIBLE);
               }
               ApplyAdapter itemAdapter = new ApplyAdapter(QueryActivity.this,R.layout.apply_list,pageList,databaseCreator);
               listView.setAdapter(itemAdapter);
           }
       });
       next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ++currentPage;
               paginate(applyList);
               ApplyAdapter itemAdapter = new ApplyAdapter(QueryActivity.this,R.layout.apply_list,pageList,databaseCreator);
               listView.setAdapter(itemAdapter);
           }
       });
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        mainBtn = findViewById(R.id.mainView);
        infoBtn = findViewById(R.id.infoView);
        userBtn = findViewById(R.id.userView);
        spinner = findViewById(R.id.spinner);
        searchView = findViewById(R.id.search);
        before = findViewById(R.id.before_btn);
        next = findViewById(R.id.next_btn);
        page = findViewById(R.id.page);
    }

    private void initApplies() {
        User user = dataProcessor.findUserById(userId,databaseCreator);
        int status = user.getStatus();
        switch (status){
            case 1 :applyList = dataProcessor.findAllApplyByUserId(userId,databaseCreator);break;
            case 2: {
                applyList = dataProcessor.findApplyListByIdOfTc(userId,databaseCreator,status);break;
            }
            case 3: {
                applyList = dataProcessor.findApplyListByIdOfTc(userId,databaseCreator,status);break;
            }
            default:break;
        }
        if(queryWay == 1){
            for (int i = applyList.size()-1; i > -1 ; i--) {
                if((applyList.get(i).getState() == 4 || applyList.get(i).getState() == 5) &&applyList.get(i).getConfirm() == 1){
                    applyList.remove(i);
                }
            }
        }
    }
    public void paginate(List<Apply> targetList){
        before.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        int contact = 6;
        int total = targetList.size()/contact;  //8
        if(targetList.size() <= contact){
            pageList = targetList;
            before.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            page.setText("1/1");
        }else {
           pageList.clear();
            if(contact*currentPage <targetList.size()){
                for (int i = contact*currentPage-contact; i < contact*currentPage; i++) {
                    pageList.add(targetList.get(i));
                }
                before.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                total +=1;
                page.setText(currentPage+"/"+total);
            }
            else if(8*currentPage > targetList.size()){
                for (int i = contact*currentPage-contact; i < targetList.size(); i++) {
                    pageList.add(targetList.get(i));
                }
                next.setVisibility(View.INVISIBLE);
                total +=1;
                page.setText(total+"/"+total);
            }
            else {
                for (int i = contact*currentPage-contact; i < targetList.size(); i++) {
                    pageList.add(targetList.get(i));
                }
                next.setVisibility(View.INVISIBLE);
                page.setText(total+"/"+total);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            isCheck = data.getIntExtra("data_return",-1);
            changeList();
            //Toast.makeText(QueryActivity.this,"return",Toast.LENGTH_SHORT).show();
        }
    }
    public void changeList(){
        if(isCheck == 1){
            Toast.makeText(QueryActivity.this,"delete",Toast.LENGTH_SHORT).show();
            pageList.remove(getPosition);
            IA.notifyDataSetChanged();
        }
    }
}