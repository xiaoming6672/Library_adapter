package com.zhang.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.adapter.callback.SelectManager;
import com.zhang.library.adapter.holder.AdapterCallbackHolder;
import com.zhang.library.adapter.holder.AdapterDataHolder;
import com.zhang.library.adapter.holder.SelectManagerHolder;
import com.zhang.library.adapter.viewholder.BaseRecyclerViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的基类Adapter
 *
 * @author ZhangXiaoMing 2020-01-04 9:44 星期六
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder>
        implements Adapter<T> {

    protected final String TAG = getClass().getSimpleName();

    private Context mContext;

    private DataHolder<T> mDataHolder;
    private AdapterCallbackHolder<T> mCallbackHolder;
    private SelectManager<T> mSelectManager;

    public final Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public List<T> getDataList() {
        return getDataHolder().getDataList();
    }

    public T getItemData(int position) {
        return getDataHolder().getData(position);
    }

    @Override
    public int getItemCount() {
        return getDataHolder().size();
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setContext(parent.getContext());
        return onCreateVHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecyclerViewHolder viewHolder, final int position) {
        final T data = getItemData(position);
        onBindData(viewHolder, data, position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallbackHolder().notifyItemClick(view, data, position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return getCallbackHolder().notifyItemLongClick(v, data, position);
            }
        });
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
        if (mSelectManager == null) {
            mSelectManager = new SelectManagerHolder<>(this);
        }
        return mSelectManager;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   Item父控件
     * @param viewType view类型
     */
    protected abstract BaseRecyclerViewHolder onCreateVHolder(ViewGroup parent, int viewType);

    /**
     * 绑定填充数据
     *
     * @param viewHolder 对应的ViewHolder
     * @param data       数据
     * @param position   位置
     */
    protected abstract void onBindData(BaseRecyclerViewHolder viewHolder, T data, int position);

}
