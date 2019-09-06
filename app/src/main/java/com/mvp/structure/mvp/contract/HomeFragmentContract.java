package com.mvp.structure.mvp.contract;

import com.mvp.structure.base.BaseContract;

/**
 * Created by thai.cao on 7/31/2019.
 */
public interface HomeFragmentContract extends BaseContract {
    interface Model extends BaseModel{
    }
    interface View extends BaseView{
    }
    interface Presenter extends BasePresenter<View>{
    }
}
