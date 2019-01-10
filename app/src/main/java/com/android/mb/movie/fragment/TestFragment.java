package com.android.mb.movie.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.TestAdapter;
import com.android.mb.movie.base.BaseFragment;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.widget.MyDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cgy on 16/7/18.
 */
public class TestFragment extends BaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener {


    private int mState;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TestAdapter mAdapter;
    private int mCurrentPage = 1;


    public static Fragment getInstance(int state) {
        Fragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.common_recycleview;
    }

    @Override
    protected void bindViews(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        mRecyclerView =  view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new TestAdapter(R.layout.item_test,new ArrayList());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void processLogic() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mState = bundle.getInt("state");
        }
        mSwipeRefreshLayout.setRefreshing(true);
        getListFormServer();
    }

    @Override
    protected void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mAdapter.getItem(position)!=null){
            ToastHelper.showLongToast(mAdapter.getItem(position));
        }
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        getListFormServer();
    }


    @Override
    public void onLoadMoreRequested() {
        mCurrentPage++;
        getListFormServer();
    }

    @Override
    public void onClick(View v) {

    }


    private void getListFormServer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> result = new ArrayList<>();
                if(mCurrentPage==1){
                    mAdapter.getData().clear();
                }
                int i = mAdapter.getItemCount();
                int j = mAdapter.getItemCount()+ProjectConstants.PAGE_SIZE;
                for(;i<j;i++){
                    result.add("页面 "+mState+",第"+i+"项目");
                }
                if (mCurrentPage == 1){
                    //首页
                    mSwipeRefreshLayout.setRefreshing(false);
                    mAdapter.setNewData(result);
                    mAdapter.setEmptyView(R.layout.empty_data, (ViewGroup) mRecyclerView.getParent());
                    if (result.size()< ProjectConstants.PAGE_SIZE){
                        mAdapter.loadMoreEnd();
                    }
                }else{
                    if (Helper.isEmpty(result)){
                        mAdapter.loadMoreEnd();
                    }else{
                        mAdapter.addData(result);
                        mAdapter.loadMoreComplete();
                    }
                }
            }
        },1500);
    }
}
