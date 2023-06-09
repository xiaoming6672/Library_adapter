package com.zhang.library.adapter.viewholder.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.Adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的基类ViewHolder
 *
 * @author ZhangXiaoMing 2020-01-04 9:43 星期六
 */
public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    /** 选中状态变更的Payload */
    public static final String PAYLOAD_SELECT_CHANGED = "PAYLOAD_SELECT_CHANGED";

    private Adapter<T> adapter;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        onInit();
    }

    public BaseRecyclerViewHolder(ViewGroup parent, @LayoutRes int layoutId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public void setAdapter(Adapter<T> adapter) {
        this.adapter = adapter;
    }

    public Adapter<T> getAdapter() {
        return adapter;
    }


    public final <E extends View> E findViewById(int id) {
        return itemView.findViewById(id);
    }

    /**
     * 根据payload刷新绑定数据
     *
     * @param item     数据
     * @param position 位置
     * @param payload  刷新payload
     */
    public void onBindDataByPayload(T item, int position, Object payload) {
        if (PAYLOAD_SELECT_CHANGED.equals(payload))
            onItemSelectChanged(item, position);
    }

    /**
     * 数据选中状态变更
     *
     * @param item     数据
     * @param position 位置
     */
    protected void onItemSelectChanged(T item, int position) {
    }


    /** 初始化 */
    public abstract void onInit();

    /** 填充数据 */
    public abstract void onBindData(T item, int position);
}
