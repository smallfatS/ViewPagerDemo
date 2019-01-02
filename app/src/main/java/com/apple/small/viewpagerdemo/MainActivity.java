package com.apple.small.viewpagerdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{
    private Context mContext;
    private View mRootView;
    private ViewPager mViewPager;
    private TextView mTvNotify;
    private MyViewPager mPagerAdapter = new MyViewPager();
    private MyTransPageTransformer pageTransformer = new MyTransPageTransformer();
    private ArrayList<PageItemBean> mItemList = new ArrayList<PageItemBean>(7);
    private HashMap<Integer, MyViewPagerItem> mPageItemList = new HashMap<>();

    private boolean isNeedNotify;
    private int mCurPageIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        iniView();
        iniData();
    }

    private void iniView() {
        mRootView = View.inflate(this, R.layout.view_pager_layout, null);
        setContentView(mRootView);
        mViewPager = mRootView.findViewById(R.id.vp_layout);
        mTvNotify = mRootView.findViewById(R.id.tv_notify);
        mViewPager.setPageTransformer(true, pageTransformer);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
    }

    private void iniData() {
        iniItemListData();
        mTvNotify.setOnClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(4);
    }

    private void iniItemListData() {
        for (int i=1; i<=7; i++) {
            PageItemBean bean = new PageItemBean();
            bean.num = i;
            bean.tip = mContext.getString(R.string.num_tip, i+"");
            mItemList.add(bean);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurPageIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_notify) {
            if (mCurPageIndex == 3) {//测试，代表逻辑需跳转到的页卡
                isNeedNotify = true;
            }
            //更新当前页卡数据
            updateViewPager(mCurPageIndex);
        }
    }

    private void updateViewPager(int position) {
        if (isNeedNotify) {//需要滚动时，调用notifyDataSetChanged重走instantiateItem和destroyItem逻辑
            mPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(3);
            isNeedNotify = false;
        }

        //仅需更新当前页时则单独刷新当前页卡view
        MyViewPagerItem item = mPageItemList.get(position);
        if (item != null) {
            PageItemBean bean = new PageItemBean();
            bean.num = position+1;
            bean.tip = mContext.getString(R.string.num_tip, position + 11 + "");
            item.updateUI(bean);
        }

    }

    public class PageItemBean {
        public int num;
        public String tip;
    }

    private class MyViewPager extends PagerAdapter {

        @Override
        public int getCount() {

            int count = mItemList == null ? 0 : mItemList.size();
            Log.i("ssytest", "count:"+count);
            return count;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            Log.i("ssytest", "isViewFromObject");
            MyViewPagerItem item = (MyViewPagerItem) object;
            return view == item.mRootView;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.i("ssytest", "instantiateItem");
            MyViewPagerItem item = new MyViewPagerItem(mContext);
            item.updateUI(mItemList.get(position));
            item.mRootView.setTag(position);
            mPageItemList.put(position, item);
            container.addView(item.mRootView);
            return item;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            Log.i("ssytest", "destroyItem");
            MyViewPagerItem item = (MyViewPagerItem) object;
            container.removeView(item.mRootView);
        }

        @Override
        public int getItemPosition(Object object) {
            MyViewPagerItem v = (MyViewPagerItem) object;
            if (v == null || v.mRootView == null){
                return POSITION_UNCHANGED;
            }
            int position = (int) v.mRootView.getTag();
            Log.i("test", "position: "+(mCurPageIndex == position ? "POSITION_NONE" : "POSITION_UNCHANGED"));
            return mCurPageIndex == position ? POSITION_NONE : POSITION_UNCHANGED;
        }
    }
}
