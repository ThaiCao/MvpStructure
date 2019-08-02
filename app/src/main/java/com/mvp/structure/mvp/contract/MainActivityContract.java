package com.mvp.structure.mvp.contract;

import com.mvp.structure.base.BaseContract;

/**
 * Created by thai.cao on 7/31/2019.
 */
public interface MainActivityContract extends BaseContract {
    interface Model extends BaseContract.BaseModel{
//        Observable<CheckVersionResponse> executeCheckAppVersion();
    }
    interface View extends BaseContract.BaseView{
        void initFragment();
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
//        void checkAppVersion();
    }
}
