package com.mvp.structure.base;

import com.mvp.structure.utils.StringUtils;

/**
 * Created by thai.cao on 7/30/2019.
 */
public abstract class BaseMVPFragment <T extends BaseContract.BasePresenter> extends BaseFragment implements BaseContract.BaseView{

    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void processLogic(){
        mPresenter = bindPresenter();
//        if(mPresenter !=null){
//            mPresenter.attachView(this);
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter !=null){
            mPresenter.destroy();
        }
    }

    @Override
    public void showError(String errorMessage) {
        if(getActivity() !=null){
            StringUtils.displayMessage(getView(), errorMessage);
        }
    }
}
