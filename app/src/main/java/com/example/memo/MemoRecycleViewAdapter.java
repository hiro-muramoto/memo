package com.example.memo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MemoRecycleViewAdapter extends RecyclerView.Adapter<MemoViewHolder>{

    private List<RowMemoUnit> list;

    public MemoRecycleViewAdapter(List<RowMemoUnit> list) {
        this.list = list;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_row, parent,false);
        MemoViewHolder vh = new MemoViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, int position) {
        holder.titleView.setText(list.get(position).getTitle());
        holder.detailView.setText(list.get(position).getDetail());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}
