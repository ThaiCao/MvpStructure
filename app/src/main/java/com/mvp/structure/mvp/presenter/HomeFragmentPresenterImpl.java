package com.mvp.structure.mvp.presenter;

import com.mvp.structure.base.BaseMvpPresenter;
import com.mvp.structure.data.bean.response.base.ResponseError;
import com.mvp.structure.mvp.contract.HomeFragmentContract;
import com.mvp.structure.mvp.model.HomeFragmentModelImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void getUserGitHub(String query) {
//        service.getCoinList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CoinList>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(CoinList coinList) {
//                        allCurrencyList = new ArrayList<>(coinList.getData().values());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // Updates UI with data
//                        cPresenter.updateCoinList(allCurrencyList);
//                    }
//                });
//
        Disposable disposable = model.executeGetUserGitHub(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> getView().onGetUserGitHubSuccess(response),
                        throwable -> getView().onGetUserGitHubError(new ResponseError(throwable))
                );
        mDisposable.add(disposable);

    }
}
