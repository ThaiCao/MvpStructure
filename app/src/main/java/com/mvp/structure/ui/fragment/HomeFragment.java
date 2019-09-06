package com.mvp.structure.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mvp.structure.R;
import com.mvp.structure.adapter.HomeAdapter;
import com.mvp.structure.base.BaseMVPFragment;
import com.mvp.structure.mvp.contract.HomeFragmentContract;
import com.mvp.structure.mvp.presenter.HomeFragmentPresenterImpl;
import com.mvp.structure.widgets.interfaces.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class HomeFragment extends BaseMVPFragment<HomeFragmentContract.Presenter> implements HomeFragmentContract.View, OnRecyclerViewItemClickListener {

    @BindView(R.id.rvHome)
    RecyclerView rvHome;

    private HomeAdapter adapter;

    private List<String> homes = new ArrayList<>();

    @Override
    protected HomeFragmentContract.Presenter bindPresenter() {
        return new HomeFragmentPresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitle() {
        setTitle(getString(R.string.app_name));
        displayBackButtonToolbar(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        homes = new ArrayList<>();
        homes.add(getString(R.string.app_name));
        homes.add(getString(R.string.app_name));
        homes.add(getString(R.string.app_name));
        homes.add(getString(R.string.app_name));
        homes.add(getString(R.string.app_name));
        homes.add(getString(R.string.app_name));
    }

    @Override
    protected void initClick() {

    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        adapter = new HomeAdapter(this);
        adapter.addItems(homes);
        final GridLayoutManager layoutManager = new GridLayoutManager(getView().getContext(),
                2);
        rvHome.setLayoutManager(layoutManager);
//        rvHome.addItemDecoration(new RecyclerViewDividerItemDecoration(getActivity()));

        rvHome.setAdapter(adapter);
    }

    @Override
    protected void processLogic() {
        super.processLogic();

    }

    @Override
    public void onItemClick(int position, View view) {

    }
}
