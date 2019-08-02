package com.mvp.structure.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mvp.structure.bus.BusProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by thai.cao on 2019/07/30
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    protected AlertDialog dialogMessage;


    protected abstract void setUpToolbar(Toolbar toolbar);
    /**
     * layout id in res
     * @return
     */
    protected abstract int activityLayoutId();

    /**
     * return layoutContainer parent, it will contain fragment
     * @return
     */
    protected abstract int layoutContainerId();

    /**
     * init view as textsize, text color, bind data... after loaddview in onCreate
     */
    protected abstract void initWidget();

    /**
     * init click event
     */
    protected abstract void initClick();

    /**
     * init toolbar
     */
    protected abstract void initToolbar();

    /**
     * set Title
     * @param title
     */
    public abstract void setTitle(String title);

    public abstract void displayBackButtonToolbar(boolean isDisplay);

    /**
     * handle logic and data
     */
    protected abstract void processLogic();

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | // In onCreate
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setElevation(0);
        setContentView(activityLayoutId());
        unbinder = ButterKnife.bind(this);
        initToolbar();
        initData(savedInstanceState);
        initWidget();
        initClick();
        processLogic();
    }

    public void pushFragment(BaseFragment fragment,boolean clearStack, boolean addToStack, boolean animation){
        if(clearStack){
            clearAllFragment();
        }
        if(fragment != null){
            replaceFragment(fragment,addToStack,animation);
        }
    }

    public void replaceFragment(BaseFragment fragment, boolean addToStack, boolean animation ){
        int idContainer = layoutContainerId();
        if(idContainer <=0){
            return;
        }

        FragmentManager fm = fragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(animation){
            // animation
//            ft.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_top_out);
        }
        ft.replace(idContainer,fragment, fragment.getClass().getName());
        if(addToStack){
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * clear all fragment
     */
    protected void clearAllFragment(){
        FragmentManager fragmentManager = fragmentManager();
        while (fragmentManager.getBackStackEntryCount() >0){
            fragmentManager.popBackStackImmediate();
        }
    }

    /**
     * get Fragment manager
     * @return
     */
    private FragmentManager fragmentManager(){
        return  getSupportFragmentManager();
    }

    /**
     * show dialog message
     * @param title
     * @param message
     */
    public void showDialogMessage(String title, String message){
        if (isDialogMessageShowing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialogMessage = null;
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogMessage = null;
            }
        });
        dialogMessage = builder.show();
    }

    /**
     * get current fragment
     * @return
     */
    protected Fragment getCurrentFragment(){
        int layoutContainer = layoutContainerId();
        if(layoutContainer <=0){
            return null;
        }
        FragmentManager fm =fragmentManager();
        Fragment fragment = fm.findFragmentById(layoutContainer);
        return  fragment;
    }

    /**
     * check dialog message is showing
     * @return
     */
    public boolean isDialogMessageShowing(){
        if(dialogMessage!=null && dialogMessage.isShowing())
            return true;
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.bus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
