package com.zhang.library.library_adapter.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.adapter.BaseRecyclerAdapter;
import com.zhang.library.adapter.viewholder.FooterViewHolder;
import com.zhang.library.adapter.viewholder.HeaderViewHolder;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.library_adapter.R;
import com.zhang.library.library_adapter.model.Model_A;
import com.zhang.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_content;
    private HeaderTestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.setDebug(true);

        setContentView(R.layout.activity_main);

        rv_content = findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HeaderTestAdapter();
        rv_content.setAdapter(adapter);

        Random random = new Random();

        List<Model_A> list = new ArrayList<>();
        for (int i = 0; i < random.nextInt(10) + 1; i++) {
            Model_A a = new Model_A();
            a.value = i;
            list.add(a);
        }
        adapter.getDataHolder().setDataList(list);
        LogUtils.debug(getClass().getSimpleName(), "data size = %d", list.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_header: {
                Random random = new Random();
                int headerCount = random.nextInt(5);
                LogUtils.info(getClass().getSimpleName(), "headerCount = " + headerCount);
                for (int i = 0; i < headerCount; i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_view_b, ((ViewGroup) rv_content.getParent()), false);
                    Header holder = new Header(view);
                    adapter.addHeader(holder);
                }
                break;
            }
            case R.id.btn_add_footer: {
                Random random = new Random();
                int headerCount = random.nextInt(5);
                LogUtils.debug(getClass().getSimpleName(), "footCount = " + headerCount);
                for (int i = 0; i < headerCount; i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_view_b, ((ViewGroup) rv_content.getParent()), false);
                    Footer holder = new Footer(view);
                    adapter.addFooter(holder);
                }
                break;
            }
            case R.id.btn_clear_header:
                adapter.clearHeader();
                break;
            case R.id.btn_clear_footer:
                adapter.clearFooter();
                break;
        }
    }

    static class Header extends HeaderViewHolder<Model_A> {

        TextView tv_text;

        Header(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onInit() {
            tv_text = findViewById(R.id.tv_text);
        }

        @Override
        public void onBindData(Model_A item, int position) {
            tv_text.setText("Header：" + position);
        }
    }

    static class Footer extends FooterViewHolder<Model_A> {
        TextView tv_text;

        Footer(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onInit() {
            tv_text = findViewById(R.id.tv_text);
        }

        @Override
        public void onBindData(Model_A item, int position) {
            tv_text.setText("Footer：" + position);
        }
    }

    static class HeaderTestAdapter extends BaseRecyclerAdapter<Model_A> {
        @Override
        protected BaseRecyclerViewHolder<Model_A> onCreateVHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent, R.layout.item_view_a);
        }

        @Override
        protected void onBindData(BaseRecyclerViewHolder<Model_A> viewHolder, Model_A data, int position) {
        }

        static class ViewHolder extends BaseRecyclerViewHolder<Model_A> {

            TextView tv_text;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public ViewHolder(ViewGroup parent, int layoutId) {
                super(parent, layoutId);
            }

            @Override
            public void onInit() {
                tv_text = findViewById(R.id.tv_text);
            }

            @Override
            public void onBindData(Model_A item, int position) {
                tv_text.setText("Data：" + position);
            }
        }
    }
}