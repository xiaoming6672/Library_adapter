package com.zhang.library.library_adapter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhang.library.library_adapter.R;
import com.zhang.library.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.setDebug(true);

        setContentView(R.layout.activity_main);
    }

    public void onClickBaseRvBtn(View view) {
        startActivity(new Intent(this, BaseRvAdapterActivity.class));
    }

    public void onClickHeaderFooterBtn(View view) {
        startActivity(new Intent(this, HeaderFooterActivity.class));
    }

    public void onClickSuperRvBtn(View view) {
        startActivity(new Intent(this, SuperRvAdapterActivity.class));
    }

}