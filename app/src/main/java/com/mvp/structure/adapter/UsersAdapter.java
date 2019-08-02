package com.mvp.structure.adapter;

import android.view.View;

import com.mvp.structure.R;
import com.mvp.structure.base.BaseListAdapter;
import com.mvp.structure.base.IViewHolder;
import com.mvp.structure.base.ViewHolderImpl;
import com.mvp.structure.data.bean.model.UserModel;
import com.mvp.structure.widgets.ImageLoadingView;
import com.mvp.structure.widgets.styledcontrol.StyledTextView;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class UsersAdapter extends BaseListAdapter<UserModel> {

    @Override
    protected IViewHolder<UserModel> createViewHolder(int viewType) {
        return new  UserHolder();
    }

    @Override
    protected void onItemClick(View v, int pos) {
        super.onItemClick(v, pos);
        notifyDataSetChanged();
    }

    class UserHolder extends ViewHolderImpl<UserModel> {

//        @BindView(R.id.tvUser)
        StyledTextView tvUser;
        ImageLoadingView ivUser;

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_user;
        }

        @Override
        public void initView() {
            tvUser = findById(R.id.tvUser);
            ivUser = findById(R.id.ivUser);
        }

        @Override
        public void onBind(UserModel data, int pos) {
            if(data !=null){
                ivUser.loadImage(getContext(), data.getAvatarUrl());
                tvUser.setText(data.getLogin());
            }
        }
    }

}

