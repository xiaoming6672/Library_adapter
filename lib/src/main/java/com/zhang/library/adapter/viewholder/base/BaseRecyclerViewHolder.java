package com.zhang.library.adapter.viewholder.base;

import android.content.Context;
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

    public BaseRecyclerViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public void setAdapter(Adapter<T> adapter) {
        this.adapter = adapter;
    }

    public Adapter<T> getAdapter() {
        return adapter;
    }

    protected Context getContext() {
        return itemView.getContext();
    }


    public final <E extends View> E findViewById(int id) {
        return itemView.findViewById(id);
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     *
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * {@link RecyclerView.Adapter#onViewDetachedFromWindow(RecyclerView.ViewHolder)
     * onViewDetachedFromWindow} those resources should be restored here.</p>
     */
    public void onViewAttachedToWindow() {
    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     *
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they are not
     * visible, attaching and detaching them as appropriate.</p>
     */
    public void onViewDetachedFromWindow() {
    }

    /**
     * Called when a view created by this adapter has been recycled.
     *
     * <p>A view is recycled when a {@link RecyclerView.LayoutManager} decides that it no longer
     * needs to be attached to its parent {@link RecyclerView}. This can be because it has fallen
     * out of visibility or a set of cached views represented by views still attached to the parent
     * RecyclerView. If an item view has large or expensive data bound to it such as large bitmaps,
     * this may be a good place to release those resources.</p>
     * <p>
     * RecyclerView calls this method right before clearing ViewHolder's internal data and sending
     * it to RecycledViewPool. This way, if ViewHolder was holding valid information before being
     * recycled, you can call {@link RecyclerView.ViewHolder#getBindingAdapterPosition()} to get its
     * adapter position.
     */
    public void onViewRecycled() {
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
