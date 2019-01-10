package com.android.mb.movie.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.movie.R;
import com.android.mb.movie.rxbus.Events;
import com.android.mb.movie.rxbus.RxBus;

import rx.functions.Action1;


/**
 * <pre>
 *     author: cgy
 *     time  : 2017/7/21
 *     desc  : BaseFragment
 * </pre>
 *
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mContext;
    protected boolean mIsFirstVisible = true;
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();
        rootView = view;
        initView(view);
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }

    /**
     * 加载布局
     */
    protected abstract int getLayoutId();

    /**
     * 数据懒加载
     */
    protected void lazyLoad() {

    }
    /**
     * find控件
     *
     * @param view
     */
    protected abstract void bindViews(View view);

    /**
     * 处理数据
     */
    protected abstract void processLogic();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 初始化界面
     *
     * @param view
     */
    private void initView(View view) {
        bindViews(view);
        processLogic();
        setListener();
    }

    /**
     * 注册事件.
     * @param event
     * @param onNext
     */
    public void regiestEvent(int event,Action1<Events<?>> onNext){
        RxBus.init()
                .setEvent(event)
                .onNext(onNext)
                .create(this);
    }

    /**
     * 发送事件.
     * @param event
     * @param o
     */
    public void sendMsg(int event,Object o){
        RxBus.getInstance().send(event,o);
    }
}
