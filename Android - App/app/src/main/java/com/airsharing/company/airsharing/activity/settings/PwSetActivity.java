package com.airsharing.company.airsharing.activity.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.retrofit.RequestController;

/**
 * Created by surin on 2017. 10. 17..
 */

public class PwSetActivity extends AppCompatActivity {

    private EditText pw1, pw2;
    private Button pw_btn;
    private RequestController reqController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwset);

        //pwset_activity mapping
        pw1 = (EditText) findViewById(R.id.pw1);
        pw2 = (EditText) findViewById(R.id.pw2);
        pw_btn = (Button) findViewById(R.id.pw_btn);

        reqController = RequestController.getInstance();
        reqController.buildRequestController(getApplicationContext());

        pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nullChecking()){
                    strChecking();
                }
            }
        });
    }

    public void strChecking(){

        String p1str = pw1.getText().toString();
        String p2str = pw2.getText().toString();

        if(p1str.equals(p2str)){
            reqController.updatePasswd(p1str);
        }else {
            Toast.makeText(getBaseContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean nullChecking(){

        String p1str = pw1.getText().toString();
        String p2str = pw2.getText().toString();

        if(p1str.equals("") || p2str.equals("")){
            Toast.makeText(getBaseContext(),"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
