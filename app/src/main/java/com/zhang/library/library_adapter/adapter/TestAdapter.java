package com.zhang.library.library_adapter.adapter;

import com.zhang.library.adapter.SuperRecyclerAdapter;
import com.zhang.library.library_adapter.adapter.viewholder.ViewHolder_A;
import com.zhang.library.library_adapter.adapter.viewholder.ViewHolder_B;

/**
 * @author ZhangXiaoMing 2020-08-12 22:18 星期三
 */
public class TestAdapter extends SuperRecyclerAdapter<Object> {

    public TestAdapter() {
        initViewHolder();
    }

    private void initViewHolder() {
        registerViewHolder(ViewHolder_A.class);
        registerViewHolder(ViewHolder_B.class);
    }
}
