package com.zhang.library.adapter.holder;

import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 适配器数据持有者
 *
 * @author ZhangXiaoMing 2020-06-05 13:50 星期五
 */
public class AdapterDataHolder<T> implements DataHolder<T> {

    private List<T> mDataList;

    private final List<DataChangeCallback<T>> mDataChangeCallbackList;

    private DataTransform<T> mDataTransform;

    public AdapterDataHolder() {
        mDataList = new ArrayList<>();
        mDataChangeCallbackList = new ArrayList<>();
    }

    private ListIterator<DataChangeCallback<T>> getDataChangeCallbackListIterator() {
        return mDataChangeCallbackList.listIterator();
    }

    @Override
    public void addDataChangeCallback(final DataChangeCallback<T> callback) {
        if (callback == null || mDataChangeCallbackList.contains(callback)) {
            return;
        }

        mDataChangeCallbackList.add(callback);
    }

    @Override
    public void removeDataChangeCallback(final DataChangeCallback<T> callback) {
        if (callback == null) {
            return;
        }

        mDataChangeCallbackList.remove(callback);
    }

    @Override
    public void setDataTransform(DataTransform<T> transform) {
        this.mDataTransform = transform;
    }

    @Override
    public void setDataList(List<? extends T> dataList) {
        if (CollectionUtils.isEmpty(dataList))
            this.mDataList = new ArrayList<>();
        else
            this.mDataList = transformData(dataList);


        ArrayList<T> list = new ArrayList<>(mDataList);
        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataChanged(list);
        }
    }

    @Override
    public boolean addData(T data) {
        if (data == null) {
            return false;
        }

        data = transformData(data);

        int size = size();
        boolean add = mDataList.add(data);

        ArrayList<T> list = new ArrayList<>(1);
        list.add(data);

        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataAdded(size, list);
        }

        return add;
    }

    @Override
    public void addData(int index, T data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        data = transformData(data);

        if (mDataList.isEmpty()) {
            mDataList.add(data);
            index = 0;
        } else {
            if (!CollectionUtils.isIndexLegal(mDataList, index)) {
                return;
            }

            mDataList.add(index, data);
        }

        List<T> list = new ArrayList<>(1);
        list.add(data);

        ListIterator<DataChangeCallback<T>> iterator = getDataChangeCallbackListIterator();
        while (iterator.hasNext()) {
            iterator.next().onDataAdded(index, list);
        }
    }

    @Override
    public boolean addData(List<? extends T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return false;
        }

        dataList = transformData(dataList);

        int size = size();
        boolean result = mDataList.addAll(dataList);

        List<T> list = new ArrayList<>(dataList);
        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataAdded(size, list);
        }

        return result;
    }

    @Override
    public boolean addData(int index, List<? extends T> dataList) {
        if (CollectionUtils.isEmpty(dataList))
            return false;

        if (mDataList == null)
            mDataList = new ArrayList<>();

        dataList = transformData(dataList);

        boolean result;
        if (mDataList.isEmpty()) {
            result = mDataList.addAll(dataList);
        } else {
            if (!CollectionUtils.isIndexLegal(mDataList, index)) {
                return false;
            }

            result = mDataList.addAll(index, dataList);
        }

        List<T> list = new ArrayList<>(dataList);
        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataAdded(index, list);
        }

        return result;
    }

    @Override
    public boolean removeData(T data) {
        if (CollectionUtils.isEmpty(mDataList)) {
            return false;
        }

        int indexOf = mDataList.indexOf(data);
        if (indexOf == -1) {
            return false;
        }
        boolean remove = mDataList.remove(data);

        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataRemoved(indexOf, data);
        }

        return remove;
    }

    @Override
    public T removeData(int index) {
        if (!CollectionUtils.isIndexLegal(mDataList, index)) {
            return null;
        }
        T removeData = mDataList.remove(index);

        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataRemoved(index, removeData);
        }

        return removeData;
    }

    @Override
    public boolean removeData(List<T> list) {
        if (CollectionUtils.isEmpty(mDataList) || CollectionUtils.isEmpty(list))
            return false;

        List<Pair<Integer, T>> pairList = new ArrayList<>();
        for (T item : list) {
            int index = mDataList.indexOf(item);
            if (index != RecyclerView.NO_POSITION)
                pairList.add(Pair.create(index, item));
        }

        boolean result = mDataList.removeAll(list);

        if (result) {
            ListIterator<DataChangeCallback<T>> iterator = getDataChangeCallbackListIterator();
            for (Pair<Integer, T> pair : pairList) {
                while (iterator.hasNext()) {
                    iterator.next().onDataRemoved(pair.first, pair.second);
                }
            }
        }

        return result;
    }

    @Override
    public void updateData(int index, T data) {
        updateData(index, data, null);
    }

    @Override
    public void updateData(int index, T data, @Nullable Object payload) {
        if (!CollectionUtils.isIndexLegal(mDataList, index)) {
            return;
        }

        data = transformData(data);
        mDataList.set(index, data);

        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataChanged(index, data, payload);
        }
    }

    @Override
    public void clearData() {
        if (!CollectionUtils.isEmpty(mDataList))
            mDataList.clear();

        ListIterator<DataChangeCallback<T>> listIterator = getDataChangeCallbackListIterator();
        while (listIterator.hasNext()) {
            listIterator.next().onDataChanged(mDataList);
        }
    }

    @Override
    public int size() {
        return CollectionUtils.getSize(mDataList);
    }

    @Override
    public int indexOf(T data) {
        if (CollectionUtils.isEmpty(mDataList)) {
            return -1;
        }
        return mDataList.indexOf(data);
    }

    @Override
    public boolean isIndexLegal(int index) {
        return CollectionUtils.isIndexLegal(mDataList, index);
    }

    @Override
    public T getData(int index) {
        return CollectionUtils.get(mDataList, index);
    }

    @Override
    public List<T> getDataList() {
        return mDataList;
    }

    @SuppressWarnings("unchecked")
    private List<T> transformData(List<? extends T> sourceList) {
        if (mDataTransform == null) {
            return sourceList == null ? new ArrayList<>() : (List<T>) sourceList;
        }

        if (CollectionUtils.isEmpty(sourceList))
            return new ArrayList<>();

        List<T> dataList = new ArrayList<>(sourceList.size());
        for (T source : sourceList) {
            T data = transformData(source);
            dataList.add(data);
        }
        return dataList;
    }

    private T transformData(T source) {
        if (mDataTransform == null) {
            return source;
        }

        T data = mDataTransform.transformData(source);
        if (data == null) {
            return source;
        }

        return data;
    }

}
