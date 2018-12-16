package com.airsharing.company.airsharing.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.airsharing.company.airsharing.activity.login.LoginActivity;
import com.airsharing.company.airsharing.model.IPAddress;
import com.airsharing.company.airsharing.model.MemberData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by surin on 2017. 10. 16..
 */

public class RequestController {

    public IPAddress address;
    public ApiService apiService;
    public SharedPreferences sharedPreferences;
    public String userid;
    public static RequestController instance;
    public Context c;
    public Intent intent;

    //variable
    MemberData member;

    public static RequestController getInstance() {
        if(instance == null){
            instance = new RequestController();
            return instance;
        }
        else{
            return instance;
        }
    }

    public void buildRequestController(Context c){
        this.c = c;
        address = new IPAddress();
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService(address.getIp(), address.getPort());
        apiService = ApplicationController.getInstance().getApiService();
    }

    public MemberData readInfo() {

        /*
        Call<List<MemberData>> thumbnailCall = apiService.getInfo(userid);
        thumbnailCall.enqueue(new Callback<List<MemberData>>() {
            @Override
            public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {
                if(response.isSuccessful()){
                    member.setName(response.body().get(0).getName());
                    member.setPhone(response.body().get(0).getPhone());
                    member.setEmail(response.body().get(0).getEmail());
                    Log.d("MyTAG", "readInfo:1 " + member.getEmail());
                }else{
                    int statusCode = response.code();
                    Log.i("MyTag","응답코드 : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<MemberData>> call, Throwable t) {
                Log.i("MyTag","서버 onFailure :  "+t.getMessage());
            }
        });

        Log.d("MyTAG", "readInfo:2 " + member.getEmail());*/

        return member;
    }

    public void updatePasswd(String pw){
        sharedPreferences = c.getSharedPreferences("sharedPreferences", MODE_PRIVATE);   // 쿠키 설정
        userid = sharedPreferences.getString("userid", null);
        Call<MemberData> thumbnailCall = apiService.updatePw(userid, pw);
        thumbnailCall.enqueue(new Callback<MemberData>() {
            @Override
            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                if(response.isSuccessful()){

                }else{
                    int statusCode = response.code();
                    Log.i("MyTag","응답코드 : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<MemberData> call, Throwable t) {
                Log.i("MyTag","서버 onFailure :  "+t.getMessage());
            }
        });
    }

    public void updateAlrm(Boolean alarm){
        sharedPreferences = c.getSharedPreferences("sharedPreferences", MODE_PRIVATE);   // 쿠키 설정
        userid = sharedPreferences.getString("userid", null);
        Call<MemberData> thumbnailCall = apiService.updateAlarm(alarm,userid);
        thumbnailCall.enqueue(new Callback<MemberData>() {
            @Override
            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                if(response.isSuccessful()){
                    Log.d("MyTag", "onResponse: 성공!");
                }else {
                    int statusCode = response.code();
                    Log.i("MyTag","응답코드 : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<MemberData> call, Throwable t) {
                Log.i("MyTag","서버 onFailure :  "+t.getMessage());
            }
        });
    }

    public void deleteMember(){
        sharedPreferences = c.getSharedPreferences("sharedPreferences", MODE_PRIVATE);   // 쿠키 설정
        userid = sharedPreferences.getString("userid", null);
        Call<MemberData> thumbnailCall = apiService.withdrawal(userid);
        thumbnailCall.enqueue(new Callback<MemberData>() {
            @Override
            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                if(response.isSuccessful()){
                    Log.d("MyTag", "onResponse: 탈퇴 성공!");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    intent = new Intent(c.getApplicationContext(),LoginActivity.class);
                    c.startActivity(intent);
                    ((Activity) c).finish();
                }else {
                    int statusCode = response.code();
                    Log.i("MyTag","응답코드 : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<MemberData> call, Throwable t) {

            }
        });
    }

    public void logoutMember(){
        sharedPreferences = c.getSharedPreferences("sharedPreferences", MODE_PRIVATE);   // 쿠키 설정
        userid = sharedPreferences.getString("userid", null);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        intent = new Intent(c.getApplicationContext(),LoginActivity.class);
        c.startActivity(intent);
        ((Activity) c).finish();
    }
}
