package com.airsharing.company.airsharing.activity.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.Contents;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017-11-06.
 */

public class SelectRegionActivity extends AppCompatActivity{

    ExpandableListView expandableListView;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandagleListDetail;
    ImageView indicator;

    private ApiService apiService;
    private String ip = "13.124.32.165";
    private int port = 8080;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_region);

        // ip, port 연결, apiService 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildApiService(ip, port);
        apiService = ApplicationController.getInstance().getApiService();

        indicator = (ImageView) findViewById(R.id.indicator);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandagleListDetail = SetExpandedList.getData();
        expandableListTitle = new ArrayList<String>(expandagleListDetail.keySet());

        expandableListView.setAdapter(new CustomExpandableListViewAdapter(getApplicationContext(), expandableListTitle, expandagleListDetail));

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                String r = expandableListTitle.get(groupPosition);
                String dr = expandagleListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);

                try {
                    Call<List<Contents>> getContentsCall = apiService.getContents(r, dr);
                    getContentsCall.enqueue(new Callback<List<Contents>>() {
                        @Override
                        public void onResponse(Call<List<Contents>> call, Response<List<Contents>> response) {
                            ArrayList<Contents> arraylist = new ArrayList<Contents>();
                            if (response.isSuccessful()) {
                                if (response.body().size() == 0) {
                                    Intent intent = new Intent(getApplicationContext(), EmptyBoardActivity.class);
                                    startActivity(intent);
                                } else {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        Contents contents = new Contents();
                                        contents.setUuid(response.body().get(i).getUuid());
                                        contents.setDust(response.body().get(i).getDust());
                                        contents.setContent(response.body().get(i).getContent());
                                        arraylist.add(contents);
                                    }

                                    Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList("data",arraylist);
                                    intent.putExtra("bundle", bundle);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Contents>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                            Log.e("onFailure", "Message: " + t.getMessage());
                        }
                    });

                } catch (Exception e) {
                    Log.e("Call Error", e.toString());
                    Toast.makeText(getApplicationContext(), "서버 에러", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}
