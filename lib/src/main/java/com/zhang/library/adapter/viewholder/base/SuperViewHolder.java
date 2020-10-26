package com.zhang.library.adapter.viewholder.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * 超级适配器item使用的ViewHolder
 *
 * @author ZhangXiaoMing 2020-08-12 19:16 星期三
 */
public abstract class SuperViewHolder<T> extends BaseRecyclerViewHolder<T> {

    public SuperViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public SuperViewHolder(ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }
}
