package com.android.mb.movie.base;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @Description
 * @Created by cgy on 2017/7/19
 * @Version v1.0
 *
 */

public class BaseMvpPresenter<V extends BaseMvpView> implements Presenter<V>{

    protected V mMvpView;

    protected CompositeSubscription mCompositeSubscription;

    /**
     * 自定义异常
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException(){
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }


    @Override
    public void attachView(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        onUnsubscribe();
    }

    /**
     * 判断 view是否为空
     * @return
     * @deprecated
     */
    public  boolean isAttachView(){
        return mMvpView != null;
    }

    /**
     * 返回目标view
     * @return
     * @deprecated
     */
    public  V getMvpView(){
        return mMvpView;
    }

    /**
     * 检查view和presenter是否连接
     * @deprecated  attachView 在BaseMvpActivity自动调用，可以确保不为空。
     */
    public void checkViewAttach(){
        if(!isAttachView()){
            throw new MvpViewNotAttachedException();
        }
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();

            //解绑完不置null第二次绑定会有问题。
            mCompositeSubscription = null;
        }
    }

    protected <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s));
    }
}
