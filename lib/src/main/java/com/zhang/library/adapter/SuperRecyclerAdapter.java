package com.zhang.library.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.zhang.library.adapter.annotation.ISuperViewHolder;
import com.zhang.library.adapter.model.ISuperRecyclerModel;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.adapter.viewholder.base.SuperViewHolder;
import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ContextUtils;

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
public class SuperRecyclerAdapter<T>/*<T extends ISuperRecyclerModel>*/
        extends BaseRecyclerAdapter<T> {

    private final Map<Integer, Class<?>> mViewHolderMap = new HashMap<>();
    private final Map<Class<?>, Integer> mViewLayoutIdMap = new HashMap<>();

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return VIEW_TYPE_HEADER_BASE - position;
        }

        if (isFooterPosition(position)) {
            int realPosition = position - getHeaderCount() - getDataCount();
            return VIEW_TYPE_FOOTER_BASE + realPosition;
        }

        if (isDataEmpty())
            return VIEW_TYPE_EMPTY_DATA;

        T data = getDataHolder().getData(getRealPosition(position));
        int identityHashCode = System.identityHashCode(data.getClass());
        LogUtils.debug(TAG, "getItemViewType()>>>data.class=%s , identityHashCode=%d", data.getClass().getName(), identityHashCode);

        if (data instanceof ISuperRecyclerModel) {
            Integer viewType = ((ISuperRecyclerModel) data).getItemViewType();
            return viewType != null ? viewType : identityHashCode;
        }

        return identityHashCode;
    }

    @Override
    protected BaseRecyclerViewHolder onCreateVHolder(@NonNull ViewGroup parent, int viewType) {
        Class<?> clazz = mViewHolderMap.get(viewType);
        if (clazz == null) {
            throw new IllegalArgumentException("No ViewHolder found! viewType=" + viewType);
        }

        return getViewHolder(parent, (Class<T>) clazz);
    }


    @Override
    protected void onBindData(BaseRecyclerViewHolder<T> viewHolder, T data, int position) {
        SuperViewHolder<T> holder = (SuperViewHolder<T>) viewHolder;
        holder.bindData(data, position);
    }


    /**
     * 注册item数据的ViewHolder
     *
     * @param clazz ViewHolder
     */
    public <VH extends SuperViewHolder> void registerViewHolder(final Class<VH> clazz) {
        registerViewHolder(clazz, null);
    }

    /**
     * 注册item数据的ViewHolder
     *
     * @param clazz    ViewHolder
     * @param viewType viewType
     */
    public <VH extends SuperViewHolder> void registerViewHolder(final Class<VH> clazz, final Integer viewType) {
        if (clazz == null) {
            return;
        }

        ISuperViewHolder annotation = getAnnotation(clazz);
        int layoutId = annotation.layoutId();
        if (layoutId == 0)
            throw new IllegalArgumentException(ISuperViewHolder.class.getSimpleName() + "'s layoutId == 0");

        Type type = getGenericType(clazz);
        if (type == null) {
            type = Object.class;
//            return;
        }

        int itemViewType = viewType != null ? viewType : System.identityHashCode(type);
        mViewHolderMap.put(itemViewType, clazz);
        mViewLayoutIdMap.put(clazz, layoutId);

        LogUtils.debug(TAG, "registerViewHolder()>>>type=%s , itemViewType=%d , clazz=%s , layoutId=%s",
                type.toString(),
                itemViewType,
                clazz,
                ContextUtils.get().getResources().getResourceEntryName(layoutId));
    }

    private static Type getGenericType(Class<?> clazz) {
        ParameterizedType parameterizedType;
        try {
            parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        } catch (Exception e) {
            e.printStackTrace();
            parameterizedType = null;
        }
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

    private static <VH extends SuperViewHolder> ISuperViewHolder getAnnotation(Class<VH> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("clazz is null");

        if (clazz == SuperViewHolder.class)
            throw new IllegalArgumentException("clazz must not be " + SuperViewHolder.class.getName());

        while (true) {
            ISuperViewHolder annotation = clazz.getAnnotation(ISuperViewHolder.class);
            if (annotation != null)
                return annotation;

            if (clazz == SuperViewHolder.class)
                break;

            clazz = (Class<VH>) clazz.getSuperclass();
        }

        throw new IllegalArgumentException(ISuperViewHolder.class.getSimpleName() + " annotation was not found in " + clazz.getName());
    }


    private SuperViewHolder<T> getViewHolder(ViewGroup parent, Class<T> clazz) {
        Integer layoutId = mViewLayoutIdMap.get(clazz);
        if (layoutId == null || layoutId == 0) {
            return null;
        }

        Constructor<?> constructor = null;

        try {
            constructor = clazz.getConstructor(ViewGroup.class, int.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

            Log.e(TAG, "getViewHolder()>>>e = " + e.getLocalizedMessage());
        }

        if (constructor != null) {
            try {
                @SuppressWarnings("unchecked")
                SuperViewHolder<T> holder = (SuperViewHolder<T>) constructor.newInstance(parent, layoutId);
                return holder;
            } catch (Exception e) {
                e.printStackTrace();

                Log.e(TAG, "getViewHolder()>>>e = " + e.getLocalizedMessage());
            }
        }

        try {
            constructor = clazz.getConstructor(View.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();

            Log.e(TAG, "getViewHolder()>>>e = " + e.getLocalizedMessage());
        }

        if (constructor == null)
            return null;


        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        try {
            @SuppressWarnings("unchecked")
            SuperViewHolder<T> holder = (SuperViewHolder<T>) constructor.newInstance(view);
            return holder;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
