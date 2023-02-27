package com.zhang.library.adapter.model;

import com.zhang.library.adapter.SuperRecyclerAdapter;

/**
 * {@link SuperRecyclerAdapter}适配器使用的包裹层数据对象
 *
 * @author ZhangXiaoMing 2023-02-20 17:22 周一
 */
public interface ISuperRecyclerModel {

    /** 返回itemView的类型 */
    Integer getItemViewType();
}
