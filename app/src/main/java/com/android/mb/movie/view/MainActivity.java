package com.android.mb.movie.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.MyFragmentPagerAdapter;
import com.android.mb.movie.base.BaseActivity;
import com.android.mb.movie.fragment.TestFragment;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.widget.FragmentViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private FragmentViewPager mFragmentViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        hideBackImage();
        setRightImage(R.mipmap.icon_search);
        setTitleText("首页");
    }

    @Override
    protected void bindViews() {
        mFragmentViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        initTabPager();
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
        ToastHelper.showToast("搜索");
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabPager(){
        mFragmentList = new ArrayList<>();
        for (int i=0;i<5;i++){
            mFragmentList.add(TestFragment.getInstance(i));
        }
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentList.size());
        mFragmentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        String[] tabTitles = new String[]{"首页","聊天","发现","用户"};
        int[] tabImages = new int[]{R.drawable.btn_tab_user,R.drawable.btn_tab_user,R.drawable.btn_tab_user,R.drawable.btn_tab_user};
        for (int i = 0; i < tabTitles.length; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitles[i]);
            ImageView imgTab =  view.findViewById(R.id.iv_tab);
            imgTab.setImageResource(tabImages[i]);
            tab.setCustomView(view);
            mTabLayout.addTab(tab);
        }
    }


}
