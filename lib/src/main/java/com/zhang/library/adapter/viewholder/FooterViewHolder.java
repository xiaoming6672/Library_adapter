package com.zhang.library.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;

import androidx.annotation.NonNull;

/**
 * RecyclerView自定义FooterView的ViewHolder
 *
 * @author ZhangXiaoMing 2020-10-26 10:58 星期一
 */
public class FooterViewHolder<T> extends BaseRecyclerViewHolder<T> {

    public FooterViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public FooterViewHolder(ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onBindData(T item, int position) {
    }
}
