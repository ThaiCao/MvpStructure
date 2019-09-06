package com.mvp.structure.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mvp.structure.bus.BusProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by thai.cao on 2019/07/30
 */

public abstract class BaseFragment extends Fragment {
    private View root = null;
    private Unbinder unbinder;
    protected boolean firstLoad = true;

    @LayoutRes
    protected abstract int getLayoutId();

    /*******************************init area*********************************/

    protected abstract void initTitle();

    protected abstract void initData(Bundle savedInstanceState);

    /**
     * init click event
     */
    protected abstract void initClick();

    /**
     * handle logic
     */
    protected abstract void processLogic();

    /**
     * init widget
     */
    protected abstract void initWidget(Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /******************************lifecycle area*****************************************/



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(firstLoad) {
            int resId = getLayoutId();
            root = inflater.inflate(resId,container,false);
        }
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(firstLoad) {
            initData(savedInstanceState);
            unbinder = ButterKnife.bind(this,root);
            initWidget(savedInstanceState);
            initClick();
            processLogic();
            firstLoad = false;
        }
        initTitle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(unbinder!= null)
            unbinder.unbind();

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
    public void onDestroyView() {
        super.onDestroyView();
//        CommonUtils.hideKeyboard(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**************************pulic function*******************************************/
    public String getName(){
        return getClass().getName();
    }

    protected <VT> VT getViewById(int id){
        if (root == null){
            return  null;
        }
        return (VT) root.findViewById(id);
    }

    public void pushFragment(BaseFragment fragment, boolean addBackStack) {
        pushFragment(fragment, addBackStack, true);
    }

    public void pushFragment(BaseFragment fragment, boolean addBackStack, boolean animation) {
        if(getActivity()!=null && getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).pushFragment(fragment, false, addBackStack, animation);
        }
    }

    public void pushFragment(BaseFragment fragment, boolean clearStack, boolean addBackStack, boolean animation) {
        if(getActivity()!=null && getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).pushFragment(fragment, clearStack, addBackStack, animation);
        }
    }

    public void showDialogMessage(String title, String message){
        if(getActivity()!=null && getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).showDialogMessage(title, message);
        }

    }

    public void setTitle(String title){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).setTitle(title);
        }
    }

    public void displayBackButtonToolbar(boolean isDisplay){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).displayBackButtonToolbar(isDisplay);
        }
    }

}
