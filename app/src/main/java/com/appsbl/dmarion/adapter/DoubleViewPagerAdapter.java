package com.appsbl.dmarion.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.appsbl.dmarion.Constants;
import com.appsbl.dmarion.DepthPageTransformer;
import com.appsbl.dmarion.MainScreen;
import com.appsbl.dmarion.R;
import com.appsbl.dmarion.model.NewsModel;
import com.emoiluj.doubleviewpager.VerticalViewPager;

import java.util.ArrayList;

import static com.appsbl.dmarion.MainScreen.bookmarksList;

public class DoubleViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private ArrayList<PagerAdapter> mAdapters;

    public DoubleViewPagerAdapter(Context context, ArrayList<PagerAdapter> verticalAdapters){
        mContext = context;
        mAdapters = verticalAdapters;
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
        childVP.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        childVP.setAdapter(mAdapters.get(position));
       // childVP.setPageTransformer(true,new DepthPageTransformer());
        container.addView(childVP);
        childVP.setOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                MainScreen.count = position;
                if(position == 0){
                    MainScreen.refreshortop.setImageResource(R.drawable.refress);
                    MainScreen.refresh = true;
                }else{
                    MainScreen.refreshortop.setImageResource(R.drawable.top);
                    MainScreen.refresh = false;
                }

                NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(position).getData();

                if(bookmarksList.contains(dataBean.getArticle_id())){
                    ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                            setImageResource(R.drawable.bookmark_tick);
                }else{
                    ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                            setImageResource(R.drawable.bookmark);
                }
                Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      //  for (int i = 0; i < Constants.newsArrayList.size(); i++) {

     //  }
        return childVP;
    }


}