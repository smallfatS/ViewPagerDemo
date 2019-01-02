package com.apple.small.viewpagerdemo;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class MyTransPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_Trans_INDEX = 0.2f;
    private static final float MIN_SCALE_INDEX = 0.9f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        if (position < -1.1) { // [-Infinity,-1.1)
            // This page is way off-screen to the left.
            page.setAlpha(0);
        } else if (position <= 0) { // [-1.1,0]
            // Use the default slide transition when moving to the left page
            page.setAlpha(1);
            page.setTranslationX(-pageWidth * position * MIN_Trans_INDEX);
            page.setScaleX((1 - MIN_SCALE_INDEX) * position + 1);
            page.setScaleY((1 - MIN_SCALE_INDEX) * position + 1);
            Log.i("test", page.getTag()+"--"+((1 - MIN_SCALE_INDEX) * position + 1));
        } else if (position <= 1.1) { // (0,1.1]
            // Fade the page out.
            page.setAlpha(1);
            // Counteract the default slide transition
            page.setTranslationX(-pageWidth * position * MIN_Trans_INDEX);
            page.setScaleX(1 - (1 - MIN_SCALE_INDEX) * position);
            page.setScaleY(1 - (1 - MIN_SCALE_INDEX) * position);
        } else { // (1.1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);
        }
    }
}
