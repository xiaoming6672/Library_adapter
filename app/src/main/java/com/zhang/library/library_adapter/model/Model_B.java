package com.zhang.library.library_adapter.model;

import com.zhang.library.adapter.model.ISuperRecyclerModel;

/**
 * @author ZhangXiaoMing 2020-08-12 22:22 星期三
 */
public class Model_B implements ISuperRecyclerModel {
    public String value;

    /** 返回itemView的类型 */
    @Override
    public Integer getItemViewType() {
        return 2;
    }
}
