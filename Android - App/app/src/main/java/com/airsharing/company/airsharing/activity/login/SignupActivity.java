package com.airsharing.company.airsharing.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.IPAddress;
import com.airsharing.company.airsharing.model.MemberData;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends Activity{

    Button signup;
    EditText id, name, pw, email, phone;
    RadioGroup radioGroup;
    ApiService apiService;
    String result;   // radio button 결과
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // ip, port 연결, apiService 연결
        IPAddress ipadress = new IPAddress();
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService(ipadress.getIp(),ipadress.getPort());
        apiService = ApplicationController.getInstance().getApiService();

        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        pw = (EditText) findViewById(R.id.pw);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        signup = (Button) findViewById(R.id.signup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio1){
                    result = "M";
                }
                else{
                    result = "F";
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if(id.getText() == null || pw.getText() == null || name.getText() == null || email.getText() == null || phone.getText() == null || result == null)
                    {
                        Toast.makeText(getApplicationContext(), "정보를 다 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        System.out.println("회원 가입 요청!!!!!!");
                        userid = id.getText().toString();
                        Call<MemberData> signup_data = apiService.signup(id.getText().toString(), pw.getText().toString(), name.getText().toString(), email.getText().toString(),
                                phone.getText().toString(), result);   // 회원가입 요청

                        System.out.println("회원 가입 response 받기!!!!!");
                        signup_data.enqueue(new Callback<MemberData>() {
                            @Override
                            public void onResponse(Call<MemberData> call, Response<MemberData> response) {
                                System.out.println("회원 가입 response 받는 중!!");
                                if (response.isSuccessful()) {
                                    System.out.println("sign up success!");
                                    Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                    Intent goHome = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(goHome);   // 디바이스 설정 화면으로 가기
                                } else {
                                    int code = response.code();
                                    Log.i("Log", "회원가입 실패 코드: " + code);
                                    System.out.println("sign up Fail!");
                                }
                            }

                            @Override
                            public void onFailure(Call<MemberData> call, Throwable t) {
                                Log.i("Log", "서버 에러: " + t.getMessage());
                            }
                        });
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    System.out.println("에러: " + e);
                }
            }
        });
    }
}
