package com.android.mb.movie.base;

/**
 * @Description
 * @Created by cgy on 2017/7/19
 * @Version v1.0
 */

public interface Presenter<V extends BaseMvpView>  {

    /**
     * presenter和对应的view绑定
     * @param mvpView  目标view
     */
    void attachView(V mvpView);

    /**
     * presenter与view解绑
     */
    void detachView();
}
