package com.airsharing.company.airsharing.activity.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.activity.DashboardActivity;
import com.airsharing.company.airsharing.activity.HomeActivity;
import com.airsharing.company.airsharing.activity.MapsActivity;
import com.airsharing.company.airsharing.activity.settings.SettingsActivity;

public class CommunityActivity extends Activity{

    private Button home, dashboard, map, community, settings, select_region_button, write_content_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        //Botton Nav Bar UI Component
        home = (Button) findViewById(R.id.home);
        dashboard = (Button) findViewById(R.id.dashboard);
        map = (Button) findViewById(R.id.map);
        community = (Button) findViewById(R.id.community);
        settings = (Button) findViewById(R.id.settings);
        select_region_button = (Button) findViewById(R.id.select_region_button);
        write_content_button = (Button) findViewById(R.id.write_content_button);

        /* 내비게이션 버튼 클릭 리스너 선언*/
        community.setBackgroundResource(R.drawable.button_blue);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(CommunityActivity.this, HomeActivity.class);
                startActivityForResult(goHome, 0);
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDashBoard = new Intent(CommunityActivity.this, DashboardActivity.class);
                startActivityForResult(goDashBoard, 0);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMap = new Intent(CommunityActivity.this, MapsActivity.class);
                startActivity(goMap);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(CommunityActivity.this, SettingsActivity.class);
                startActivityForResult(goSettings, 0);
            }
        });

        select_region_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectRegionActivity.class);
                startActivity(intent);
            }
        });

        write_content_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });
    }
}
