package com.airsharing.company.airsharing.activity.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.Contents;

import java.util.ArrayList;

/**
 * Created by user on 2017-11-06.
 */

public class BoardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommunityAdapter communityAdapter;

    Button write;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        ArrayList<Contents> contentList = bundle.getParcelableArrayList("data");

        recyclerView = (RecyclerView) findViewById(R.id.community_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        communityAdapter = new CommunityAdapter(contentList, getApplicationContext());
        recyclerView.setAdapter(communityAdapter);

        write = (Button) findViewById(R.id.write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });

    }
}
