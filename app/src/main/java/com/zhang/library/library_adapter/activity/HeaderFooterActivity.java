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
import com.zhang.library.adapter.viewholder.FooterViewHolder;
import com.zhang.library.adapter.viewholder.HeaderViewHolder;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.library_adapter.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeaderFooterActivity extends AppCompatActivity {


    private RecyclerView rvContent;
    private Adapter mAdapter;

    private EmptyViewHolder<String> mEmptyHolder;
    private List<HeaderViewHolder<String>> mHeaderList;
    private List<FooterViewHolder<String>> mFooterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_header_footer);

        mAdapter = new Adapter();
        rvContent = findViewById(R.id.rv_content);
        rvContent.setAdapter(mAdapter);

        mHeaderList = new ArrayList<>();
        mFooterList = new ArrayList<>();

        findViewById(R.id.btn_set_list).performClick();
    }

    //<editor-fold desc="数据操作">
    public void onClickSetData(View view) {
        int size = new Random().nextInt(5) + 1;
        List<String> list = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            list.add("设置数据：" + index);
        }

        mAdapter.getDataHolder().setDataList(list);
    }

    public void onClickClearData(View view) {
        mAdapter.getDataHolder().clearData();
    }
    //</editor-fold>

    //<editor-fold desc="EmptyHolder操作">
    public void onClickSetEmptyHolder(View view) {
        if (mEmptyHolder == null) {
            //初始化EmptyViewHolder
            mEmptyHolder = new EmptyViewHolder<>(findViewById(R.id.rv_content), R.layout.view_common_rv_empty);
        }

        mAdapter.setEmptyViewHolder(mEmptyHolder);
    }

    public void onClickRemoveEmptyHolder(View view) {
        mAdapter.removeEmptyViewHolder();
    }
    //</editor-fold>

    //<editor-fold desc="Header操作">
    public void onClickAddHeader(View view) {
        Header header = new Header(rvContent);
        mAdapter.addHeader(header);
        mHeaderList.add(header);
    }

    public void onClickRemoveHeader(View view) {
        if (mHeaderList.isEmpty())
            return;

        HeaderViewHolder<String> header = mHeaderList.remove(mHeaderList.size() - 1);
        mAdapter.removeHeader(header);
    }

    public void onClickClearHeader(View view) {
        mAdapter.clearHeader();
        mHeaderList.clear();
    }
    //</editor-fold>

    //<editor-fold desc="Footer操作">
    public void onClickAddFooter(View view) {
        Footer footer = new Footer(rvContent);
        mAdapter.addFooter(footer);
        mFooterList.add(footer);
    }

    public void onClickRemoveFooter(View view) {
        if (mFooterList.isEmpty())
            return;

        FooterViewHolder<String> footer = mFooterList.remove(mFooterList.size() - 1);
        mAdapter.removeFooter(footer);
    }

    public void onClickClearFooter(View view) {
        mAdapter.clearFooter();
        mFooterList.clear();
    }
    //</editor-fold>


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
    }


    private static class ViewHolder extends BaseRecyclerViewHolder<String> {

        TextView tvText;

        public ViewHolder(@NonNull ViewGroup parent) {
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
         * @param item     数据
         * @param position 位置
         */
        @Override
        public void onBindData(String item, int position) {
            tvText.setText(item);
        }
    }

    private static class Header extends HeaderViewHolder<String> {

        TextView tvHeaderText;

        public Header(ViewGroup parent) {
            super(parent, R.layout.item_common_header);
        }

        @Override
        public void onInit() {
            tvHeaderText = findViewById(R.id.tv_header_text);
        }

        @Override
        public void onBindData(String item, int position) {
//            if (!TextUtils.isEmpty(tvHeaderText.getText()))
//                return;

            String content = String.valueOf(new Random().nextInt(100));
            tvHeaderText.setText(content);
        }
    }

    private static class Footer extends FooterViewHolder<String> {

        TextView tvFooterText;

        public Footer(ViewGroup parent) {
            super(parent, R.layout.item_common_footer);
        }

        @Override
        public void onInit() {
            tvFooterText = findViewById(R.id.tv_footer_text);
        }

        @Override
        public void onBindData(String item, int position) {
//            if (!TextUtils.isEmpty(tvFooterText.getText()))
//                return;

            String content = String.valueOf(new Random().nextInt(100));
            tvFooterText.setText(content);
        }
    }

}

