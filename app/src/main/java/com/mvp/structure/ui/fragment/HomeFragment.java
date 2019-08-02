package com.mvp.structure.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mvp.structure.R;
import com.mvp.structure.adapter.UsersAdapter;
import com.mvp.structure.base.BaseMVPFragment;
import com.mvp.structure.data.bean.response.UserResponse;
import com.mvp.structure.data.bean.response.base.ResponseError;
import com.mvp.structure.mvp.contract.HomeFragmentContract;
import com.mvp.structure.mvp.presenter.HomeFragmentPresenterImpl;
import com.mvp.structure.widgets.RecyclerViewDividerItemDecoration;

import butterknife.BindView;

/**
 * Created by thai.cao on 7/31/2019.
 */
public class HomeFragment extends BaseMVPFragment<HomeFragmentContract.Presenter> implements HomeFragmentContract.View {

    @BindView(R.id.rcvUser)
    RecyclerView rcvUser;

    private UsersAdapter adapter;

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
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initClick() {

    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("Home");
        displayBackButtonToolbar(true);
        adapter = new UsersAdapter();
//            Log.e("TEST_FRAGMENT","HotFragment initview");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvUser.setLayoutManager(layoutManager);
        rcvUser.addItemDecoration(new RecyclerViewDividerItemDecoration(getActivity()));

        rcvUser.setAdapter(adapter);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.getUserGitHub("language");
    }

    @Override
    public void onGetUserGitHubSuccess(UserResponse data) {
        adapter.addItems(data.getUsers());
    }

    @Override
    public void onGetUserGitHubError(ResponseError error) {
    }
}
