package com.airsharing.company.airsharing.activity.community;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.UploadContent;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-11-06.
 */

public class UploadActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private HashMap<String, List<String>> regions;
    private List<String> region1, region2;
    private String uuid, content_region1, content_region2;
    private EditText content;
    private Spinner spinner, spinner2;
    private SpinnerAdapter spinnerAdapter1, spinnerAdapter2;
    private String region, detailedRegion;
    Button upload_button;

    private ApiService apiService;
    private String ip = "13.124.32.165";
    private int port = 8080;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadform);

        // ip, port 연결, apiService 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService(ip, port);
        apiService = ApplicationController.getInstance().getApiService();

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        uuid = sharedPreferences.getString("userid", null);
        Log.i("UUID", "value: " + uuid);
        regions = SetExpandedList.getData_spinner();

        content = (EditText) findViewById(R.id.content);
        upload_button = (Button) findViewById(R.id.upload_button);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        region1 = new ArrayList<String>(regions.keySet());

        spinnerAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, region1);
        spinner.setAdapter(spinnerAdapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = adapterView.getItemAtPosition(i).toString();

                if (!str.equals("시/도")) {
                    Log.i("choose region", "value: " + str);
                    region2 = new ArrayList<>(regions.get(str));
                    spinnerAdapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, region2);
                    spinner2.setAdapter(spinnerAdapter2);
                    region = str;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = adapterView.getItemAtPosition(i).toString();

                if (!str.equals("선택하세요")) {
                    Log.i("Final Select Region", str);
                    detailedRegion = str;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadContent uploadContent= new UploadContent();
                uploadContent.setUuid(uuid);
                uploadContent.setContent(content.getText().toString());
                uploadContent.setRegion(region);
                uploadContent.setDetailedRegion(detailedRegion);
                Call<Void> uploadContentCall = apiService.upload(uuid, uploadContent.getContent(), uploadContent.getRegion(), uploadContent.getDetailedRegion());

                try {
                    uploadContentCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "업로드 성공", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("onFailure", "Message: " + t.getMessage());
                            Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    Log.e("Call Error", e.toString());
                    Toast.makeText(getApplicationContext(), "서버 에러", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
