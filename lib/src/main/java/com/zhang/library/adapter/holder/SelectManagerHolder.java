package com.zhang.library.adapter.holder;

import com.zhang.library.adapter.Adapter;
import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.adapter.callback.SelectManager;
import com.zhang.library.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 选择管理
 *
 * @author ZhangXiaoMing 2020-06-19 11:26 星期五
 */
public class SelectManagerHolder<T> implements SelectManager<T>, DataHolder.DataChangeCallback<T> {

    private Adapter<T> mAdapter;

    private SelectMode mMode;
    private List<T> mList;
    private final Map<T, String> mSelectedMap = new IdentityHashMap<>();

    private final List<OnItemSelectChangeCallback<T>> mCallbackList = new ArrayList<>();

    public SelectManagerHolder(Adapter<T> adapter) {
        this.mAdapter = adapter;
        this.mAdapter.getDataHolder().addDataChangeCallback(this);

        this.mList = new ArrayList<>();
    }

    @Override
    public void setMode(SelectMode mode) {
        this.mMode = mode;
    }

    @Override
    public SelectMode getMode() {
        return mMode;
    }

    @Override
    public void addOnItemSelectChangeCallback(OnItemSelectChangeCallback<T> callback) {
        if (callback == null || mCallbackList.contains(callback)) {
            return;
        }
        this.mCallbackList.add(callback);
    }

    @Override
    public void removeOnItemSelectChangeCallback(OnItemSelectChangeCallback<T> callback) {
        this.mCallbackList.remove(callback);
    }

    @Override
    public void setItems(List<T> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.clear();
        mList.addAll(list);
        mSelectedMap.clear();

        if (getMode() == SelectMode.SINGLE_MUST_ONE) {
            setSelected(0, true);
        }
    }

    @Override
    public void updateItem(int index, T item) {
        if (!CollectionUtils.isIndexLegal(mList, index) || item == null) {
            return;
        }
        T old = CollectionUtils.get(mList, index);
        mList.set(index, item);

        if (isSelected(old)) {
            mSelectedMap.remove(old);
            mSelectedMap.put(item, "");
        }
    }

    @Override
    public void addItem(T item) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(item);
    }

    @Override
    public void addItems(List<T> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.addAll(list);
    }

    @Override
    public void addItems(int index, List<T> list) {
        if (CollectionUtils.isEmpty(list) || index > CollectionUtils.getSize(mList)) {
            return;
        }

        mList.addAll(index, list);
    }

    @Override
    public void removeItem(T item) {
        mList.remove(item);
        if (isSelected(item)) {
            switch (getMode()) {
                case SINGLE:
                case SINGLE_MUST_ONE:
                    clearSelected();
                    break;
                case MULTIPLE:
                    setSelected(item, false);
                    break;
                case MULTIPLE_MUST_ONE:
                    mSelectedMap.remove(item);
                    notifyUnSelected(item);

                    if (mSelectedMap.isEmpty()) {
                        setSelected(0, true);
                    }
                    break;
            }
        }
    }

    @Override
    public void setSelected(int index, boolean isSelected) {
        T item = CollectionUtils.get(mList, index);
        setSelected(item, isSelected);
    }

    @Override
    public void setSelected(T item, boolean isSelected) {
        if (item == null) {
            return;
        }
        switch (mMode) {
            case SINGLE:
                if (isSelected) {
                    selectSignle(item);
                } else {
                    mSelectedMap.remove(item);

                    notifyUnSelected(item);
                }
                break;
            case SINGLE_MUST_ONE:
                if (isSelected) {
                    selectSignle(item);
                }
                break;
            case MULTIPLE:
                if (isSelected) {
                    selectMultiple(item);
                } else {
                    mSelectedMap.remove(item);

                    notifyUnSelected(item);
                }
                break;
            case MULTIPLE_MUST_ONE:
                if (isSelected) {
                    selectMultiple(item);
                } else {
                    if (mSelectedMap.size() > 1) {
                        mSelectedMap.remove(item);

                        notifyUnSelected(item);
                    }
                }
                break;
        }
    }

    @Override
    public boolean isSelected(int index) {
        return isSelected(CollectionUtils.get(mList, index));
    }

    @Override
    public boolean isSelected(T item) {
        if (item == null) {
            return false;
        }

        return mSelectedMap.containsKey(item);
    }

    @Override
    public int getSelectedIndex() {
        if (!getMode().isSingleType())
            throw new UnsupportedOperationException("Unsupported for multiple select mode");

        T item = null;
        for (T key : mSelectedMap.keySet()) {
            item = key;
            break;
        }
        return mList.indexOf(item);
    }

    @Override
    public List<Integer> getSelectedIndexs() {
        if (getMode().isSingleType())
            throw new UnsupportedOperationException("Unsupported for single select mode");

        List<Integer> list = new ArrayList<>();
        for (T key : mSelectedMap.keySet()) {
            list.add(mList.indexOf(key));
        }

        return list;
    }

    @Override
    public T getSelectedItem() {
        if (!getMode().isSingleType()) {
            throw new UnsupportedOperationException("Unsupported for multiple select mode");
        }
        T item = null;
        for (T key : mSelectedMap.keySet()) {
            item = key;
            break;
        }
        return item;
    }

    @Override
    public List<T> getSelectedItems() {
        if (getMode().isSingleType()) {
            throw new UnsupportedOperationException("Unsupported for single select mode");
        }
        return new ArrayList<>(mSelectedMap.keySet());
    }

    @Override
    public void selectAll() {
        if (getMode().isSingleType()) {
            throw new UnsupportedOperationException("Unsupported for single select mode");
        }
        mSelectedMap.clear();
        for (T item : mList) {
            mSelectedMap.put(item, "");
        }
    }

    @Override
    public void clearSelected() {
        Iterator<T> iterator = mSelectedMap.keySet().iterator();
        while (iterator.hasNext()) {
            T key = iterator.next();
            iterator.remove();
            notifyUnSelected(key);
        }

        if (getMode() == SelectMode.SINGLE_MUST_ONE || getMode() == SelectMode.MULTIPLE_MUST_ONE) {
            setSelected(0, true);
        }
    }

    /** 单选选中 */
    private void selectSignle(T item) {
        Iterator<T> iterator = mSelectedMap.keySet().iterator();
        while (iterator.hasNext()) {
            T key = iterator.next();
            iterator.remove();
            notifyUnSelected(key);
        }
        mSelectedMap.clear();
        mSelectedMap.put(item, "");

        notifySelected(item);
    }

    /** 多选选中 */
    private void selectMultiple(T item) {
        mSelectedMap.put(item, "");

        notifySelected(item);
    }

    /** 通知item选中状态 */
    private void notifySelected(T item) {
        if (item == null) {
            return;
        }

        notifyItem(item, true);
    }

    /** 通知item取消选中状态 */
    private void notifyUnSelected(T item) {
        if (item == null) {
            return;
        }

        notifyItem(item, false);
    }

    private void notifyItem(T item, boolean isSelected) {
        for (OnItemSelectChangeCallback<T> callback : mCallbackList) {
            callback.onItemSelectedChange(item, isSelected);
        }
    }

    @Override
    public void onDataChanged(List<T> list) {
        setItems(list);
    }

    @Override
    public void onDataChanged(int index, T data) {
        updateItem(index, data);
    }

    @Override
    public void onDataAdded(int index, List<T> dataList) {
        addItems(index, dataList);
    }

    @Override
    public void onDataRemoved(int index, T data) {
        removeItem(data);
    }
}
