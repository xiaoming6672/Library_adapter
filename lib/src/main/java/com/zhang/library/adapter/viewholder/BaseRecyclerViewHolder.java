package com.zhang.library.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的基类ViewHolder
 *
 * @author ZhangXiaoMing 2020-01-04 9:43 星期六
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        onInit();
    }

    public BaseRecyclerViewHolder(ViewGroup parent, int layoutId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public final <E extends View> E findViewById(int id) {
        return itemView.findViewById(id);
    }

    /** 初始化 */
    public abstract void onInit();
}
