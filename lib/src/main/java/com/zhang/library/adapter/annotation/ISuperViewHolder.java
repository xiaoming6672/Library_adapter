package com.zhang.library.adapter.annotation;

import com.zhang.library.adapter.viewholder.base.SuperViewHolder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.LayoutRes;

/**
 * {@link SuperViewHolder}子类使用的layoutId注解
 *
 * @author ZhangXiaoMing 2020年9月25日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ISuperViewHolder {

    /** 布局id */
    @LayoutRes
    int layoutId();
}