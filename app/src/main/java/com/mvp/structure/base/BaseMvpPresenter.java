package com.mvp.structure.base;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by thai.cao on 7/30/2019.
 */
public abstract class BaseMvpPresenter<M extends BaseMvpModel, V extends BaseContract.BaseView> implements BaseContract.BasePresenter<V> {
    private WeakReference<V> viewRef;
    protected CompositeDisposable mDisposable;
    protected M model;

    public BaseMvpPresenter(V view){
        attachView(view);
        mDisposable = new CompositeDisposable();
    }
    public M getInstance(Class<M> theClass)
            throws IllegalAccessException, InstantiationException {

        return theClass.newInstance();
    }

    @UiThread
    @Override
    public void destroy() {
        if (isViewAttached()) {
            detachView();
        }
    }

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
//        this.mView = view;
    }


    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    public final boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }

        if(mDisposable !=null){
            mDisposable.clear();
            mDisposable.dispose();
        }
    }
}
