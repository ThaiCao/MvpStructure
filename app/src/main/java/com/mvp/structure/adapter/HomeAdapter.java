package com.mvp.structure.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mvp.structure.R;
import com.mvp.structure.base.BaseListAdapter;
import com.mvp.structure.base.IViewHolder;
import com.mvp.structure.base.ViewHolderImpl;
import com.mvp.structure.widgets.CircleTransform;
import com.mvp.structure.widgets.interfaces.OnRecyclerViewItemClickListener;
import com.mvp.structure.widgets.styledcontrol.StyledTextView;

public class HomeAdapter extends BaseListAdapter<String> {

    private OnRecyclerViewItemClickListener listener;

    public HomeAdapter(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected IViewHolder<String> createViewHolder(int viewType) {
        return new HotHolder();
    }

    @Override
    protected void onItemClick(View v, int pos) {
        super.onItemClick(v, pos);
        listener.onItemClick(pos, v);
    }

    class HotHolder extends ViewHolderImpl<String> {
        private StyledTextView tvName;
        private ImageView ivHome;

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_home;
        }

        @Override
        public void initView() {
            tvName = findById(R.id.tvName);
            ivHome = findById(R.id.ivHome);
        }

        @Override
        public void onBind(String data, int pos) {
            if(data !=null){
                tvName.setText(data);
                RequestOptions requestOptions =new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transform(new CircleTransform(getContext()))
                        .dontAnimate()
                        .error(R.drawable.ic_loading);
                Glide.with(getContext()).load(getDrawableByPosition(pos)).apply(requestOptions).into(ivHome);
            }
        }

        private int getDrawableByPosition(int position){
            int drawable = R.drawable.ic_home_1;
            switch (position){
                case 0:
                    drawable = R.drawable.ic_home_1;
                    break;
                case 1:
                    drawable = R.drawable.ic_home_2;
                    break;
                case 2:
                    drawable = R.drawable.ic_home_3;
                    break;
                case 3:
                    drawable = R.drawable.ic_home_4;
                    break;
                case 4:
                    drawable = R.drawable.ic_home_5;
                    break;
                case 5:
                    drawable = R.drawable.ic_home_6;
                    break;
                case 6:
                    drawable = R.drawable.ic_home_7;
                    break;
                case 7:
                    drawable = R.drawable.ic_home_8;
                    break;
            }
            return drawable;
        }
    }
}
