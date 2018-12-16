package com.airsharing.company.airsharing.activity.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.IPAddress;
import com.airsharing.company.airsharing.model.MemberData;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindIdActivity extends Activity{
    Button ok;
    EditText name, email;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);

        // ip, port 연결, apiService 연결
        IPAddress ipadress = new IPAddress();
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService(ipadress.getIp(),ipadress.getPort());
        apiService = ApplicationController.getInstance().getApiService();

        ok = (Button) findViewById(R.id.ok);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String data1 = name.getText().toString();
                    String data2 = email.getText().toString();
                    Call<List<MemberData>> findId_data = apiService.findid(data1, data2);

                    findId_data.enqueue(new Callback<List<MemberData>>() {
                        @Override
                        public void onResponse(Call<List<MemberData>> call, Response<List<MemberData>> response) {
                            if (response.isSuccessful()) {
                                List<MemberData> datas = response.body();
                                if(datas != null){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(FindIdActivity.this);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            Intent goLogin = new Intent(FindIdActivity.this, LoginActivity.class);
                                            startActivity(goLogin);
                                        }
                                    });
                                    for(MemberData data : datas){
                                        System.out.println("사용자 아이디: " + data.getUuid());
                                        alert.setMessage("당신의 아이디는 " + data.getUuid() + "입니다.");
                                        alert.show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "일치하는 계정이 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "오류 발생.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MemberData>> call, Throwable t) {
                            Log.i("Log", "서버 에러: " + t.getMessage());
                        }
                    });

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "모든 정보를 다 입력하셨나요?", Toast.LENGTH_SHORT).show();
                    System.out.println("에러: " + e);
                }
            }
        });

    }
}
