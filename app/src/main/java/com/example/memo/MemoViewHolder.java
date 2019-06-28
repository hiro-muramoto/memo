package com.example.memo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MemoViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView detailView;

    public MemoViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
        detailView = (TextView) itemView.findViewById(R.id.detail);

    }
}