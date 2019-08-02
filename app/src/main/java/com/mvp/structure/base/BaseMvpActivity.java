package com.mvp.structure.base;

/**
 * Created by thai.cao on 7/30/2019.
 */
public abstract class BaseMvpActivity<T extends BaseContract.BasePresenter> extends BaseActivity implements BaseContract.BaseView {
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void processLogic() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter){
        mPresenter = presenter;
//        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

}
