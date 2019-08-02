package com.mvp.structure.base;

/**
 * Created by thai.cao on 2019/07/30
 */

public interface BaseContract {

    interface BaseModel {
//        void onResponse(T response);
//        void onError(ResponseError error);
    }

    interface BasePresenter<T extends BaseView> {

        void destroy();

        void attachView(T view);

        void detachView();

        void start();
    }

    interface BaseView {

        void showLoading();

        void hideLoading();

        void showError(String errorMessage);
    }
}
