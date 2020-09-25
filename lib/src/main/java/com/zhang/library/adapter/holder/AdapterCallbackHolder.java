package com.zhang.library.adapter.holder;

import android.view.View;

import com.zhang.library.adapter.callback.OnItemClickCallback;
import com.zhang.library.adapter.callback.OnItemLongClickCallback;

/**
 * Adapter 点击事件、长按事件管理
 *
 * @author ZhangXiaoMing 2020-06-05 15:38 星期五
 */
public class AdapterCallbackHolder<T> {

    private OnItemClickCallback<T> mOnItemClickCallback;
    private OnItemLongClickCallback<T> mOnItemLongClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback<T> itemClickCallback) {
        this.mOnItemClickCallback = itemClickCallback;
    }

    public void setOnItemLongClickCallback(OnItemLongClickCallback<T> itemLongClickCallback) {
        this.mOnItemLongClickCallback = itemLongClickCallback;
    }

    public void notifyItemClick(View view, T model, int position) {
        if (mOnItemClickCallback == null) {
            return;
        }
        mOnItemClickCallback.onItemClick(view, model, position);
    }

    public boolean notifyItemLongClick(View view, T model, int position) {
        if (mOnItemLongClickCallback == null) {
            return false;
        }
        return mOnItemLongClickCallback.onItemLongClick(view, model, position);
    }
}