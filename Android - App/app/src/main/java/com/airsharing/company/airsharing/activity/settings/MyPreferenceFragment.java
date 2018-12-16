package com.airsharing.company.airsharing.activity.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.activity.login.LoginActivity;
import com.airsharing.company.airsharing.activity.settings.PwSetActivity;
import com.airsharing.company.airsharing.activity.settings.SettingsActivity;
import com.airsharing.company.airsharing.model.IPAddress;
import com.airsharing.company.airsharing.model.MemberData;
import com.airsharing.company.airsharing.retrofit.ApiService;
import com.airsharing.company.airsharing.retrofit.ApplicationController;
import com.airsharing.company.airsharing.retrofit.RequestController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyPreferenceFragment extends PreferenceFragment {
    final static String TAG = "AirSharing";

    SharedPreferences sharedPreferences;
    RequestController reqController;
    Intent intent;
    Boolean alarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

        reqController = RequestController.getInstance();
        reqController.buildRequestController(getContext());

        //알림 설정
        Preference pushSet = (Preference) findPreference("pushSet");
        pushSet.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(!(Boolean) o){
                    //알림 해제를 눌렀을 때
                    Log.d(TAG, "onPreferenceChange: false");
                    alarm = false;
                    reqController.updateAlrm(alarm);


                }else{
                    //알림 설정을 눌렀을 때
                    Log.d(TAG, "onPreferenceChange: true");
                    alarm = true;
                    reqController.updateAlrm(alarm);

                }
                return true;
            }
        });

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();

        if(key.equals("infoSet")){
            intent = new Intent(getActivity(),InfoSetActivity.class);
            startActivityForResult(intent,0);
        }else if(key.equals("pwSet")){
            intent = new Intent(getActivity(),PwSetActivity.class);
            startActivityForResult(intent,0);
        }else if(key.equals("logoutSet")){
            reqController.logoutMember();
        }else if(key.equals("deleteSet")){
            reqController.deleteMember();
        }else if(key.equals("locationSet")){
            Intent intent = new Intent(getActivity(),LocationSetActivity.class);
            startActivityForResult(intent,0);
        }
        return true;
    }
}
