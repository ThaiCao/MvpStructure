package com.mvp.structure.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.mvp.structure.R;
import com.mvp.structure.base.BaseContract;
import com.mvp.structure.base.BaseMvpActivity;
import com.mvp.structure.mvp.contract.MainActivityContract;
import com.mvp.structure.mvp.presenter.MainActivityPresenterImpl;
import com.mvp.structure.ui.fragment.HomeFragment;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<BaseContract.BasePresenter> implements MainActivityContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
//setup toolbar display menu icon on left conner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
//                Log.e("TEST_TOOLBAR","getBackStackEntryCount: " + getSupportFragmentManager().getBackStackEntryCount());
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    //show hamburger
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });
    }

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int layoutContainerId() {
        return R.id.container;
    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected void initClick() {

    }

    @Override
    protected void initToolbar() {
        setUpToolbar(toolbar);
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void displayBackButtonToolbar(boolean isDisplay) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplay);
        getSupportActionBar().setDisplayShowHomeEnabled(isDisplay);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initFragment();
        }
    }

    @Override
    protected BaseContract.BasePresenter bindPresenter() {
        return new MainActivityPresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void initFragment() {
        pushFragment(new HomeFragment(),true, true, true);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        }else{
            finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}
