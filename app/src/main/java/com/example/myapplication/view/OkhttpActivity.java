package com.example.myapplication.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        Button button = findViewById(R.id.bt1);
        getAsync(this.getCurrentFocus());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkhttp();
            }
        });
    }
    private void sendRequestWithOkhttp(){
        new Thread(){
            {try{
                OkHttpClient client = new OkHttpClient();
                URL url = new URL("https://www.baidu.com");
                Request request = new Request.Builder().url(url).build();
                Log.d("OkhttpActivity","oo");
                Response response = client.newCall(request).execute();
                String responseData = null;
                if(response.body() != null){
                    responseData = response.body().string();
                }
                if(responseData != null){
                       showResponse(responseData);
                }
            }catch (Exception e){e.printStackTrace();}
            finally {

            }
            }
        }.start();
    }
    private void showResponse(String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("OkhttpActivity",response);
            }
        });

    }
 /*  private void sendRequestWithOkhttp(){
        new Thread(){{
            try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://10.0.2.2/get_data.xml").build();
                Response response;
                response = okHttpClient.newCall(request).execute();
            String r = response.body().string();
                if (r != null){
                    parseXMLWithPull(r);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        }.start();
    }*/
   private void parseXMLWithPull(String r) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParse = factory.newPullParser();
            xmlPullParse.setInput(new StringReader(r));
            int evenType = xmlPullParse.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (evenType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParse.getName();
                if(evenType == XmlPullParser.START_TAG){
                    if(nodeName == "id"){
                        id = xmlPullParse.nextText();
                    }
                    if(nodeName == "name"){
                        name = xmlPullParse.nextText();
                    }
                    if(nodeName == "version"){
                        version = xmlPullParse.nextText();
                    }
                }
                if(evenType == xmlPullParse.END_TAG){ //输出
                    if(nodeName == "app"){
                        Log.d("OkhttpActivity","qwq");
                    }
                }
                evenType = xmlPullParse.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getSync(View view)  {
        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder().url("https://10.0.2.2/get_data.xml").build();
                OkHttpClient  okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i("OkhttpActivity","getSync"+response.body().string());
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }.start();

    }
    public void getAsync(View view){
        Request request = new Request.Builder().url("https://10.0.2.2/get_data.xml").build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.i("OkhttpActivity","getAsync:"+response.body().string());
                }
            }
        });
    }

   /* public void postSync(View view){
        new Thread(){
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder().add("a","1").add("b","2").build();
                Request request = new Request.Builder().url("http://www.httpbin.org/post").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i("OkhttpActivity","postSync"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void postAsync(View view){
        FormBody formBody = new FormBody.Builder().add("a","1").add("b","2").build();
        Request request = new Request.Builder().url("http://www.httpbin.org/post").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.i("OkhttpActivity","postAsync:"+response.body().string());
                }
            }
        });
    }*/
}