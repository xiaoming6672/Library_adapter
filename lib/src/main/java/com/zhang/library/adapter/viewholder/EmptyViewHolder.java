package com.zhang.library.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;

import androidx.annotation.NonNull;

/**
 * RecyclerView无显示数据时候显示的ViewHolder
 *
 * @author ZhangXiaoMing 2021年9月30日16:58 星期四
 */
public class EmptyViewHolder<T> extends BaseRecyclerViewHolder<T> {

    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public EmptyViewHolder(ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onBindData(T item, int position) {
    }
}
