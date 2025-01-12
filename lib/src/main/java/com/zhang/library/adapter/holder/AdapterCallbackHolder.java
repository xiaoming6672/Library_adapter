package com.zhang.library.adapter.holder;

import android.view.View;

import com.zhang.library.adapter.Adapter;
import com.zhang.library.adapter.callback.OnItemClickCallback;
import com.zhang.library.adapter.callback.OnItemLongClickCallback;
import com.zhang.library.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter 点击事件、长按事件管理
 *
 * @author ZhangXiaoMing 2020-06-05 15:38 星期五
 */
public class AdapterCallbackHolder<T> {

    private List<OnItemClickCallback<T>> mOnItemClickCallbackList;
    private List<OnItemLongClickCallback<T>> mOnItemLongClickCallbackList;

    private final WeakReference<Adapter<T>> mAdapter;

    public AdapterCallbackHolder(Adapter<T> adapter) {
        mAdapter = new WeakReference<>(adapter);
    }

    /** 添加点击事件监听 */
    public void addOnItemClickCallback(OnItemClickCallback<T> itemClickCallback) {
        if (mOnItemClickCallbackList == null) {
            mOnItemClickCallbackList = new ArrayList<>();
        }

        mOnItemClickCallbackList.add(itemClickCallback);
    }

    /** 添加长按事件监听 */
    public void addOnItemLongClickCallback(OnItemLongClickCallback<T> itemLongClickCallback) {
        if (mOnItemLongClickCallbackList == null) {
            mOnItemLongClickCallbackList = new ArrayList<>();
        }

        mOnItemLongClickCallbackList.add(itemLongClickCallback);
    }

    /**
     * 通知EmptyViewHolder点击事件
     *
     * @param view 控件
     */
    public void notifyEmptyViewClick(View view) {
        if (CollectionUtils.isEmpty(mOnItemClickCallbackList))
            return;

        for (OnItemClickCallback<T> callback : mOnItemClickCallbackList) {
            callback.onEmptyViewClick(view);
        }
    }

    /**
     * 通知item点击事件
     *
     * @param view     点击的View
     * @param position item的位置
     */
    public void notifyItemClick(View view, int position) {
        if (CollectionUtils.isEmpty(mOnItemClickCallbackList)) {
            return;
        }

        if (mAdapter.get() == null) return;

        T data = mAdapter.get().getDataHolder().getData(position);
        for (OnItemClickCallback<T> callback : mOnItemClickCallbackList) {
            callback.onItemClick(view, data, position);
        }
    }

    public boolean notifyEmptyViewLongClick(View view) {
        if (CollectionUtils.isEmpty(mOnItemLongClickCallbackList))
            return false;

        boolean handled = false;
        for (OnItemLongClickCallback<T> callback : mOnItemLongClickCallbackList) {
            boolean result = callback.onEmptyViewLongClick(view);
            if (result)
                handled = true;
        }

        return handled;
    }

    /**
     * 通知item长按事件
     *
     * @param view     长按的View
     * @param position item的位置
     */
    public boolean notifyItemLongClick(View view, int position) {
        if (CollectionUtils.isEmpty(mOnItemLongClickCallbackList)) {
            return false;
        }

        if (mAdapter.get() == null) return false;

        T data = mAdapter.get().getDataHolder().getData(position);

        boolean handled = false;
        for (OnItemLongClickCallback<T> callback : mOnItemLongClickCallbackList) {
            boolean result = callback.onItemLongClick(view, data, position);
            if (result)
                handled = true;
        }

        return handled;
    }
}