package com.apple.small.viewpagerdemo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewPagerItem {

    private Context mContext;
    public View mRootView;
    private ImageView mIvBg;
    private ImageView mIvNum;
    private TextView mTvTip;

    public MyViewPagerItem(Context context) {
        mContext = context;
        mRootView = View.inflate(context, R.layout.view_pager_item, null);
        mIvBg = mRootView.findViewById(R.id.viewpager_bg);
        mIvNum = mRootView.findViewById(R.id.viewpager_num);
        mTvTip = (TextView) mRootView.findViewById(R.id.viewpager_tip);
    }

    public void updateUI(MainActivity.PageItemBean bean) {
        if (bean == null) {
            return;
        }
        int num = bean.num;
        String tip = bean.tip;
        String bgImgIdStr = "bg" + num;
        String numImgIdStr = "num_" + num;
        int bgImgId = mContext.getResources().getIdentifier(bgImgIdStr, "drawable", mContext.getPackageName());
        int numImgId = mContext.getResources().getIdentifier(numImgIdStr, "drawable", mContext.getPackageName());
        mIvBg.setImageResource(bgImgId);
        mIvNum.setImageResource(numImgId);
//        mTvTip.setText(mContext.getString(R.string.num_tip, num+""));
        mTvTip.setText(tip);
    }
}
