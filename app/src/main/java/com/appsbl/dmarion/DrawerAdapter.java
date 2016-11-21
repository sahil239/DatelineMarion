package com.appsbl.dmarion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbl.dmarion.model.LeftPanel;
import com.appsbl.dmarion.model.NewsModel;
import com.emoiluj.doubleviewpager.DoubleViewPagerAdapter;
import com.emoiluj.doubleviewpager.VerticalViewPager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.appsbl.dmarion.MainScreen.bookmarksList;

public class DrawerAdapter extends PagerAdapter {

    private Context mContext;
    private int mParent;
    private int mChilds;
    private JSONArray mColors;
    Activity mActivity;
    private boolean isDrawer;

    SharedPreferences sp;

    public DrawerAdapter(Activity activity, Context c, int parent, int childs, boolean drawer) {
        mContext = c;
        mParent = parent;
        mChilds = childs;
        mActivity = activity;
        isDrawer = drawer;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mChilds;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;


        sp = PreferenceManager.getDefaultSharedPreferences(mActivity);
        //  if (isDrawer) {
        view = layoutInflater.inflate(R.layout.viewpagetitemdrawer, null);

        LinearLayout linearCategory = (LinearLayout) view.findViewById(R.id.linearCategory);
        final ImageView all_news = (ImageView) view.findViewById(R.id.all_news);
        final ImageView bookmarks = (ImageView) view.findViewById(R.id.bookmarks);
        final ImageView unread = (ImageView) view.findViewById(R.id.unread);

        all_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarks.setImageResource(R.drawable.all_bookmark);
                all_news.setImageResource(R.drawable.all_news_tick);
                unread.setImageResource(R.drawable.all_read);
                fetchDataFromSqlite(Constants.newsTable, "");
            }
        });


        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchBookMarkIds();
                bookmarks.setImageResource(R.drawable.all_bookmark_tick);
                all_news.setImageResource(R.drawable.all_news);
                unread.setImageResource(R.drawable.all_read);
                fetchDataFromSqlite(Constants.bookmarksTable,"");
            }
        });

        unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarks.setImageResource(R.drawable.all_bookmark);
                all_news.setImageResource(R.drawable.all_news);
                unread.setImageResource(R.drawable.all_read_tick);
            }
        });

        try {
            Log.d("leftPanel", "" + sp.getString("leftPanel", ""));
            JSONObject obj = new JSONObject(sp.getString("leftPanel", ""));
            Gson gson = new Gson();
            LeftPanel leftPanel = gson.fromJson(obj.toString(), LeftPanel.class);
            List<LeftPanel.CategoryBean> categoryBeen = leftPanel.getCategory();

            for (int i = 0; i < categoryBeen.size(); i++) {

                LeftPanel.CategoryBean categoryBean = categoryBeen.get(i);

                if (!categoryBean.isIs_general_category()) {
                    LinearLayout linearLayoutMain = new LinearLayout(mActivity);
                    linearLayoutMain.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mActivity);
                    title.setText(categoryBean.getKey());
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    LinearLayout.LayoutParams layoutParamsTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParamsTitle.setMargins(10, 10, 10, 10);
                    title.setLayoutParams(layoutParamsTitle);

                    View line = new View(mActivity);
                    LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                    layoutParamsLine.setMargins(10, 0, 0, 10);
                    line.setLayoutParams(layoutParamsLine);
                    line.setBackgroundColor(Color.parseColor("#ebebeb"));
                    HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mActivity);
                    horizontalScrollView.setHorizontalScrollBarEnabled(false);
                    LinearLayout linearLayoutChild = new LinearLayout(mActivity);
                    linearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);

                    for (int k = 0; k < categoryBeen.get(i).getData().size(); k++) {


                        final TextView category = new TextView(mActivity);
                        category.setText(categoryBean.getData().get(k));
                        LinearLayout.LayoutParams lpCategory = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lpCategory.setMargins(5, 0, 5, 0);
                        category.setPadding(25, 15, 25, 15);
                        category.setTextSize(12);
                        category.setBackground(mActivity.getResources().getDrawable(R.drawable.rounded_coffee));
                        category.setLayoutParams(lpCategory);

                        category.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fetchDataFromSqlite(Constants.newsTable, category.getText().toString());
                                // Toast.makeText(mActivity, category.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        linearLayoutChild.addView(category);
                    }

                    horizontalScrollView.addView(linearLayoutChild);

                    View line1 = new View(mActivity);
                    LinearLayout.LayoutParams layoutParamsLine1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                    layoutParamsLine1.setMargins(0, 10, 0, 0);
                    line1.setLayoutParams(layoutParamsLine1);
                    line1.setBackgroundColor(Color.BLACK);

                    linearLayoutMain.addView(title);
                    linearLayoutMain.addView(line);
                    linearLayoutMain.addView(horizontalScrollView);
                    linearLayoutMain.addView(line1);

                    linearCategory.addView(linearLayoutMain);
                }


            }


        } catch (Exception e) {

            Log.d("cate!!>>", e.getMessage());
        }

        TextView nightmode = (TextView) view.findViewById(R.id.nightmode);
        nightmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppCompatDelegate.getDefaultNightMode()
                        == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                Intent intent = mActivity.getIntent();
                mActivity.finish();
                mActivity.startActivity(intent);

            }
        });


        container.addView(view);
        return view;
    }



    void fetchBookMarkIds(){

        bookmarksList.clear();
        SQLiteDatabase database = mActivity.openOrCreateDatabase(Constants.databaseName, mActivity.MODE_PRIVATE, null);

        bookmarksList.clear();
        if (database != null) {

            Cursor cursor1 = database.rawQuery("select * from '" + Constants.bookmarksTable + "'", null);

            if(cursor1.getCount() > 0){
                cursor1.moveToFirst();
                do{
                    Log.d("cursor>>", cursor1.getString(cursor1.getColumnIndex("article_id")));
                    bookmarksList.add(cursor1.getString(cursor1.getColumnIndex("article_id")));

                }while (cursor1.moveToNext());

            }
        }
    }


    private void generateVerticalAdapters(ArrayList<PagerAdapter> verticalAdapters) {
        for (int i=0; i<2; i++){
            if(i == 1){
                verticalAdapters.add(new VerticalPagerAdapter(mActivity));
            }/*else if(i == 2){
                verticalAdapters.add(new DrawerAdapter(MainScreen.this,this, 2, 1,false));
            }*/else{
                verticalAdapters.add(new DrawerAdapter(mActivity,mActivity, 0, 1,true));
            }

        }
    }

    void fetchDataFromSqlite(String tableName, String category_name) {

        SQLiteDatabase database = mActivity.openOrCreateDatabase(Constants.databaseName, mActivity.MODE_PRIVATE, null);

        Constants.newsArrayList.clear();
        if (database != null) {

            Cursor cursor;
            if (category_name.equals("")) {
                cursor = database.rawQuery("select * from '" + tableName + "'", null);
            } else {
                cursor = database.rawQuery("select * from '" + tableName + "'" + " WHERE category_name ='" + category_name + "'", null);
            }

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    //Getting data using column name

                    Log.d("cursor", cursor.getColumnNames()[0]);
                    NewsModel.GeneralNewsBean item = new NewsModel.GeneralNewsBean();
                    NewsModel.GeneralNewsBean.DataBean dataBean = new NewsModel.GeneralNewsBean.DataBean();

                    dataBean.setCategory_name(cursor.getString(cursor.getColumnIndex("category_name")));
                    dataBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    dataBean.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    dataBean.setDetail_description_url(cursor.getString(cursor.getColumnIndex("detail_description_url")));
                    dataBean.setImage_url(cursor.getString(cursor.getColumnIndex("image_url")));
                    dataBean.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
                    dataBean.setStartFromHere(cursor.getString(cursor.getColumnIndex("startFromHere")));
                    dataBean.setLocalImage(cursor.getString(cursor.getColumnIndex("localImage")));

                    item.setData(dataBean);

                    Constants.newsArrayList.add(item);

                } while (cursor.moveToNext());

                ArrayList<PagerAdapter> verticalAdapters = new ArrayList<PagerAdapter>();
                generateVerticalAdapters(verticalAdapters);

                MainScreen.doubleViewPagerAdapter = new com.appsbl.dmarion.adapter.DoubleViewPagerAdapter(mActivity, verticalAdapters);
                MainScreen.viewPager.setAdapter(MainScreen.doubleViewPagerAdapter);
                MainScreen.viewPager.setCurrentItem(1);

                Toast.makeText(mActivity, "List is Populated..." + Constants.newsArrayList.size(), Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(mActivity, "List is Empty...", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
