package com.airsharing.company.airsharing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.activity.community.CommunityActivity;
import com.airsharing.company.airsharing.activity.settings.SettingsActivity;

public class DashboardActivity extends Activity {

    private Button home, dashboard, map, community, settings;
    private Button btnCO2, btnCO, btnO2, btnDust;
    private WebView webView;
    private String userid = "Qsah32";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Botton Nav Bar UI Component
        home = (Button) findViewById(R.id.home);
        dashboard = (Button) findViewById(R.id.dashboard);
        map = (Button) findViewById(R.id.map);
        community = (Button) findViewById(R.id.community);
        settings = (Button) findViewById(R.id.settings);

        btnCO2 = (Button)findViewById(R.id.btnCO2);
        btnCO = (Button)findViewById(R.id.btnCO);
        btnO2 = (Button)findViewById(R.id.btnO2);
        btnDust = (Button)findViewById(R.id.btnDust);
        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        btnCO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://13.124.32.165:8080/mobile/chart/today/co";
                webView.loadUrl(url);
            }
        });
        btnCO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://13.124.32.165:8080/mobile/chart/today/co2";
                webView.loadUrl(url);
            }
        });
        btnO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://13.124.32.165:8080/mobile/chart/today/o2";
                webView.loadUrl(url);
            }
        });
        btnDust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://13.124.32.165:8080/mobile/chart/today/dust";
                webView.loadUrl(url);
            }
        });

        /* 내비게이션 버튼 클릭 리스너 선언*/
        dashboard.setBackgroundResource(R.drawable.button_blue);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivityForResult(goHome, 0);
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDashBoard = new Intent(DashboardActivity.this, DashboardActivity.class);
                startActivityForResult(goDashBoard, 0);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMap = new Intent(DashboardActivity.this, MapsActivity.class);
                startActivity(goMap);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMap = new Intent(DashboardActivity.this, CommunityActivity.class);
                startActivity(goMap);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivityForResult(goSettings, 0);
            }
        });
    }
}
