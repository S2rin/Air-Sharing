package com.airsharing.company.airsharing.activity.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.UploadContent;

/**
 * Created by user on 2017-11-06.
 */

public class EmptyBoardActivity extends AppCompatActivity {

    Button write;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board2);

        write = (Button) findViewById(R.id.write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadContent.class);
                startActivity(intent);
            }
        });
    }
}
