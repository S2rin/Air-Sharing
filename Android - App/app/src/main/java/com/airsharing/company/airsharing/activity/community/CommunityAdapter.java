package com.airsharing.company.airsharing.activity.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airsharing.company.airsharing.R;
import com.airsharing.company.airsharing.model.Contents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-11-06.
 */

public class CommunityAdapter extends RecyclerView.Adapter<CommunityViewHolder> {

    private ArrayList<Contents> contents;
    private Contents uploadContent;
    private Context context;
    CommunityViewHolder holder;

    public CommunityAdapter(ArrayList<Contents> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_row, parent, false);
        return new CommunityViewHolder(v);
    }

    @Override
    public void onViewRecycled(CommunityViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        uploadContent = contents.get(position);

        this.holder = holder;
        holder.user.setText(uploadContent.getUuid());
        holder.dust.setText(uploadContent.getDust());
        holder.content.setText(uploadContent.getContent());
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }
}
