package com.zhang.library.adapter;

import com.zhang.library.adapter.callback.DataHolder;
import com.zhang.library.adapter.callback.SelectManager;
import com.zhang.library.adapter.holder.AdapterCallbackHolder;

/**
 * 自定义适配器接口
 *
 * @author ZhangXiaoMing 2020-06-19 15:39 星期五
 */
public interface Adapter<T> {

    /** 获得数据管理者 */
    DataHolder<T> getDataHolder();

    /** 获得回调管理者 */
    AdapterCallbackHolder<T> getCallbackHolder();

    /** 获得选择管理者 */
    SelectManager<T> getSelectManager();
}
