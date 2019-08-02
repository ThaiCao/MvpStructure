package com.mvp.structure.ui.activity;

import android.app.SearchManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mvp.structure.R;
import com.mvp.structure.base.BaseContract;
import com.mvp.structure.base.BaseMvpActivity;
import com.mvp.structure.mvp.contract.MainActivityContract;
import com.mvp.structure.mvp.presenter.MainActivityPresenterImpl;
import com.mvp.structure.ui.fragment.HomeFragment;
import com.mvp.structure.utils.StringUtils;
import com.mvp.structure.utils.Utilities;

import butterknife.BindView;

/**
 * Created by thai.cao on 8/2/2019.
 */
public class DrawerActivity extends BaseMvpActivity<BaseContract.BasePresenter> implements MainActivityContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.read_dl_slide)
    DrawerLayout mDlSlide;

    private SearchView searchView = null;
    private MenuItem searchMenuItem = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    protected BaseContract.BasePresenter bindPresenter() {
        return new MainActivityPresenterImpl(this);
    }

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
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initFragment();
        }
    }

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected int layoutContainerId() {
        return R.id.container;
    }

    @Override
    protected void initWidget() {
        //Sliding display DrawerLayout
        mDlSlide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //The back button works when the side is open
        mDlSlide.setFocusableInTouchMode(false);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (
                        this,
                        mDlSlide,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                )
        {
        };
        mDlSlide.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home:
                mDlSlide.openDrawer(GravityCompat.START);
                return true;
            // manage other entries if you have it ...
        }
        return true;
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
    public void initFragment() {
        pushFragment(new HomeFragment(),true, true, true);
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
    public void onBackPressed() {
        if (mDlSlide.isDrawerOpen(GravityCompat.START)) {
            mDlSlide.closeDrawer(GravityCompat.START);
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        }else{
            finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        searchMenuItem = menu.findItem(R.id.action_search);
        if (searchMenuItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    onSearchViewTextChange(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.clearFocus();
                    Utilities.hideKeyboard(searchView);
                    onSearchViewTextSubmit(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
            MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    onSearchViewActionExpand(item);
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    onSearchViewActionCollapse(item);
                    return true;
                }
            });
        }
        return true;
    }

    public void closeMenuSearch(){
        if (searchMenuItem != null) {
            searchMenuItem.collapseActionView();
        }
    }

    protected void onSearchViewTextChange(String text) {

    }

    protected void onSearchViewTextSubmit(String text) {
        if (text != null && !StringUtils.isEmpty(text)) {
            Fragment fragment = getCurrentFragment();
//            if (fragment != null) {
//                if (!(fragment instanceof SearchFragment)) {
//                    SearchFragment searchFragment = new SearchFragment();
//                    pushFragment(searchFragment, false, true, true);
//                } else {
//                    ((SearchFragment) fragment).onQuerySearch(text);
//                }
//            }
        }
    }


    protected void onSearchViewActionExpand(MenuItem item) {
    }

    protected void onSearchViewActionCollapse(MenuItem item) {
        Fragment fragment = getCurrentFragment();
//        if (fragment instanceof SearchFragment) {
////            onBackPressed();
//        }
    }


}
