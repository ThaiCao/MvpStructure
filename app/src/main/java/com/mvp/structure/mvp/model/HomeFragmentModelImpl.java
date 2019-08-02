package com.mvp.structure.mvp.model;

import com.mvp.structure.base.BaseMvpModel;
import com.mvp.structure.data.api.RequestClient;
import com.mvp.structure.data.bean.response.UserResponse;
import com.mvp.structure.mvp.contract.HomeFragmentContract;

import io.reactivex.Observable;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class HomeFragmentModelImpl extends BaseMvpModel implements HomeFragmentContract.Model {
    @Override
    public Observable<UserResponse> executeGetUserGitHub(String query) {
        return RequestClient.getSharedInstance().getUsers(query);
    }

}
