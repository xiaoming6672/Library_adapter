package com.zhang.library.library_adapter;

import android.app.Application;

import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ContextUtils;

/**
 * @author ZhangXiaoMing 2023-08-02 14:36 周三
 */
public class ApiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextUtils.set(this);
        LogUtils.init(true);
    }
}
