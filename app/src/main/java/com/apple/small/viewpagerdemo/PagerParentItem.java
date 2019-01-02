package com.apple.small.viewpagerdemo;

import android.content.Context;
import android.view.View;

public class PagerParentItem {
    public View mRootView;
    public Context mContext;

    public PagerParentItem(Context context) {
        mContext = context;
        getView();
    }
    public int getView(){

        return 0;
    }
    public void updateUI(MainActivity.PageItemBean bean){}

}