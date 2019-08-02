package com.mvp.structure.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by thai.cao on 7/30/2019.
 */
public class BaseViewHolder <T> extends RecyclerView.ViewHolder{
    public IViewHolder<T> holder;

    public BaseViewHolder(View itemView, IViewHolder<T> holder) {
        super(itemView);
        this.holder = holder;
        holder.initView();
    }
}
