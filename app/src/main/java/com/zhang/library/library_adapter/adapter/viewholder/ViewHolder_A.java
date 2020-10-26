package com.zhang.library.library_adapter.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.zhang.library.adapter.annotation.ASuperViewHolder;
import com.zhang.library.adapter.viewholder.base.SuperViewHolder;
import com.zhang.library.library_adapter.R;
import com.zhang.library.library_adapter.model.Model_A;

import androidx.annotation.NonNull;

/**
 * @author ZhangXiaoMing 2020-08-12 22:22 星期三
 */
@ASuperViewHolder(layoutId = R.layout.item_view_a)
public class ViewHolder_A extends SuperViewHolder<Model_A> {

    private TextView tv_text;

    public ViewHolder_A(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBindData(Model_A item, int position) {
        tv_text.setText(String.valueOf(item.value));
    }

    @Override
    public void onInit() {
        tv_text = findViewById(R.id.tv_text);
    }
}
