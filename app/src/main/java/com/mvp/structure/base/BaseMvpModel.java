package com.mvp.structure.base;

import com.mvp.structure.data.bean.response.base.BaseResponse;

/**
 * Created by thai.cao on 2019/07/30
 */

public abstract class BaseMvpModel<T extends BaseResponse> implements BaseContract.BaseModel{
//    protected ObservableEmitter<T> observableEmitter;
//    @Override
//    public void onResponse(T response) {
//        observableEmitter.onNext(response);
//        observableEmitter.onComplete();
//    }
//
//    @Override
//    public void onError(ResponseError error) {
//        observableEmitter.onError(new Throwable(error.errorMessage));
//    }
}
