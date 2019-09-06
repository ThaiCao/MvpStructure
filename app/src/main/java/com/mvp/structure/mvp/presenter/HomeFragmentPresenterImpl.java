package com.mvp.structure.mvp.presenter;

import com.mvp.structure.base.BaseMvpPresenter;
import com.mvp.structure.mvp.contract.HomeFragmentContract;
import com.mvp.structure.mvp.model.HomeFragmentModelImpl;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class HomeFragmentPresenterImpl extends BaseMvpPresenter<HomeFragmentModelImpl, HomeFragmentContract.View> implements HomeFragmentContract.Presenter{
    public HomeFragmentPresenterImpl(HomeFragmentContract.View view) {
        super(view);
        model = new HomeFragmentModelImpl();
    }



    @Override
    public void start() {

    }

}
