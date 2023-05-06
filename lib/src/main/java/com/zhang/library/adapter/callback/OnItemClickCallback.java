package com.zhang.library.adapter.callback;

import android.view.View;

/**
 * 列表视图中条目点击事件
 *
 * @author ZhangXiaoMing 2020-01-04 11:13 星期六
 */
public interface OnItemClickCallback<T> {

    /**
     * 点击事件
     *
     * @param view     控件
     * @param data     数据
     * @param position 位置
     */
    default void onItemClick(View view, T data, int position) {
    }

    /**
     * EmptyViewHolder中点击事件
     *
     * @param view 控件
     */
    default void onEmptyViewClick(View view) {
    }
}
