package com.zhang.library.library_adapter.viewholder;

import android.view.ViewGroup;

import com.zhang.library.adapter.viewholder.EmptyViewHolder;
import com.zhang.library.library_adapter.R;
import com.zhang.library.library_adapter.model.Model_A;

import androidx.annotation.NonNull;

/**
 * @author ZhangXiaoMing 2021-09-30 17:49 星期四
 */
public class EmptyHolder extends EmptyViewHolder<Model_A> {

    public EmptyHolder(@NonNull ViewGroup parent) {
        super(parent, R.layout.view_empty);
    }
}
