package com.mvp.structure.mvp.presenter;

import com.mvp.structure.base.BaseMvpPresenter;
import com.mvp.structure.mvp.contract.MainActivityContract;
import com.mvp.structure.mvp.model.MainActivityModelImpl;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class MainActivityPresenterImpl extends BaseMvpPresenter<MainActivityModelImpl, MainActivityContract.View> implements MainActivityContract.Presenter{
    public MainActivityPresenterImpl(MainActivityContract.View view) {
        super(view);
        model = new MainActivityModelImpl();
    }

    @Override
    public void start() {

    }
}
