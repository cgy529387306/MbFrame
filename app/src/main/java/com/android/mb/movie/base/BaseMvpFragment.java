package com.android.mb.movie.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.mb.movie.utils.ToastHelper;


/**
 * <pre>
 *     author: cgy
 *     time  : 2017/7/20
 *     desc  :
 * </pre>
 *
 */

public abstract class BaseMvpFragment<P extends Presenter<V>,V extends BaseMvpView>
        extends BaseFragment implements BaseMvpView{


    protected P mPresenter;

//    private CustomProgressDialog mProgressDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void back() {

    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgressDialog() {
//        if(mProgressDialog == null) {
//            mProgressDialog = new CustomProgressDialog(mContext);
//            mProgressDialog.setCancelable(false);
//        }else {
//            mProgressDialog.show();
//        }
//        //为进度条添加一个事件.
//        mProgressDialog.setOnKeyListener(onKeyListener);
    }

    @Override
    public void dismissProgressDialog() {
//        if (mContext.isFinishing()) {
//            return;
//        }
//        if (null != mProgressDialog && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
    }

    /**
     * add a keylistener for progress dialog
     */
    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissProgressDialog();
            }
            return false;
        }
    };

}
