package com.airsharing.company.airsharing.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.activity.community.CommunityActivity;
import com.airsharing.company.airsharing.activity.settings.SettingsActivity;
import com.airsharing.company.airsharing.model.AirData;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Activity {

    private Button home, dashboard, map, community, settings, btn_airRequest;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;
    private Button btn_airResult, co, co2, o2, dust, finedust;
    private TextView realocation, temp, humi;
    private String userid;
    private static final int PERMISSIONS_REQUEST_LOCATION = 2;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    private double latitude, longitude;

    private ApplicationController2 application;
    private ApiService apiService;
    private static String ip = "192.9.45.231";
    private static int port = 3000;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //ip, port 연결
        application = ApplicationController2.getInstance();
        application.buildApiService(ip,port);
        apiService = ApplicationController2.getInstance().getApiService();

        //쿠키 설정
        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);   // 쿠키 설정
        userid = sharedPreferences.getString("userid", null);

        Log.i("TAG", "onCreate: userid : " + userid);
        //위치 UI
        realocation = (TextView) findViewById(R.id.realocation);

        //Air Sensor + temp, humi Sensor Result
        btn_airResult = (Button) findViewById(R.id.home_result);
        temp = (TextView) findViewById(R.id.temp);
        humi = (TextView) findViewById(R.id.humi);
        co = (Button) findViewById(R.id.btn_co);
        co2 = (Button) findViewById(R.id.btn_co2);
        o2 = (Button) findViewById(R.id.btn_o2);
        dust = (Button) findViewById(R.id.btn_dust);
        finedust = (Button) findViewById(R.id.btn_microdust);

        //공기 측정 UI Component
        btn_airRequest = (Button) findViewById(R.id.btn_airRequest);

        //Botton Nav Bar UI Component
        home = (Button) findViewById(R.id.home);
        dashboard = (Button) findViewById(R.id.dashboard);
        map = (Button) findViewById(R.id.map);
        community = (Button) findViewById(R.id.community);
        settings = (Button) findViewById(R.id.settings);

        /*GPS 설정*/
        settingsGPS();

        currentLocation = getMyLocation();

        /* 스마트 Airzone 측정 요청 리스너 */
        btn_airRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //프로그래스바 요청
                callProgressBarInside();
            }
        });

        /* 내비게이션 버튼 클릭 리스너 선언*/
        home.setBackgroundResource(R.drawable.button_blue);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDashBoard = new Intent(HomeActivity.this, DashboardActivity.class);
                startActivityForResult(goDashBoard, 0);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMap = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(goMap);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMap = new Intent(HomeActivity.this, CommunityActivity.class);
                startActivity(goMap);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivityForResult(goSettings, 0);
            }
        });


    }

    private void callProgressBarInside() {
        mHandler = new Handler();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog = ProgressDialog.show(HomeActivity.this, "AirSharing", "스마트 AirZone 측정 중 입니다");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();

                                //TODO : IoT 공기 측정 요청
                                Call<List<AirData>> thumbnailCall = apiService.requestOutsidedb(userid,Double.toString(longitude),Double.toString(latitude));
                                thumbnailCall.enqueue(new Callback<List<AirData>>() {
                                    @Override
                                    public void onResponse(Call<List<AirData>> call, Response<List<AirData>> response) {
                                        if(response.isSuccessful()){

                                            AirData airData = new AirData();
                                            airData.setCo(response.body().get(0).getCo());
                                            airData.setCo_result(response.body().get(0).getCo_result());
                                            airData.setCo2(response.body().get(0).getCo2());
                                            airData.setCo2_result(response.body().get(0).getCo2_result());
                                            airData.setO2(response.body().get(0).getO2());
                                            airData.setO2_result(response.body().get(0).getO2_result());
                                            airData.setDust(response.body().get(0).getDust());
                                            airData.setDust_result(response.body().get(0).getDust_result());
                                            airData.setFinedust(response.body().get(0).getFinedust());
                                            airData.setFinedust_result(response.body().get(0).getFinedust_result());
                                            airData.setLongitude(response.body().get(0).getLongitude());
                                            airData.setLatitude(response.body().get(0).getLatitude());
                                            airData.setTemp(response.body().get(0).getTemp());
                                            airData.setHumidity(response.body().get(0).getHumidity());

                                            //View Set
                                            //공기 판별 결과 set
                                            if(airData.getFinedust_result().equals("bad")){
                                                btn_airResult.setText("나쁨");
                                                btn_airResult.setBackgroundResource(R.drawable.bad);
                                            }else if(airData.getFinedust_result().equals("soso")){
                                                btn_airResult.setText("보통");
                                                btn_airResult.setBackgroundResource(R.drawable.normal);
                                            }else if(airData.getFinedust_result().equals("good")) {
                                                btn_airResult.setText("좋음");
                                                btn_airResult.setBackgroundResource(R.drawable.good);
                                            }

                                            //온습도 set
                                            temp.setText("온도: "+airData.getTemp()+" ℃");
                                            humi.setText("습도: "+airData.getHumidity()+" %");//온습도 set

                                            //공기 데이터 set
                                            co.setText(airData.getCo()+" ppm");
                                            co2.setText(airData.getCo2()+" ppm");
                                            o2.setText(airData.getO2()+" %");
                                            dust.setText(airData.getDust()+" µg/m3");//공기 데이터 set
                                            finedust.setText(airData.getFinedust()+" µg/m3");
                                            co.setText(airData.getCo()+" ppm");
                                            co2.setText(airData.getCo2()+" ppm");
                                            o2.setText(airData.getO2()+" %");
                                            dust.setText(airData.getDust()+" µg/m3");

                                            ToastMessage();

                                        }else {
                                            int statusCode = response.code();
                                            Log.i("MyTag","응답코드 : "+statusCode);
                                            Toast.makeText(getBaseContext(),"스마트 AirZone 측정 요청 실패.",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<AirData>> call, Throwable t) {
                                        Log.i("MyTag","서버 onFailure :  "+t.getMessage());
                                    }
                                });

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 10000);
            }
        });
    }


    void ToastMessage(){
        //10초 후 ToastMessage
        Toast.makeText(getBaseContext(),"측정이 완료되었습니다",Toast.LENGTH_SHORT).show();
    }

    //사용자의 위치를 수신하는 GetLocation
    private Location getMyLocation(){
        currentLocation = null;

        //Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 사용자 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
        }else{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                double lng = currentLocation.getLongitude();
                double lat = currentLocation.getLatitude();
                Log.d("Main", "longitude=" + lng + ", latitude=" + lat);
            }
        }

        return  currentLocation;
    }

    private void settingsGPS(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                // TODO 위도, 경도로 하고 싶은 것
                Log.d("GPS 보고싶어요 : ",latitude+", "+longitude);

                Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.KOREA);
                List<Address> address;
                try{
                    if(geocoder != null){
                        //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                        //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                        address = geocoder.getFromLocation(latitude,longitude,1);

                        if(address != null && address.size()>0){
                            //주소 받아오기
                            String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                            realocation.setText(currentLocationAddress);
                        }
                    }

                }catch (IOException e){
                    Toast.makeText(getBaseContext(),"주소를 가져올 수 없습니다",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    //사용자 권한 요청 처리
    boolean canReadLocation = false;
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // success!
                Location userLocation = getMyLocation();
                if (userLocation != null) {
                    // todo : 사용자의 현재 위치 구하기
                    latitude = userLocation.getLatitude();
                    longitude = userLocation.getLongitude();
                    Log.d("TEST", "onRequestPermissionsResult: " + latitude + longitude);
                }
                canReadLocation = true;
            } else {
                // Permission was denied or request was cancelled
                canReadLocation = false;
            }
        }
    }
}
