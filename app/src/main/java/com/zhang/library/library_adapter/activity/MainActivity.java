package com.zhang.library.library_adapter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.zhang.library.library_adapter.R;
import com.zhang.library.library_adapter.adapter.TestAdapter;
import com.zhang.library.library_adapter.model.Model_A;
import com.zhang.library.library_adapter.model.Model_B;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv_content = findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(this));


        TestAdapter adapter = new TestAdapter();
        rv_content.setAdapter(adapter);

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                Model_A a = new Model_A();
                a.value = i;

                list.add(a);
            } else {
                Model_B b = new Model_B();
                b.value = String.valueOf(System.currentTimeMillis());

                list.add(b);
            }
        }

        adapter.getDataHolder().setDataList(list);
    }
}