package com.zhang.library.library_adapter.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.adapter.annotation.ISuperViewHolder;
import com.zhang.library.adapter.viewholder.base.SuperViewHolder;
import com.zhang.library.library_adapter.R;
import com.zhang.library.library_adapter.model.Model_B;

import androidx.annotation.NonNull;

/**
 * @author ZhangXiaoMing 2020-08-12 22:22 星期三
 */
@ISuperViewHolder(layoutId = R.layout.item_view_b)
public class ViewHolder_B extends SuperViewHolder<Model_B> {

    private TextView tv_text;

    public ViewHolder_B(@NonNull View itemView) {
        super(itemView);
    }

    public ViewHolder_B(ViewGroup parent, int layoutId) {
        super(parent, layoutId);
    }

    @Override
    public void onBindData(Model_B item, int position) {
        tv_text.setText(item.value);
    }

    @Override
    public void onInit() {
        tv_text = findViewById(R.id.tv_text);
    }
}
