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
import java.util.List;

import androidx.annotation.CallSuper;
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

    //<editor-fold desc="EmptyHolder">

    /**
     * 添加空数据展示ViewHolder
     *
     * @param emptyView 空数据view
     */
    public void setEmptyViewHolder(View emptyView) {
        if (emptyView == null)
            return;

        setEmptyViewHolder(new EmptyViewHolder<>(emptyView));
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

        boolean dataEmpty = isDataEmpty();
        if (dataEmpty)
            notifyItemInserted(getHeaderCount());
    }

    /** 移除空数据展示ViewHolder */
    public void removeEmptyViewHolder() {
        mEmptyHolder = null;

        boolean dataEmpty = isDataEmpty();
        if (dataEmpty)
            notifyItemRemoved(getHeaderCount());
    }

    /** 是否有EmptyViewHolder */
    protected boolean hasEmptyView() {
        return mEmptyHolder != null;
    }

    /** 列表无数据 */
    protected boolean isEmptyViewType(int viewType) {
        return viewType == VIEW_TYPE_EMPTY_DATA;
    }

    //</editor-fold>

    //<editor-fold desc="Header/Footer">

    //<editor-fold desc="Header">

    /**
     * 添加HeaderView
     *
     * @param header 头部View
     */
    public void addHeader(View header) {
        if (header == null) {
            return;
        }

        addHeader(new HeaderViewHolder<>(header));
    }

    /**
     * 添加HeaderView
     *
     * @param header 头部ViewHolder
     */
    public void addHeader(HeaderViewHolder<T> header) {
        if (header == null)
            return;

        List<HeaderViewHolder<T>> list = new ArrayList<>();
        list.add(header);
        addHeader(list);
    }

    /**
     * 添加HeaderView
     *
     * @param list 头部ViewHolder列表
     */
    public void addHeader(List<HeaderViewHolder<T>> list) {
        if (CollectionUtils.isEmpty(list))
            return;

        if (mHeaderList == null)
            mHeaderList = new ArrayList<>();

        int count = 0;
        for (HeaderViewHolder<T> holder : list) {
            if (containHeader(holder))
                continue;

            mHeaderList.add(holder);
            count++;
        }

        if (count == 0)
            return;

        int position = mHeaderList.size() - count;
        notifyItemRangeInserted(position, count);
    }

    /**
     * 移除HeaderView
     *
     * @param view 头部View
     */
    public void removeHeader(View view) {
        if (view == null || CollectionUtils.isEmpty(mHeaderList))
            return;

        HeaderViewHolder<T> header = null;
        for (HeaderViewHolder<T> holder : mHeaderList) {
            if (holder.itemView.equals(view)) {
                header = holder;
                break;
            }
        }

        if (header == null)
            return;

        removeHeader(header);
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

        int index = mHeaderList.indexOf(header);
        boolean result = mHeaderList.remove(header);

        if (result)
            notifyItemRemoved(index);
    }

    /**
     * 判断header是否已添加
     *
     * @param header header
     */
    public boolean containHeader(HeaderViewHolder<T> header) {
        if (header == null || CollectionUtils.isEmpty(mHeaderList))
            return false;

        return mHeaderList.contains(header);
    }

    /**
     * 判断布局是否已被添加到header中
     *
     * @param view 布局
     */
    public boolean containHeader(View view) {
        if (view == null || CollectionUtils.isEmpty(mHeaderList))
            return false;

        for (HeaderViewHolder<T> holder : mHeaderList) {
            if (holder.itemView.equals(view))
                return true;
        }

        return false;
    }

    /** 清空所有的HeaderView */
    public void clearHeader() {
        if (CollectionUtils.isEmpty(mHeaderList))
            return;

        int count = mHeaderList.size();
        mHeaderList.clear();

        notifyItemRangeRemoved(0, count);
    }
    //</editor-fold>

    //<editor-fold desc="Footer">

    /**
     * 添加FooterView
     *
     * @param footer 底部View
     */
    public void addFooter(View footer) {
        if (footer == null)
            return;

        addFooter(new FooterViewHolder<>(footer));
    }

    /**
     * 添加FooterView
     *
     * @param footer 底部ViewHolder
     */
    public void addFooter(FooterViewHolder<T> footer) {
        if (footer == null)
            return;

        List<FooterViewHolder<T>> list = new ArrayList<>();
        list.add(footer);
        addFooter(list);
    }

    /**
     * 添加FooterView
     *
     * @param list 底部ViewHolder列表
     */
    public void addFooter(List<FooterViewHolder<T>> list) {
        if (CollectionUtils.isEmpty(list))
            return;

        if (mFooterList == null)
            mFooterList = new ArrayList<>();

        int count = 0;
        for (FooterViewHolder<T> holder : list) {
            if (mFooterList.contains(holder))
                continue;

            mFooterList.add(holder);
            count++;
        }

        if (count == 0)
            return;

        int position = getItemCount();
        notifyItemRangeInserted(position, count);
    }

    /**
     * 移除FooterView
     *
     * @param view 底部View
     */
    public void removeFooter(View view) {
        if (view == null || CollectionUtils.isEmpty(mFooterList))
            return;

        FooterViewHolder<T> footer = null;
        for (FooterViewHolder<T> holder : mFooterList) {
            if (holder.itemView.equals(view)) {
                footer = holder;
                break;
            }
        }

        if (footer == null)
            return;

        removeFooter(footer);
    }

    /**
     * 移除FooterView
     *
     * @param footer 底部ViewHolder
     */
    public void removeFooter(FooterViewHolder<T> footer) {
        if (footer == null || CollectionUtils.isEmpty(mFooterList))
            return;

        int index = mFooterList.indexOf(footer) + getHeaderCount() + getDataCount();
        boolean result = mFooterList.remove(footer);

        if (result)
            notifyItemRemoved(index);
    }

    /** 清空所有的FooterView */
    public void clearFooter() {
        if (CollectionUtils.isEmpty(mFooterList))
            return;

        int count = mFooterList.size();
        mFooterList.clear();

        int position = getHeaderCount() + getDataCount();
        notifyItemRangeRemoved(position, count);
    }
    //</editor-fold>

    //<editor-fold desc="判断方法">

    /** 是否有添加Header */
    public boolean hasHeaderHolder() {
        return !CollectionUtils.isEmpty(mHeaderList);
    }

    /** 获取Header数量 */
    public int getHeaderCount() {
        return CollectionUtils.getSize(mHeaderList);
    }

    /** 是否有添加Footer */
    public boolean hasFooterHolder() {
        return !CollectionUtils.isEmpty(mFooterList);
    }

    /** 获取Footer数量 */
    public int getFooterCount() {
        return CollectionUtils.getSize(mFooterList);
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
    public boolean isHeaderPosition(int position) {
        return hasHeaderHolder() && position < getHeaderCount();
    }

    /** position是否在FooterView范围 */
    public boolean isFooterPosition(int position) {
        return !isHeaderPosition(position) && !isDataPosition(position);
//        return hasFooterView() && position >= (getHeaderCount() + getDataCount());
    }
    //</editor-fold>

    //</editor-fold>

    /** position位置是否是数据范围 */
    public boolean isDataPosition(int position) {
        if (hasHeaderHolder()) {
            int headerCount = getHeaderCount();
            int realPosition = position - headerCount;

            return 0 <= realPosition && realPosition < getDataCount();
        }
        return position < getDataCount();
    }

    /**
     * 获取当前item的position在对应的数据模块中的真实位置。
     * <li> 如果当前position属于头部数据，返回头部position
     * <li> 如果当前position属于实际数据，返回数据position
     * <li> 如果当前position属于尾部数据，返回尾部position
     *
     * @param position 当前item的position
     */
    public int getRealPosition(int position) {
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
        return CollectionUtils.get(mFooterList, viewType - VIEW_TYPE_FOOTER_BASE);
    }

    /** 列表数据是否为空 */
    public boolean isDataEmpty() {
        return getDataHolder().size() == 0;
    }

    /**
     * 获取数据列表数量
     * <br>注意：此方法不能完全替代{@link DataHolder#size()}使用
     * <br>如果列表为空，并且有添加{@link #mEmptyHolder}，此时返回的值为1
     */
    public int getDataCount() {
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

    @Override
    @CallSuper
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        setContext(recyclerView.getContext());
    }

    @Override
    @CallSuper
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        setContext(null);
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        if (viewHolder instanceof EmptyViewHolder) {
            viewHolder.itemView.setOnClickListener(v -> getCallbackHolder().notifyEmptyViewClick(v));
            viewHolder.itemView.setOnLongClickListener(v -> getCallbackHolder().notifyEmptyViewLongClick(v));

            viewHolder.setAdapter(this);
            viewHolder.onBindData(null, realPosition);
            return;
        }

        final T data = getItemData(position);

        viewHolder.itemView.setOnClickListener(view -> getCallbackHolder().notifyItemClick(view, data, realPosition));
        viewHolder.itemView.setOnLongClickListener(v -> getCallbackHolder().notifyItemLongClick(v, data, realPosition));

        viewHolder.setAdapter(this);
        viewHolder.onBindData(data, realPosition);
        onBindData(viewHolder, data, realPosition);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder<T> holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        }

        for (Object payload : payloads) {
            final T data = getItemData(position);
            holder.onBindDataByPayload(data, position, payload);
        }
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
                    notifyItemChanged(index);
                }

                @Override
                public void onDataAdded(int index, List<T> dataList) {
                    //添加的数据数量
                    int addedCount = CollectionUtils.getSize(dataList);
                    //如果是true，表示本次添加数据之前，列表数据数量为0
                    boolean isEmptyBeforeAdd = getDataHolder().size() == addedCount;

                    //添加数据之前是空列表，并且有设置空状态显示，则先移除掉空状态的显示，然后再通知新增数据的显示
                    if (isEmptyBeforeAdd && hasEmptyView())
                        notifyItemRemoved(0);

                    //通知增加新数据
                    notifyItemRangeInserted(index, addedCount);
                }

                @Override
                public void onDataRemoved(int index, T data) {
                    notifyItemRemoved(index);
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
    protected void onBindData(BaseRecyclerViewHolder<T> viewHolder, T data, int position) {
    }

}
