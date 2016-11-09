package com.emoiluj.doubleviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;

public class DoubleViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private ArrayList<PagerAdapter> mAdapters;

    VerticalViewPager.OnPageChangeListener onPageChangeListener;
    public DoubleViewPagerAdapter(Context context, ArrayList<PagerAdapter> verticalAdapters,VerticalViewPager.OnPageChangeListener pageChangeListener){
        mContext = context;
        mAdapters = verticalAdapters;
        onPageChangeListener = pageChangeListener;
    }


    @Override
    public int getCount() {
        return mAdapters.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        VerticalViewPager childVP = new VerticalViewPager(mContext);

        childVP.setPageTransformer(true,new ZoomOutTransformer());
        childVP.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        childVP.setAdapter(mAdapters.get(position));
        childVP.setOnPageChangeListener(onPageChangeListener);
        container.addView(childVP);
        return childVP;
    }

}