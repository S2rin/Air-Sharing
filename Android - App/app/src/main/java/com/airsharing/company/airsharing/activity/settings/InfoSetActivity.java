package com.airsharing.company.airsharing.activity.settings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.MemberData;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController;
import com.airsharing.company.airsharing.retrofit.RequestController;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoSetActivity extends AppCompatActivity {

    //Activity 변수
    private ApiService apiService;
    private MemberData member;
    private EditText name,email,phone;
    private Button change_btn;
    private String memberID, name_str, email_str,phone_str;

    private final static String TAG = "TEST";

    private RequestController reqController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoset);

        //infoset_activity 매칭
        name = (EditText) findViewById(R.id.name_str);
        email = (EditText) findViewById(R.id.email_str);
        phone = (EditText) findViewById(R.id.phone_str);
        change_btn = (Button) findViewById(R.id.change_btn);

        reqController = RequestController.getInstance();
        reqController.buildRequestController(getApplicationContext());

        //TODO 1 : 서버로부터 회원 정보를 요청하여 EditText 각 항목에 회원 정보 Set [동기]


        //TODO 2 : 변경하기 클릭 버튼 클릭 시 서버에 회원 데이터 전송 후 변경된 데이터로 Set
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}