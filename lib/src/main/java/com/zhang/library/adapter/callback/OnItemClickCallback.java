package com.zhang.library.adapter.callback;

import android.view.View;

/**
 * 列表视图中条目点击事件
 *
 * @author ZhangXiaoMing 2020-01-04 11:13 星期六
 */
public interface OnItemClickCallback<T> {
    void onItemClick(View itemView, T data, int position);
}
