package com.zhang.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.adapter.callback.SelectManager;
import com.zhang.library.adapter.holder.AdapterCallbackHolder;
import com.zhang.library.adapter.holder.AdapterDataHolder;
import com.zhang.library.adapter.holder.SelectManagerHolder;
import com.zhang.library.adapter.viewholder.EmptyViewHolder;
import com.zhang.library.adapter.viewholder.FooterViewHolder;
import com.zhang.library.adapter.viewholder.HeaderViewHolder;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.utils.CollectionUtils;

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
    /** 列表没有数据 */
    protected final int VIEW_TYPE_EMPTY_DATA = -0x100;
    /** HeaderView的基础ViewType */
    protected final int VIEW_TYPE_HEADER_BASE = -0x101;
    /** FooterView的基础ViewType */
    protected final int VIEW_TYPE_FOOTER_BASE = 0x101;

    protected final String TAG = getClass().getSimpleName();

    private Context mContext;

    private DataHolder<T> mDataHolder;
    private AdapterCallbackHolder<T> mCallbackHolder;
    private SelectManager<T> mSelectManager;

    private EmptyViewHolder<T> mEmptyHolder;
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
            return getDataHolder().getData(getRealPosition(position));
        }
        return null;
    }

    /**
     * 添加空数据展示ViewHolder
     *
     * @param emptyView 空数据view
     */
    public void setEmptyViewHolder(View emptyView) {
        if (emptyView == null)
            return;

        mEmptyHolder = new EmptyViewHolder<>(emptyView);

        notifyDataSetChanged();
    }

    /**
     * 添加空数据展示ViewHolder
     *
     * @param holder 空数据ViewHolder
     */
    public void setEmptyViewHolder(EmptyViewHolder<T> holder) {
        if (holder == null)
            return;

        mEmptyHolder = holder;

        notifyDataSetChanged();
    }

    /** 移除空数据展示ViewHolder */
    public void removeEmptyViewHolder() {
        mEmptyHolder = null;

        notifyDataSetChanged();
    }

    /**
     * 添加HeaderView
     *
     * @param header 头部View
     */
    public void addHeader(View header) {
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
    public void removeHeader(View header) {
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
    public void removeHeader(HeaderViewHolder<T> header) {
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
    public void addFooter(View footer) {
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
    public void removeFooter(View footer) {
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
    public void removeFooter(FooterViewHolder<T> footer) {
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

    /** 列表无数据 */
    protected boolean isEmptyViewType(int viewType) {
        return viewType == VIEW_TYPE_EMPTY_DATA;
    }

    /** 头部HeaderView */
    protected boolean isHeaderViewType(int viewType) {
        int headerCount = getHeaderCount();
        return VIEW_TYPE_HEADER_BASE - headerCount <= viewType && viewType <= VIEW_TYPE_HEADER_BASE;
    }

    /** 底部FooterView */
    protected boolean isFooterViewType(int viewType) {
        int footerCount = getFooterCount();
        return VIEW_TYPE_FOOTER_BASE <= viewType && viewType <= VIEW_TYPE_FOOTER_BASE + footerCount;
    }

    /** position位置是否是在HeaderView范围 */
    protected boolean isHeaderPosition(int position) {
        return hasHeaderView() && position < getHeaderCount();
    }

    /** position位置是否是数据范围 */
    protected boolean isDataPosition(int position) {
        if (hasHeaderView()) {
            int headerCount = getHeaderCount();
            int realPosition = position - headerCount;

            return 0 <= realPosition && realPosition < getDataCount();
        }
        return position < getDataCount();
    }

    /** position是否在FooterView范围 */
    protected boolean isFooterPosition(int position) {
        return !isHeaderPosition(position) && !isDataPosition(position);
//        return hasFooterView() && position >= (getHeaderCount() + getDataCount());
    }

    /**
     * 获取当前item的position在对应的数据模块中的真实位置。
     * <p>
     * 如果当前position属于头部数据，返回头部position
     * <p>
     * 如果当前position属于实际数据，返回数据position
     * <p>
     * 如果当前position属于尾部数据，返回尾部position
     *
     * @param position 当前item的position
     */
    protected int getRealPosition(int position) {
        if (isHeaderPosition(position)) {
            return position;
        }
        if (isFooterPosition(position)) {
            return position - getHeaderCount() - getDataCount();
        }

        return position - getHeaderCount();
    }

    protected HeaderViewHolder<T> getHeader(int viewType) {
        int index = Math.abs(viewType) - Math.abs(VIEW_TYPE_HEADER_BASE);
        return CollectionUtils.get(mHeaderList, index);
    }

    protected FooterViewHolder<T> getFooter(int viewType) {
        return CollectionUtils.get(mFooterList, viewType - 1);
    }

    /** 列表数据是否为空 */
    protected boolean isDataEmpty() {
        return getDataHolder().size() == 0;
    }

    /** 获取数据列表数量 */
    protected int getDataCount() {
        return isDataEmpty() ? (mEmptyHolder == null ? 0 : 1) : getDataHolder().size();
    }

    @Override
    public int getItemCount() {
        int dataSize = getDataCount();
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
            int realPosition = position - getHeaderCount() - getDataCount();
            return VIEW_TYPE_FOOTER_BASE + realPosition;
        }

        return isDataEmpty() ? VIEW_TYPE_EMPTY_DATA : VIEW_TYPE_NORMAL_DATA;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        setContext(parent.getContext());

        if (isHeaderViewType(viewType)) {
            HeaderViewHolder<T> headerHolder = getHeader(viewType);

            if (headerHolder != null)
                return headerHolder;
        }

        if (isFooterViewType(viewType)) {
            FooterViewHolder<T> footerHolder = getFooter(viewType);

            if (footerHolder != null)
                return footerHolder;
        }

        if (isEmptyViewType(viewType))
            return mEmptyHolder;

        return onCreateVHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecyclerViewHolder<T> viewHolder, final int position) {
        final int realPosition = getRealPosition(position);
        if (viewHolder instanceof HeaderViewHolder || viewHolder instanceof FooterViewHolder) {
            viewHolder.setAdapter(this);

            viewHolder.onBindData(null, realPosition);
            return;
        }

        final T data = getItemData(position);
        viewHolder.setAdapter(this);
        viewHolder.onBindData(data, realPosition);
        onBindData(viewHolder, data, realPosition);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallbackHolder().notifyItemClick(view, data, realPosition);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return getCallbackHolder().notifyItemLongClick(v, data, realPosition);
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
