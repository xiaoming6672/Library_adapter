package com.zhang.library.adapter.callback;

import android.view.View;

/**
 * 列表视图中条目长按事件
 *
 * @author ZhangXiaoMing 2020-01-04 11:15 星期六
 */
public interface OnItemLongClickCallback<T> {

    /**
     * 长按事件
     *
     * @param view     控件
     * @param data     数据
     * @param position 位置
     */
    default boolean onItemLongClick(View view, T data, int position) {
        return false;
    }

    /**
     * EmptyViewHolder中长按点击事件
     *
     * @param view 控件
     */
    default boolean onEmptyViewLongClick(View view) {
        return false;
    }

}
