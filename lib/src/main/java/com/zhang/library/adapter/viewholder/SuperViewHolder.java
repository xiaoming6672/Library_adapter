package com.zhang.library.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author ZhangXiaoMing 2020-08-12 19:16 星期三
 */
public abstract class SuperViewHolder<T> extends BaseRecyclerViewHolder {

    public SuperViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public SuperViewHolder(ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }

    public abstract void onBindData(T item, int position);
}
