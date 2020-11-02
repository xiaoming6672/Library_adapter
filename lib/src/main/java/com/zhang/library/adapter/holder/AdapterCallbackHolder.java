package com.zhang.library.adapter.holder;

import android.view.View;

import com.zhang.library.adapter.callback.OnItemClickCallback;
import com.zhang.library.adapter.callback.OnItemLongClickCallback;
import com.zhang.library.utils.CollectionUtils;

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
     * 设置点击回调监听
     *
     * @param itemClickCallback 点击回调监听
     *
     * @deprecated
     */
    public void setOnItemClickCallback(OnItemClickCallback<T> itemClickCallback) {
        if (mOnItemClickCallbackList == null) {
            mOnItemClickCallbackList = new ArrayList<>();
        }

        mOnItemClickCallbackList.add(itemClickCallback);
    }


    /**
     * 设置长按回调监听
     *
     * @param itemLongClickCallback 长按回调监听
     *
     * @deprecated
     */
    public void setOnItemLongClickCallback(OnItemLongClickCallback<T> itemLongClickCallback) {
        if (mOnItemLongClickCallbackList == null) {
            mOnItemLongClickCallbackList = new ArrayList<>();
        }

        mOnItemLongClickCallbackList.add(itemLongClickCallback);
    }

    /**
     * 通知item点击事件
     *
     * @param view     点击的View
     * @param model    item的数据
     * @param position item的位置
     */
    public void notifyItemClick(View view, T model, int position) {
        if (CollectionUtils.isEmpty(mOnItemClickCallbackList)) {
            return;
        }

        for (OnItemClickCallback<T> callback : mOnItemClickCallbackList) {
            callback.onItemClick(view, model, position);
        }
    }

    /**
     * 通知item长按事件
     *
     * @param view     长按的View
     * @param model    item的数据
     * @param position item的位置
     */
    public boolean notifyItemLongClick(View view, T model, int position) {
        if (CollectionUtils.isEmpty(mOnItemLongClickCallbackList)) {
            return false;
        }

        for (OnItemLongClickCallback<T> callback : mOnItemLongClickCallbackList) {
            callback.onItemLongClick(view, model, position);
        }

        return true;
    }
}