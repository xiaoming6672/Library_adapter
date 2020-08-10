package com.zhang.library.adapter.callback;

import java.util.List;

/**
 * 选择模式
 *
 * @author ZhangXiaoMing 2020-06-19 10:06 星期五
 */
public interface SelectManager<T> {

    /**
     * 设置选择模式
     *
     * @param mode 选择模式
     */
    void setMode(SelectMode mode);

    /** 获取选择模式 */
    SelectMode getMode();

    /**
     * 设置item选择状态改变回调
     *
     * @param callback 回调
     */
    void addOnItemSelectChangeCallback(OnItemSelectChangeCallback<T> callback);

    /** 移除回调 */
    void removeOnItemSelectChangeCallback(OnItemSelectChangeCallback<T> callback);

    /**
     * 设置数据
     *
     * @param list 数据列表
     */
    void setItems(List<T> list);

    /**
     * 更新指定位置数据
     *
     * @param index 位置
     * @param item  item数据
     */
    void updateItem(int index, T item);

    /**
     * 添加数据在队列末尾
     *
     * @param item 数据
     */
    void addItem(T item);

    /**
     * 添加数据列表在队列末尾
     *
     * @param list 数据列表
     */
    void addItems(List<T> list);

    /**
     * 在指定位置添加数据列表
     *
     * @param index 指定位置
     * @param list  数据列表
     */
    void addItems(int index, List<T> list);

    /**
     * 移除数据
     *
     * @param item 被移除的数据
     */
    void removeItem(T item);

    /**
     * 设置选中状态
     *
     * @param index      位置
     * @param isSelected 选中状态
     */
    void setSelected(int index, boolean isSelected);

    /**
     * 设置选中状态
     *
     * @param item       数据
     * @param isSelected 选中状态
     */
    void setSelected(T item, boolean isSelected);

    /**
     * 指定位置的数据是否被选中
     *
     * @param index 指定位置
     *
     * @return <br><b>true:</b>是  <br><b>false:</b>不是
     */
    boolean isSelected(int index);

    /**
     * 指定数据是否被选中
     *
     * @param item 指定的数据
     *
     * @return <br><b>true:</b>是  <br><b>false:</b>不是
     */
    boolean isSelected(T item);

    /** 获取被选中的位置，{@link SelectMode#isSingleType()} == true 的时候才可用 */
    int getSelectedIndex();

    /** 获取被选中的位置列表，{@link SelectMode#isSingleType()} == false 的时候才可用 */
    List<Integer> getSelectedIndexs();

    /** 获取被选中的数据，{@link SelectMode#isSingleType()} == true 的时候才可用 */
    T getSelectedItem();

    /** 获取被选中的数据列表，{@link SelectMode#isSingleType()} == false 的时候才可用 */
    List<T> getSelectedItems();

    /** 全选，{@link SelectMode#isSingleType()} == false 的时候才可用 */
    void selectAll();

    /** 清除已选中的 */
    void clearSelected();

    interface OnItemSelectChangeCallback<T> {
        /**
         * item数据选中状态改变
         *
         * @param item       item数据
         * @param isSelected 选中状态
         */
        void onItemSelectedChange(T item, boolean isSelected);
    }

    enum SelectMode {
        SINGLE,
        SINGLE_MUST_ONE,
        MULTIPLE,
        MULTIPLE_MUST_ONE,
        ;

        public boolean isSingleType() {
            return this == SINGLE || this == SINGLE_MUST_ONE;
        }
    }

}
