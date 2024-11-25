package com.zhang.library.adapter.viewholder.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.zhang.library.utils.context.ContextUtils;


/**
 * 列表适配器的基类ViewHolder
 *
 * @author ZhangXiaoMing 2020-01-03 17:27 星期五
 * @deprecated 列表改用 {@link RecyclerView}，不再使用{@link ListView}
 */
public abstract class BaseViewHolder {

    public final View itemView;

    public BaseViewHolder(View itemView) {
        this.itemView = itemView;
        onInit();
    }

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        this(LayoutInflater.from(ContextUtils.get()).inflate(layoutId, parent, false));
    }

    public final <T extends View> T findViewById(int id) {
        return itemView.findViewById(id);
    }

    /** 初始化 */
    public abstract void onInit();
}
