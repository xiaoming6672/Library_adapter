package com.zhang.library.adapter.callback;

import android.view.View;

/**
 * 列表视图中条目长按事件
 *
 * @author ZhangXiaoMing 2020-01-04 11:15 星期六
 */
public interface OnItemLongClickCallback<T> {
    boolean onItemLongClick(View view, T data, int position);
}
