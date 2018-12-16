package com.airsharing.company.airsharing.activity.community;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.airsharing.company.airsharing.R;


/**
 * Created by hyunju on 2017-11-05.
 */

public class CommunityViewHolder extends RecyclerView.ViewHolder {
    protected TextView user;
    protected TextView content;
    protected TextView dust;

    public CommunityViewHolder(View itemView) {
        super(itemView);

        user = itemView.findViewById(R.id.textUser);
        content = itemView.findViewById(R.id.textContent);
        dust = itemView.findViewById(R.id.textDust);
    }

}
