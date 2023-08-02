package com.zhang.library.adapter.callback;

import java.util.List;

/**
 * 数据持有
 *
 * @author ZhangXiaoMing 2020-06-05 14:01 星期五
 */
public interface DataHolder<T> {

    void addDataChangeCallback(final DataChangeCallback<T> callback);

    void removeDataChangeCallback(final DataChangeCallback<T> callback);

    void setDataTransform(DataTransform<T> transform);

    void setDataList(final List<? extends T> dataList);

    boolean addData(final T data);

    void addData(final int index, final T data);

    boolean addData(final List<? extends T> dataList);

    boolean addData(final int index, final List<? extends T> dataList);

    boolean removeData(final T data);

    T removeData(final int index);

    void updateData(final int index, final T data);

    void clearData();

    int size();

    int indexOf(final T data);

    boolean isIndexLegal(int index);

    T getData(final int index);

    List<T> getDataList();

   interface DataChangeCallback<T> {

        void onDataChanged(List<T> list);

        void onDataChanged(int index, T data);

        void onDataAdded(int index, List<T> dataList);

        void onDataRemoved(int index, T data);
    }

   interface DataTransform<T> {
        /**
         * 转换数据
         *
         * @param source 源数据
         *
         * @return null-不进行转换
         */
        T transformData(T source);
    }
}
