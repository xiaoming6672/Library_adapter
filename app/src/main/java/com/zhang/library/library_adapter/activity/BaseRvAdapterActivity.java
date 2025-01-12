package com.zhang.library.library_adapter.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zhang.library.adapter.BaseRecyclerAdapter;
import com.zhang.library.adapter.viewholder.EmptyViewHolder;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.library_adapter.R;
import com.zhang.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseRvAdapterActivity extends AppCompatActivity {


    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_rv_adapter);

        RecyclerView rvContent = findViewById(R.id.rv_content);
        mAdapter = new Adapter();
        mAdapter.getCallbackHolder().addOnItemClickCallback((view, data, position) -> {
            LogUtils.debug("ZHANG", "data=%s", data);
            mAdapter.getDataHolder().updateData(position, data + "0");
        });
        rvContent.setAdapter(mAdapter);
    }


    public void onClickSetData(View view) {
        List<String> list = new ArrayList<>();

        Random random = new Random();
        int size = random.nextInt(10) + 5;
        for (int index = 0; index < size; index++) {
            list.add("设置数据：" + index);
        }

        mAdapter.getDataHolder().setDataList(list);
    }

    public void onClickClearData(View view) {
        mAdapter.getDataHolder().clearData();
    }

    public void onClickAddData(View view) {
        int value = new Random().nextInt(100);
        String data = "添加数据：" + value;
        mAdapter.getDataHolder().addData(data);
    }

    public void onClickRemoveData(View view) {
        int size = mAdapter.getDataHolder().size();
        mAdapter.getDataHolder().removeData(size - 1);
    }


    private static class Adapter extends BaseRecyclerAdapter<String> {

        /**
         * 创建ViewHolder
         *
         * @param parent   Item父控件
         * @param viewType view类型
         */
        @Override
        protected BaseRecyclerViewHolder<String> onCreateVHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            setEmptyViewHolder(new EmptyViewHolder<>(recyclerView, R.layout.view_common_rv_empty));
        }
    }

    private static class ViewHolder extends BaseRecyclerViewHolder<String> {

        TextView tvText;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_common_rv_list);
        }

        /** 初始化 */
        @Override
        public void onInit() {
            tvText = findViewById(R.id.tv_text);
        }

        /**
         * 填充数据
         *
         * @param item
         * @param position
         */
        @Override
        public void onBindData(String item, int position) {
            tvText.setText(item);
        }
    }
}
