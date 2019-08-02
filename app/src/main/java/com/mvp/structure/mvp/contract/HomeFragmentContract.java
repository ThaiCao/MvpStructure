package com.mvp.structure.mvp.contract;

import com.mvp.structure.base.BaseContract;
import com.mvp.structure.data.bean.response.UserResponse;
import com.mvp.structure.data.bean.response.base.ResponseError;

import io.reactivex.Observable;

/**
 * Created by thai.cao on 7/31/2019.
 */
public interface HomeFragmentContract extends BaseContract {
    interface Model extends BaseModel{
        Observable<UserResponse> executeGetUserGitHub(String query);
    }
    interface View extends BaseView{
        void onGetUserGitHubSuccess(UserResponse data);
        void onGetUserGitHubError(ResponseError error);
    }
    interface Presenter extends BasePresenter<View>{
        void getUserGitHub(String query);
    }
}
