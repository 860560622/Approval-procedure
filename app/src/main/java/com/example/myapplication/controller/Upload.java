package com.example.myapplication.controller;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Upload {
    public void uploadImage()throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        File file1 = new File("C:\\Users\\ASUS\\Desktop\\12.jpg");
        File file2 = new File("C:\\Users\\ASUS\\Desktop\\11.jpg");
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("file1",file1.getName(), RequestBody.create(file1, MediaType.parse("image/jpeg")))
                .addFormDataPart("file2",file2.getName(),RequestBody.create(file2, MediaType.parse("image/jpeg"))).build();
        Request request = new Request.Builder().url("i-bp14sfcqffcv2snavuwp").post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }
}
