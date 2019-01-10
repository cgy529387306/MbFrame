package com.android.mb.movie.presenter;

import com.android.mb.movie.base.BaseMvpPresenter;
import com.android.mb.movie.presenter.interfaces.IHomePresenter;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.view.interfaces.IHomeView;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cgy on 2018/2/11 0011.
 */

public class HomePresenter extends BaseMvpPresenter<IHomeView> implements IHomePresenter {


    @Override
    public void getHotList() {
        Observable observable = ScheduleMethods.getInstance().getHotList();
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mMvpView!=null){
//                    if(e instanceof HttpException){
//                        try {
//                            String response = ((HttpException)e).response().errorBody().string();
//                            BaseError error = GsonUtil.GsonToBean(response, BaseError.class);
//                            mMvpView.onError(error.getMessage());
//                        } catch (Exception e1) {
//                            e1.printStackTrace();
//                        }
//                    }else if (e instanceof ApiException){
//                        mMvpView.onError(e.getMessage());
//                    }
                }
            }

            @Override
            public void onNext(Object list) {
                if (mMvpView!=null){

                }
            }
        });
    }
}
