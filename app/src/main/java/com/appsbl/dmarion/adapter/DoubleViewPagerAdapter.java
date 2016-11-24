package com.appsbl.dmarion.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.appsbl.dmarion.Constants;
import com.appsbl.dmarion.MainScreen;
import com.appsbl.dmarion.R;
import com.appsbl.dmarion.model.NewsModel;
import com.emoiluj.doubleviewpager.VPager;
import com.emoiluj.doubleviewpager.VerticalViewPager;
import com.emoiluj.doubleviewpager.ZoomOutTransformer;

import java.util.ArrayList;

import static java.lang.Math.min;

public class DoubleViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private ArrayList<PagerAdapter> mAdapters;

    public static VPager childVP;

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
        childVP = new VPager(mContext);
        childVP.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        childVP.setAdapter(mAdapters.get(position));
       // childVP.setCurrentItem(2);


        childVP.setPageTransformer(true,new ZoomOutTransformer());

       // childVP.setPageTransformer(true,new DepthPageTransformer());
        container.addView(childVP);
        childVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                MainScreen.snackbar.dismiss();
                MainScreen.toolbar.animate().translationY(-MainScreen.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();

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

                /*if(bookmarksList.contains(dataBean.getArticle_id())){
                    ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                            setImageResource(R.drawable.bookmark_tick);
                }else{
                    ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                            setImageResource(R.drawable.bookmark);
                }*/

               // openOrCreateDatabase();
                if(fetchDataFromSqlite(Constants.bookmarksTable,dataBean.getArticle_id())){
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


    boolean fetchDataFromSqlite(String tableName, String category_name) {

        SQLiteDatabase database = mContext.openOrCreateDatabase(Constants.databaseName, mContext.MODE_PRIVATE, null);


        if (database != null) {

            Cursor cursor;
            if (category_name.equals("")) {
                cursor = database.rawQuery("select * from '" + tableName + "'", null);
            } else {
                cursor = database.rawQuery("select * from '" + tableName + "'" + " WHERE article_id ='" + category_name + "'", null);
            }

            if (cursor.getCount() > 0) {

                return true;

            } else {

                return false;

            }
        }

        return false;
    }


    void openOrCreateDatabase(){

    SQLiteDatabase  database = mContext.openOrCreateDatabase(Constants.databaseName, mContext.MODE_PRIVATE, null);
        database.execSQL("create table if not exists "+Constants.newsTable +" (category_name text" +
                ", title text" +
                ", description text" +
                ", image_url text" +
                ", article_id text" +
                ", detail_description_url text" +
                ", pubDate text" +
                ", localImage text" +
                ", startFromHere text)");

        database.execSQL("create table if not exists " + Constants.bookmarksTable + " (category_name text" +
                ", article_id text" +
                ", title text" +
                ", description text" +
                ", image_url text" +
                ", detail_description_url text" +
                ", pubDate text" +
                ", localImage text" +
                ", startFromHere text)");

    }


}