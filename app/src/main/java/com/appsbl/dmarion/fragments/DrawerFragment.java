package com.appsbl.dmarion.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbl.dmarion.Constants;
import com.appsbl.dmarion.MainScreen;
import com.appsbl.dmarion.R;
import com.appsbl.dmarion.SettingsActivity;
import com.appsbl.dmarion.SplashActivity;
import com.appsbl.dmarion.VerticalPagerAdapter;
import com.appsbl.dmarion.model.LeftPanel;
import com.appsbl.dmarion.model.NewsModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.appsbl.dmarion.MainScreen.bookmarksList;

/**
 * Created by HP on 12-11-2016.
 */

public class DrawerFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;

    public static final DrawerFragment newInstance(String message)

    {

        DrawerFragment f = new DrawerFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString(EXTRA_MESSAGE, message);

        f.setArguments(bdl);

        return f;

    }


    public static ImageView all_news,bookmarks,unread;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        String message = getArguments().getString(EXTRA_MESSAGE);

        View view = inflater.inflate(R.layout.viewpagetitemdrawer, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        LinearLayout linearCategory = (LinearLayout)view.findViewById(R.id.linearCategory);
        all_news = (ImageView)view.findViewById(R.id.all_news);
        bookmarks = (ImageView)view.findViewById(R.id.bookmarks);
        unread = (ImageView)view.findViewById(R.id.unread);
         ImageView settings = (ImageView)view.findViewById(R.id.setting);
        final ImageView arrow = (ImageView)view.findViewById(R.id.arrow);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainScreen.viewpager.setCurrentItem(1);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        all_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarks.setImageResource(R.drawable.all_bookmark);
                all_news.setImageResource(R.drawable.all_news_tick);
                unread.setImageResource(R.drawable.all_read);
                fetchDataFromSqlite(Constants.newsTable,"");
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
                //   fetchDataFromSqlite(Constants.bookmarksTable,"");
            }
        });



        try {
            JSONObject obj = new JSONObject(sharedPreferences.getString("leftPanel",""));
            Gson gson = new Gson();
            LeftPanel leftPanel = gson.fromJson(obj.toString(), LeftPanel.class);
            List<LeftPanel.CategoryBean> categoryBeen = leftPanel.getCategory();

            for(int i = 0; i < categoryBeen.size();i++){

                LeftPanel.CategoryBean categoryBean = categoryBeen.get(i);

                if(!categoryBean.isIs_general_category()) {
                    LinearLayout linearLayoutMain = new LinearLayout(getActivity());
                    linearLayoutMain.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(getActivity());
                    title.setText(categoryBean.getKey());
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    LinearLayout.LayoutParams layoutParamsTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParamsTitle.setMargins(10, 10, 10, 10);
                    title.setLayoutParams(layoutParamsTitle);

                    View line = new View(getActivity());
                    LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                    layoutParamsLine.setMargins(10,0,0,10);
                    line.setLayoutParams(layoutParamsLine);
                    line.setBackgroundColor(Color.parseColor("#ebebeb"));
                    HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());
                    horizontalScrollView.setHorizontalScrollBarEnabled(false);
                    LinearLayout linearLayoutChild = new LinearLayout(getActivity());
                    linearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);

                    for (int k = 0; k < categoryBeen.get(i).getData().size(); k++) {


                        final TextView category = new TextView(getActivity());
                        category.setText(categoryBean.getData().get(k));
                        LinearLayout.LayoutParams lpCategory = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lpCategory.setMargins(5, 0, 5, 0);
                        category.setPadding(25,15,25,15);
                        category.setTextSize(12);
                        category.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_coffee));
                        category.setLayoutParams(lpCategory);

                        category.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fetchDataFromSqlite(Constants.newsTable,category.getText().toString());
                                // Toast.makeText(getActivity(), category.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        linearLayoutChild.addView(category);
                    }

                    horizontalScrollView.addView(linearLayoutChild);

                    View line1 = new View(getActivity());
                    LinearLayout.LayoutParams layoutParamsLine1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
                    layoutParamsLine1.setMargins(0,10,0,0);line1.setLayoutParams(layoutParamsLine1);
                    line1.setBackgroundColor(Color.BLACK);

                    linearLayoutMain.addView(title);
                    linearLayoutMain.addView(line);
                    linearLayoutMain.addView(horizontalScrollView);
                    linearLayoutMain.addView(line1);

                    linearCategory.addView(linearLayoutMain);
                }


            }


        }catch (Exception e){

            Log.d("cate!!>>",e.getMessage());
        }

        TextView nightmode = (TextView) view.findViewById(R.id.nightmode);
        nightmode.setVisibility(View.GONE);
        nightmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppCompatDelegate.getDefaultNightMode()
                        == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                Intent intent = getActivity().getIntent();
                getActivity().finish();
                getActivity().startActivity(intent);

            }
        });

    

        return view;

    }

    File SDCardRoot = Environment.getExternalStorageDirectory();
    File folder = new File(SDCardRoot+Constants.dataFilePath);

    public String loadJSONFromAsset(String path) {
        String json = null;
        try {

            InputStream is = new FileInputStream(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    void fetchBookMarkIds(){

        SQLiteDatabase database = getActivity().openOrCreateDatabase(Constants.databaseName, getActivity().MODE_PRIVATE, null);

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


    void fetchDataFromSqlite(String tableName,String category_name){

        SQLiteDatabase database = getActivity().openOrCreateDatabase(Constants.databaseName, getActivity().MODE_PRIVATE, null);

        Constants.newsArrayList.clear();
        if (database != null) {

            Cursor cursor;
            if(category_name.equals("")){
                cursor  = database.rawQuery("select * from '" + tableName + "'", null);
            }else{
                cursor  = database.rawQuery("select * from '" + tableName + "'"+" WHERE category_name ='" + category_name + "'", null);
            }

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    //Getting data using column name

                    Log.d("cursor",cursor.getColumnNames()[0]);
                    NewsModel.GeneralNewsBean item = new NewsModel.GeneralNewsBean();
                    NewsModel.GeneralNewsBean.DataBean dataBean = new NewsModel.GeneralNewsBean.DataBean();

                    dataBean.setCategory_name(cursor.getString(cursor.getColumnIndex("category_name")));
                    dataBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    dataBean.setArticle_id(cursor.getString(cursor.getColumnIndex("article_id")));
                    dataBean.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    dataBean.setDetail_description_url(cursor.getString(cursor.getColumnIndex("detail_description_url")));
                    dataBean.setImage_url(cursor.getString(cursor.getColumnIndex("image_url")));
                    dataBean.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
                    dataBean.setStartFromHere(cursor.getString(cursor.getColumnIndex("startFromHere")));
                    dataBean.setLocalImage(cursor.getString(cursor.getColumnIndex("localImage")));

                    item.setData(dataBean);

                    Constants.newsArrayList.add(item);

                } while (cursor.moveToNext());



                VerticalPagerFragment.infiniteViewPager.removeAllViews();

                for(int i = 0; i < Constants.newsArrayList.size();i++){

                    VerticalPagerFragment.infiniteViewPager.addView(setInfiniteViewPager(i));

                    NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(i).getData();

                    if(i == 0){

                        if(tableName.equals(Constants.bookmarksTable)){
                            ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                                    setImageResource(R.drawable.bookmark_tick);
                        }else{
                        for(int j = 0 ; j < bookmarksList.size(); j++) {

                            String id = bookmarksList.get(j);
                            if (dataBean.getArticle_id().equals(id)) {

                                ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                                        setImageResource(R.drawable.bookmark_tick);
                            } else {
                                ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                                        setImageResource(R.drawable.bookmark);
                            }

                        }
                        }
                    }

                }


                MainScreen.viewpager.setCurrentItem(1);
                Toast.makeText(getActivity(), "List is Populated..."+Constants.newsArrayList.size(), Toast.LENGTH_SHORT).show();

            } else {

                if (tableName.equals(Constants.bookmarksTable)){
                    Toast.makeText(getActivity(), "No bookmarks added.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "No news found.", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }


    public View setInfiniteViewPager(int position){

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.viewpagetitem,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                getActivity().getResources().getDisplayMetrics().widthPixels*75/100);
        layoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainScreen.snackbar.isShown()){
                    MainScreen.snackbar.dismiss();
                    MainScreen.toolbar.animate().translationY(-MainScreen.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
                }else{
                    MainScreen.snackbar.show();
                    MainScreen.toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }
        });
        final NewsModel.GeneralNewsBean generalNewsBean = Constants.newsArrayList.get(position);
        TextView description = (TextView)view.findViewById(R.id.description);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView category = (TextView)view.findViewById(R.id.category);
        TextView pubDate = (TextView)view.findViewById(R.id.pubDate);
        TextView readMore = (TextView)view.findViewById(R.id.readMore);

        readMore.setVisibility(View.GONE);

        title.setText(generalNewsBean.getData().getTitle());
        description.setText(generalNewsBean.getData().getDescription());
        category.setText(generalNewsBean.getData().getCategory_name());
        pubDate.setText(generalNewsBean.getData().getPubDate());
        title.setText(generalNewsBean.getData().getTitle());
        // readMore.setText(generalNewsBean.getData().getDetail_description_url());
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setStartAnimations(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setExitAnimations(getActivity(), R.anim.slide_in_left, R.anim.slide_out_right);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getActivity(), Uri.parse(generalNewsBean.getData().getDetail_description_url()));
            }
        });

        Glide.with(getActivity())
                .load(generalNewsBean.getData().getImage_url())
                .into(imageView);



        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
