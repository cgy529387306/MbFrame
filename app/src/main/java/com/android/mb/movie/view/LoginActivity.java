package com.android.mb.movie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.presenter.HomePresenter;
import com.android.mb.movie.view.interfaces.IHomeView;

/**
 * Created by Administrator on 2018\8\20 0020.
 */

public class LoginActivity extends BaseMvpActivity<HomePresenter,IHomeView> implements IHomeView,View.OnClickListener{

    private TextView mTvTest;

    @Override
    protected void loadIntent() {
        int test = getIntent().getIntExtra("test",1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        setTitleText("登录");
    }

    @Override
    protected void bindViews() {
        mTvTest = findViewById(R.id.tv_test);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getHotList();
    }

    @Override
    protected void setListener() {
        mTvTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getHotList() {

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }
}
