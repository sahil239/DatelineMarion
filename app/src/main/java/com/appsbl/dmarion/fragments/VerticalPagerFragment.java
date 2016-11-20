package com.appsbl.dmarion.fragments;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbl.dmarion.Constants;
import com.appsbl.dmarion.MainScreen;
import com.appsbl.dmarion.R;
import com.appsbl.dmarion.VerticalPagerAdapter;
import com.appsbl.dmarion.model.NewsModel;
import com.bumptech.glide.Glide;
import com.emoiluj.doubleviewpager.VerticalViewPager;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;

import pro.alexzaitsev.freepager.library.view.core.VerticalPager;
import pro.alexzaitsev.freepager.library.view.infinite.InfiniteVerticalPager;


/**
 * Created by HP on 12-11-2016.
 */

public class VerticalPagerFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";


    public static VerticalViewPager doubleViewVertical;

    public static InfiniteVerticalPager infiniteVerticalPager;

    public static final VerticalPagerFragment newInstance(String message)

    {

        VerticalPagerFragment f = new VerticalPagerFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString(EXTRA_MESSAGE, message);

        f.setArguments(bdl);

        return f;

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        String message = getArguments().getString(EXTRA_MESSAGE);

        View view = inflater.inflate(R.layout.verticalpagerlayout, container, false);

        fetchDataFromSqlite(Constants.newsTable, "");


        doubleViewVertical = (VerticalViewPager) view.findViewById(R.id.doubleViewVertical);

        doubleViewVertical.setAdapter(new VerticalPagerAdapter(getActivity()));

        infiniteVerticalPager = (InfiniteVerticalPager) view.findViewById(R.id.infiniteVerticalPager);

        LayoutTransition layoutTransition = new LayoutTransition();
        infiniteVerticalPager.setVerticalFadingEdgeEnabled(true);

        doubleViewVertical.setOnPageChangeListener(new VerticalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int newPageIndex) {

                MainScreen.snackbar.dismiss();
                if (newPageIndex == 0) {
                    MainScreen.refreshortop.setImageResource(R.drawable.refress);
                } else {
                    MainScreen.refreshortop.setImageResource(R.drawable.top);
                }

                //   CustomWebFragment.webView.loadUrl(Constants.newsArrayList.get(newPageIndex).getData().getDetail_description_url());

                NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(newPageIndex).getData();
                boolean found = false;
                for (int j = 0; j < MainScreen.bookmarksList.size(); j++) {

                    String id = MainScreen.bookmarksList.get(j);
                    if (dataBean.getArticle_id().equals(id)) {
                        found = true;
                        ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                                setImageResource(R.drawable.bookmark_tick);
                        break;

                    }


                }

                if (!found) {
                    ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                            setImageResource(R.drawable.bookmark);

                }

                Toast.makeText(getActivity(), "" + newPageIndex, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


      /*  infiniteVerticalPager.addOnPageChangedListener(new VerticalPager.OnVerticalPageChangeListener() {
            @Override
            public void onVerticalPageChanged(int newPageIndex) {
                Toast.makeText(getActivity(),""+newPageIndex,Toast.LENGTH_SHORT).show();
            }
        });*/
        // doubleViewVertical.setPageTransformer();
        for (int i = 0; i < Constants.newsArrayList.size(); i++) {

            infiniteVerticalPager.addView(setInfiniteViewPager(i));

            //  doubleViewVertical.addView(setInfiniteViewPager(i));

            NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(i).getData();

            if (i == 0) {
                for (int j = 0; j < MainScreen.bookmarksList.size(); j++) {

                    String id = MainScreen.bookmarksList.get(j);
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
        infiniteVerticalPager.addOnPageChangedListener(new VerticalPager.OnVerticalPageChangeListener() {
            @Override
            public void onVerticalPageChanged(int newPageIndex) {

                if (MainScreen.snackbar.isShown()) {

                    MainScreen.snackbar.dismiss();
                    MainScreen.toolbar.animate().translationY(-MainScreen.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
                }
                if (newPageIndex == 0) {
                    MainScreen.refreshortop.setImageResource(R.drawable.refress);
                } else {
                    MainScreen.refreshortop.setImageResource(R.drawable.top);
                }

                CustomWebFragment.webView.loadUrl(Constants.newsArrayList.get(newPageIndex).getData().getDetail_description_url());

                NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(newPageIndex).getData();
                boolean found = false;
                for (int j = 0; j < MainScreen.bookmarksList.size(); j++) {

                    String id = MainScreen.bookmarksList.get(j);
                    if (dataBean.getArticle_id().equals(id)) {
                        found = true;
                        ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                                setImageResource(R.drawable.bookmark_tick);
                        break;

                    }


                }

                if (!found) {
                    ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                            setImageResource(R.drawable.bookmark);

                }


            }
        });


        return view;

    }


    public View setInfiniteViewPager(int position) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpagetitem, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                getActivity().getResources().getDisplayMetrics().widthPixels * 75 / 100);
        layoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainScreen.snackbar.isShown()) {
                    MainScreen.snackbar.dismiss();
                    MainScreen.toolbar.animate().translationY(-MainScreen.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
                } else {
                    MainScreen.snackbar.show();
                    MainScreen.toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }
        });
        final NewsModel.GeneralNewsBean generalNewsBean = Constants.newsArrayList.get(position);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView category = (TextView) view.findViewById(R.id.category);
        TextView pubDate = (TextView) view.findViewById(R.id.pubDate);
        TextView readMore = (TextView) view.findViewById(R.id.readMore);

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

    File SDCardRoot = Environment.getExternalStorageDirectory();
    File folder = new File(SDCardRoot + Constants.dataFilePath);

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


    void fetchDataFromSqlite(String tableName, String category_name) {

        SQLiteDatabase database = getActivity().openOrCreateDatabase(Constants.databaseName, getActivity().MODE_PRIVATE, null);

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

                    dataBean.setArticle_id(cursor.getString(cursor.getColumnIndex("article_id")));
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


                //   MainScreen.viewpager.setAdapter(MainScreen.doubleViewPagerAdapter);
                MainScreen.viewpager.setCurrentItem(1);
                Toast.makeText(getActivity(), "List is Populated..." + Constants.newsArrayList.size(), Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getActivity(), "List is Empty...", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
