package com.zhang.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.adapter.callback.SelectManager;
import com.zhang.library.adapter.holder.AdapterCallbackHolder;
import com.zhang.library.adapter.holder.AdapterDataHolder;
import com.zhang.library.adapter.holder.SelectManagerHolder;
import com.zhang.library.adapter.viewholder.base.BaseViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * ListView/GridView的基类适配器
 *
 * @author ZhangXiaoMing 2020-01-03 17:20 星期五
 * @deprecated 列表改用 {@link RecyclerView}，Adapter使用{@link BaseRecyclerAdapter}
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter implements Adapter<T> {

    protected final String TAG = getClass().getSimpleName();

    private Context mContext;

    /** 数据管理 */
    private DataHolder<T> mDataHolder;
    /** 回调管理 */
    private AdapterCallbackHolder<T> mCallbackHolder;
    /** 选择管理 */
    private SelectManager<T> mSelectedManager;

    @Override
    public int getCount() {
        return getDataHolder().size();
    }

    @Override
    public T getItem(int position) {
        return getDataHolder().getData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setContext(Context context) {
        this.mContext = context;
    }

    public final Context getContext() {
        return mContext;
    }

    @Override
    public DataHolder<T> getDataHolder() {
        if (mDataHolder == null) {
            mDataHolder = new AdapterDataHolder<>();
            mDataHolder.addDataChangeCallback(new DataHolder.DataChangeCallback<T>() {
                @Override
                public void onDataChanged(List<T> list) {
                    notifyDataSetChanged();
                }

                @Override
                public void onDataChanged(int index, T data) {
                    notifyDataSetChanged();
                }

                @Override
                public void onDataAdded(int index, List<T> dataList) {
                    notifyDataSetChanged();
                }

                @Override
                public void onDataRemoved(int index, T data) {
                    notifyDataSetChanged();
                }
            });
        }
        return mDataHolder;
    }

    @Override
    public AdapterCallbackHolder<T> getCallbackHolder() {
        if (mCallbackHolder == null) {
            mCallbackHolder = new AdapterCallbackHolder<>();
        }
        return mCallbackHolder;
    }

    @Override
    public SelectManager<T> getSelectManager() {
        if (mSelectedManager == null) {
            mSelectedManager = new SelectManagerHolder<>(this);
        }
        return mSelectedManager;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        BaseViewHolder holder;
        setContext(viewGroup.getContext());

        if (view == null) {
            holder = onCreateViewHolder(viewGroup, getItemViewType(position), position);
            view = holder.itemView;
            view.setTag(holder);
        } else {
            holder = (BaseViewHolder) view.getTag();
        }

        final T data = getItem(position);
        onBindData(holder, data, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallbackHolder().notifyItemClick(view, data, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return getCallbackHolder().notifyItemLongClick(view, data, position);
            }
        });

        return view;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   Item父控件
     * @param viewType view类型
     * @param position 位置
     */
    protected abstract BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, int position);

    /**
     * 绑定填充数据
     *
     * @param viewHolder 对应的ViewHolder
     * @param data       数据
     * @param position   位置
     */
    protected abstract void onBindData(BaseViewHolder viewHolder, T data, int position);
}
