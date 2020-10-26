package com.zhang.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.adapter.callback.SelectManager;
import com.zhang.library.adapter.holder.AdapterCallbackHolder;
import com.zhang.library.adapter.holder.AdapterDataHolder;
import com.zhang.library.adapter.holder.SelectManagerHolder;
import com.zhang.library.adapter.viewholder.FooterViewHolder;
import com.zhang.library.adapter.viewholder.HeaderViewHolder;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.common.view.BaseAppView;
import com.zhang.library.utils.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的基类Adapter
 *
 * @author ZhangXiaoMing 2020-01-04 9:44 星期六
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>>
        implements Adapter<T> {

    /** data数据的ViewType */
    protected final int VIEW_TYPE_NORMAL_DATA = 0;
    /** HeaderView的基础ViewType */
    protected final int VIEW_TYPE_HEADER_BASE = -1;
    /** FooterView的基础ViewType */
    protected final int VIEW_TYPE_FOOTER_BASE = 1;

    protected final String TAG = getClass().getSimpleName();

    private Context mContext;

    private DataHolder<T> mDataHolder;
    private AdapterCallbackHolder<T> mCallbackHolder;
    private SelectManager<T> mSelectManager;

    private List<HeaderViewHolder<T>> mHeaderList;
    private List<FooterViewHolder<T>> mFooterList;

    public final Context getContext() {
        return mContext;
    }

    protected void setContext(Context context) {
        this.mContext = context;
    }

    protected T getItemData(int position) {
        if (isDataPosition(position)) {
            return getDataHolder().getData(position);
        }
        return null;
    }

    /**
     * 添加HeaderView
     *
     * @param header 头部View
     */
    public void addHeader(BaseAppView header) {
        if (header == null) {
            return;
        }
        if (mHeaderList == null) {
            mHeaderList = new ArrayList<>();
        }

        mHeaderList.add(new HeaderViewHolder<T>(header));

        notifyDataSetChanged();
    }

    /**
     * 添加HeaderView
     *
     * @param header 头部ViewHolder
     */
    public void addHeader(HeaderViewHolder<T> header) {
        if (header == null) {
            return;
        }
        if (mHeaderList == null) {
            mHeaderList = new ArrayList<>();
        }

        mHeaderList.add(header);

        notifyDataSetChanged();
    }

    /**
     * 添加HeaderView
     *
     * @param list 头部ViewHolder列表
     */
    public void addHeader(List<HeaderViewHolder<T>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        if (mHeaderList == null) {
            mHeaderList = new ArrayList<>();
        }

        mHeaderList.addAll(list);

        notifyDataSetChanged();
    }

    /**
     * 移除HeaderView
     *
     * @param header 头部View
     */
    public void removeHeader(BaseAppView header) {
        if (header == null || CollectionUtils.isEmpty(mHeaderList)) {
            return;
        }

        Iterator<HeaderViewHolder<T>> iterator = mHeaderList.iterator();
        while (iterator.hasNext()) {
            HeaderViewHolder<T> holder = iterator.next();
            if (holder.itemView.equals(header)) {
                iterator.remove();
                break;
            }
        }

        notifyDataSetChanged();
    }

    /**
     * 移除HeaderView
     *
     * @param header 头部ViewHolder
     */
    public void removeHeader(HeaderViewHolder<BaseAppView> header) {
        if (header == null || CollectionUtils.isEmpty(mHeaderList)) {
            return;
        }

        mHeaderList.remove(header);

        notifyDataSetChanged();
    }

    /** 清空所有的HeaderView */
    public void clearHeader() {
        if (CollectionUtils.isEmpty(mHeaderList)) {
            return;
        }

        mHeaderList.clear();

        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     *
     * @param footer 底部View
     */
    public void addFooter(BaseAppView footer) {
        if (footer == null) {
            return;
        }
        if (mFooterList == null) {
            mFooterList = new ArrayList<>();
        }

        mFooterList.add(new FooterViewHolder<T>(footer));

        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     *
     * @param footer 底部ViewHolder
     */
    public void addFooter(FooterViewHolder<T> footer) {
        if (footer == null) {
            return;
        }
        if (mFooterList == null) {
            mFooterList = new ArrayList<>();
        }

        mFooterList.add(footer);

        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     *
     * @param list 底部ViewHolder列表
     */
    public void addFooter(List<FooterViewHolder<T>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        if (mFooterList == null) {
            mFooterList = new ArrayList<>();
        }

        mFooterList.addAll(list);

        notifyDataSetChanged();
    }

    /**
     * 移除FooterView
     *
     * @param footer 底部View
     */
    public void removeFooter(BaseAppView footer) {
        if (footer == null || CollectionUtils.isEmpty(mFooterList)) {
            return;
        }

        Iterator<FooterViewHolder<T>> iterator = mFooterList.iterator();
        while (iterator.hasNext()) {
            FooterViewHolder<T> holder = iterator.next();
            if (holder.itemView.equals(footer)) {
                iterator.remove();
                break;
            }
        }

        notifyDataSetChanged();
    }

    /**
     * 移除FooterView
     *
     * @param footer 底部ViewHolder
     */
    public void removeFooter(FooterViewHolder<BaseAppView> footer) {
        if (footer == null || CollectionUtils.isEmpty(mFooterList)) {
            return;
        }

        mFooterList.remove(footer);

        notifyDataSetChanged();
    }

    /** 清空所有的FooterView */
    public void clearFooter() {
        if (CollectionUtils.isEmpty(mFooterList)) {
            return;
        }

        mFooterList.clear();

        notifyDataSetChanged();
    }

    protected boolean hasHeaderView() {
        return !CollectionUtils.isEmpty(mHeaderList);
    }

    protected boolean hasFooterView() {
        return !CollectionUtils.isEmpty(mFooterList);
    }

    protected int getHeaderCount() {
        return CollectionUtils.getSize(mHeaderList);
    }

    protected int getFooterCount() {
        return CollectionUtils.getSize(mFooterList);
    }

    protected boolean isHeaderViewType(int viewType) {
        int headerCount = getHeaderCount();
        return VIEW_TYPE_HEADER_BASE - headerCount <= viewType && viewType <= VIEW_TYPE_HEADER_BASE;
    }

    protected boolean isFooterViewType(int viewType) {
        int footerCount = getFooterCount();
        return VIEW_TYPE_FOOTER_BASE <= viewType && viewType <= VIEW_TYPE_FOOTER_BASE + footerCount;
    }

    protected boolean isHeaderPosition(int position) {
        return hasHeaderView() && position < getHeaderCount();
    }

    protected boolean isDataPosition(int position) {
        if (hasHeaderView()) {
            int headerCount = getHeaderCount();
            int realPosition = position - headerCount;

            return 0 <= realPosition && realPosition < getDataHolder().size();
        }
        return position < getDataHolder().size();
    }

    protected boolean isFooterPosition(int position) {
        return !isHeaderPosition(position) && !isDataPosition(position);
//        return hasFooterView() && position >= (getHeaderCount() + getDataHolder().size());
    }

    protected HeaderViewHolder<T> getHeader(int viewType) {
        int index = Math.abs(viewType) - 1;
        return CollectionUtils.get(mHeaderList, index);
    }

    protected FooterViewHolder<T> getFooter(int viewType) {
        return CollectionUtils.get(mFooterList, viewType - 1);
    }

    @Override
    public int getItemCount() {
        int dataSize = getDataHolder().size();
        int headerSize = getHeaderCount();
        int footerSize = getFooterCount();
        return dataSize + headerSize + footerSize;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return VIEW_TYPE_HEADER_BASE - position;
        }
        if (isFooterPosition(position)) {
            int realPosition = position - getHeaderCount() - getDataHolder().size();
            return VIEW_TYPE_FOOTER_BASE + realPosition;
        }
        return VIEW_TYPE_NORMAL_DATA;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setContext(parent.getContext());

        if (isHeaderViewType(viewType)) {
            return getHeader(viewType);
        }
        if (isFooterViewType(viewType)) {
            return getFooter(viewType);
        }

        return onCreateVHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecyclerViewHolder<T> viewHolder, final int position) {
        if (viewHolder instanceof HeaderViewHolder || viewHolder instanceof FooterViewHolder) {
            viewHolder.setAdapter(this);

            //相对于Header或者Footer的真实位置
            int realPosition;
            if (viewHolder instanceof HeaderViewHolder) {
                realPosition = position;
            } else {
                realPosition = position - getHeaderCount() - getDataHolder().size();
            }

            viewHolder.onBindData(null, realPosition);
            return;
        }

        final T data = getItemData(position);
        viewHolder.setAdapter(this);
        viewHolder.onBindData(data, position);
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
    protected abstract BaseRecyclerViewHolder<T> onCreateVHolder(ViewGroup parent, int viewType);

    /**
     * 绑定填充数据
     *
     * @param viewHolder 对应的ViewHolder
     * @param data       数据
     * @param position   位置
     */
    protected abstract void onBindData(BaseRecyclerViewHolder<T> viewHolder, T data, int position);

}
