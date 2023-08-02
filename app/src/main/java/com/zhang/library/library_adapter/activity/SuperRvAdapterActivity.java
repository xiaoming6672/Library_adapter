package com.zhang.library.library_adapter.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.adapter.SuperRecyclerAdapter;
import com.zhang.library.adapter.annotation.ISuperViewHolder;
import com.zhang.library.adapter.viewholder.base.SuperViewHolder;
import com.zhang.library.library_adapter.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("NonConstantResourceId")
public class SuperRvAdapterActivity extends AppCompatActivity {


    private Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_super_rv_adapter);

        mAdapter = new Adapter();

        RecyclerView rvContent = findViewById(R.id.rv_content);
        rvContent.setAdapter(mAdapter);
    }

    public void onClickSetData(View view) {
        List<Number> list = new ArrayList<>();
        Random random = new Random();

        int intSize = random.nextInt(3) + 1;
        for (int i = 0; i < intSize; i++) {
            list.add(random.nextInt(100));
        }

        int floatSize = random.nextInt(3) + 1;
        for (int i = 0; i < floatSize; i++) {
            list.add(random.nextInt(100) / 10F);
        }

        int doubleSize = random.nextInt(3) + 1;
        for (int i = 0; i < doubleSize; i++) {
            list.add(random.nextInt(100) / 2D);
        }

        Collections.shuffle(list);
        mAdapter.getDataHolder().setDataList(list);
    }

    public void onClickRandomRemoveData(View view) {
        int size = mAdapter.getDataHolder().size();
        int position = new Random().nextInt(size);
        mAdapter.getDataHolder().removeData(position);
    }


    private static class Adapter extends SuperRecyclerAdapter<Number> {

        public Adapter() {
            registerViewHolder(IntegerViewHolder.class);
            registerViewHolder(FloatViewHolder.class);
            registerViewHolder(DoubleViewHolder.class);
        }
    }


    private static class NumberViewHolder<T extends Number> extends SuperViewHolder<T> {

        TextView tvNumberContent;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public NumberViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
            super(parent, layoutId);
        }

        /** 初始化 */
        @Override
        public void onInit() {
            tvNumberContent = findViewById(R.id.tv_number_content);
        }

        /**
         * 填充数据
         *
         * @param item     数据
         * @param position 位置
         */
        @Override
        public void onBindData(T item, int position) {
            tvNumberContent.setText(item.toString());
        }
    }

    @ISuperViewHolder(layoutId = R.layout.item_number_integer)
    private static class IntegerViewHolder extends NumberViewHolder<Integer> {

        public IntegerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public IntegerViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }
    }

    @ISuperViewHolder(layoutId = R.layout.item_number_float)
    private static class FloatViewHolder extends NumberViewHolder<Float> {

        public FloatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public FloatViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }
    }

    @ISuperViewHolder(layoutId = R.layout.item_number_double)
    private static class DoubleViewHolder extends NumberViewHolder<Double> {

        public DoubleViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public DoubleViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }
    }

}
