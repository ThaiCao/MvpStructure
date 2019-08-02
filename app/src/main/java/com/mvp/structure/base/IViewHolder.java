package com.mvp.structure.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thai.cao on 7/30/2019.
 */
public interface IViewHolder<M> {
    View createItemView(ViewGroup parent);
    void initView();
    void onBind(M data, int pos);
    void onClick();
}
