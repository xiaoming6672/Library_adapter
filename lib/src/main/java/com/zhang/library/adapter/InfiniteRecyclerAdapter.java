package com.zhang.library.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 循环列表包裹层适配器
 *
 * @author ZhangXiaoMing 2023-01-28 16:33 周六
 */
public class InfiniteRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final String TAG = getClass().getSimpleName();

    private RecyclerView.Adapter<VH> mAdapter;


    public void setAdapter(RecyclerView.Adapter<VH> adapter) {
        if (mAdapter == adapter)
            return;

        if (mAdapter != null)
            mAdapter.unregisterAdapterDataObserver(mObserver);

        this.mAdapter = adapter;
        if (mAdapter == null)
            return;

        mAdapter.registerAdapterDataObserver(mObserver);
        notifyDataSetChanged();
    }

    public RecyclerView.Adapter<VH> getAdapter() {
        return mAdapter;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        mAdapter.onBindViewHolder(holder, getRealPosition(position));
    }

    @Override
    public int getItemCount() {
        if (mAdapter == null)
            return 0;

        int itemCount = mAdapter.getItemCount();
        if (itemCount <= 1)
            return itemCount;

        return Integer.MAX_VALUE;
    }

    @Override
    public long getItemId(int position) {
        if (mAdapter != null)
            return mAdapter.getItemId(getRealPosition(position));

        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mAdapter != null)
            return mAdapter.getItemViewType(getRealPosition(position));

        return super.getItemViewType(position);
    }


    @Override
    public boolean onFailedToRecycleView(@NonNull VH holder) {
        if (mAdapter != null)
            return mAdapter.onFailedToRecycleView(holder);

        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);

        if (mAdapter != null)
            mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        super.onViewDetachedFromWindow(holder);

        if (mAdapter != null)
            mAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        if (mAdapter != null)
            mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        if (mAdapter != null)
            mAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    /**
     * 获取真实的位置
     *
     * @param position 当前位置
     */
    private int getRealPosition(int position) {
        if (mAdapter == null || mAdapter.getItemCount() == 0)
            return position;

        int itemCount = mAdapter.getItemCount();
        return position % itemCount;
    }

    private final RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            Log.i(TAG, "registerAdapterDataObserver>>>onChanged()");
            super.onChanged();

            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeChanged()");
            super.onItemRangeChanged(positionStart, itemCount);

            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeChanged()");
            super.onItemRangeChanged(positionStart, itemCount, payload);

            notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeInserted()");
            super.onItemRangeInserted(positionStart, itemCount);

            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeRemoved()");
            super.onItemRangeRemoved(positionStart, itemCount);

            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeMoved()");
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);

            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onStateRestorationPolicyChanged() {
            Log.i(TAG, "registerAdapterDataObserver>>>onStateRestorationPolicyChanged()");
            super.onStateRestorationPolicyChanged();

            notifyDataSetChanged();
        }
    };

}
