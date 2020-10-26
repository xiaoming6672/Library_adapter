package com.zhang.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.annotation.ASuperViewHolder;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.adapter.viewholder.base.SuperViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 多功能RecyclerView的适配器，能适配各种数据类型的ViewHolder
 * <p>
 * 一种数据类型对应一个ViewHolder，只需在使用的时候调用registerViewHolder注册ViewHolder即可
 *
 * @author ZhangXiaoMing 2020-08-12 19:10 星期三
 */
public class SuperRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    private Map<Integer, Class<?>> mViewHolderMap = new HashMap<>();
    private Map<Class<?>, Integer> mViewLayoutIdMap = new HashMap<>();

    @Override
    public int getItemViewType(int position) {
        T data = getDataHolder().getData(position);

        return System.identityHashCode(data.getClass());
    }

    @Override
    protected BaseRecyclerViewHolder onCreateVHolder(ViewGroup parent, int viewType) {
        Class<?> clazz = mViewHolderMap.get(viewType);
        if (clazz == null) {
            throw new IllegalArgumentException("No ViewHolder found!");
        }

        return getViewHolder(parent, (Class<T>) clazz);
    }


    @Override
    protected void onBindData(BaseRecyclerViewHolder<T> viewHolder, T data, int position) {
        SuperViewHolder<T> holder = (SuperViewHolder<T>) viewHolder;
        holder.onBindData(data, position);
    }


    public <T extends SuperViewHolder> void registerViewHolder(Class<T> clazz) {
        if (clazz == null) {
            return;
        }

        ASuperViewHolder annotation = getAnnotation(clazz);
        int layoutId = annotation.layoutId();
        if (layoutId == 0)
            throw new IllegalArgumentException(ASuperViewHolder.class.getSimpleName() + "'s layoutId == 0");

        Type type = getGenericType(clazz);
        if (type == null) {
            return;
        }

        int viewType = System.identityHashCode(type);
        mViewHolderMap.put(viewType, clazz);
        mViewLayoutIdMap.put(clazz, layoutId);
    }

    private static Type getGenericType(Class<?> clazz) {
        final ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        if (parameterizedType == null) {
            return null;
        }

        final Type[] types = parameterizedType.getActualTypeArguments();
        if (types != null && types.length > 0) {
            return types[0];
        } else {
            return null;
        }
    }

    private static <E extends SuperViewHolder> ASuperViewHolder getAnnotation(Class<E> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        if (clazz == SuperViewHolder.class)
            throw new IllegalArgumentException("clazz must not be " + SuperViewHolder.class.getName());

        while (true) {
            ASuperViewHolder annotation = clazz.getAnnotation(ASuperViewHolder.class);
            if (annotation != null)
                return annotation;

            if (clazz == SuperViewHolder.class)
                break;

            clazz = (Class<E>) clazz.getSuperclass();
        }

        throw new IllegalArgumentException(ASuperViewHolder.class.getSimpleName() + " annotation was not found in " + clazz.getName());
    }


    private SuperViewHolder<T> getViewHolder(ViewGroup parent, Class<T> clazz) {
        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            constructor = null;
        }
        if (constructor == null) {
            return null;
        }

        Integer layoutId = mViewLayoutIdMap.get(clazz);
        if (layoutId == null || layoutId == 0) {
            return null;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        SuperViewHolder<T> holder = null;
        try {
            holder = (SuperViewHolder) constructor.newInstance(view);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return holder;

    }
}
