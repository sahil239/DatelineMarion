package com.appsbl.dmarion;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.appsbl.dmarion.api.ApiClient;
import com.appsbl.dmarion.api.ApiInterface;
import com.appsbl.dmarion.fragments.CustomWebFragment;
import com.appsbl.dmarion.fragments.DrawerFragment;
import com.appsbl.dmarion.fragments.VerticalPagerFragment;
import com.appsbl.dmarion.model.NewsModel;
import com.bumptech.glide.Glide;
import com.emoiluj.doubleviewpager.DoubleViewPager;
import com.emoiluj.doubleviewpager.DoubleViewPagerAdapter;
import com.emoiluj.doubleviewpager.HorizontalViewPager;
import com.emoiluj.doubleviewpager.VerticalViewPager;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainScreen extends AppCompatActivity {

    public static ViewPager viewpager;
    public static DoubleViewPager viewPager;
    File SDCardRoot = Environment.getExternalStorageDirectory();
    File folder = new File(SDCardRoot + Constants.dataFilePath);
    public static Snackbar snackbar;
    RelativeLayout linear;
    public static Toolbar toolbar;

    public static ImageView refreshortop;
    ImageView opendrawer;

    public static ArrayList<String> bookmarksList = new ArrayList<>();
    public static com.appsbl.dmarion.adapter.DoubleViewPagerAdapter doubleViewPagerAdapter;

    public static boolean refresh = true;
    public static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        sp = PreferenceManager.getDefaultSharedPreferences(MainScreen.this);
        editor = sp.edit();

        viewPager = (DoubleViewPager)findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        refreshortop = (ImageView) findViewById(R.id.refreshortop);

        viewPager.setOffscreenPageLimit(2);
        opendrawer = (ImageView) findViewById(R.id.opendrawer);

        opendrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        refreshortop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (refresh) {
                    new GetNews().execute();
                } else {


                   // Toast.makeText(MainScreen.this,viewPager.getCurrentItem()+"",Toast.LENGTH_SHORT).show();

                  /*  VerticalViewPager verticalViewPager = (VerticalViewPager) viewPager.getChildAt(viewPager.getCurrentItem());
                    verticalViewPager.setCurrentItem(0,true);*/
                    //com.appsbl.dmarion.adapter.DoubleViewPagerAdapter.childVP.setCurrentItem(0,true);

                    refreshortop.setImageResource(R.drawable.refress);
                    ArrayList<PagerAdapter> verticalAdapters = new ArrayList<PagerAdapter>();
                    generateVerticalAdapters(verticalAdapters);

                    doubleViewPagerAdapter = new com.appsbl.dmarion.adapter.DoubleViewPagerAdapter(getApplicationContext(), verticalAdapters);

                    viewPager.setAdapter(doubleViewPagerAdapter);
                    viewPager.setCurrentItem(1);

                  //  com.appsbl.dmarion.adapter.DoubleViewPagerAdapter.childVP.setCurrentItem(0,true);

                }


            }
        });



        // setSupportActionBar(toolbar);

        // getSupportActionBar().setShowHideAnimationEnabled(false);

        openOrCreateDatabase();
        fetchBookMarkIds();
        fetchDataFromSqlite();
        loadUI();
    }

    void bottomSnackBar() {

        snackbar = Snackbar.make(linear, "", Snackbar.LENGTH_INDEFINITE);
// Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
// Hide the text

        layout.setBackgroundColor(Color.TRANSPARENT);
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

// Inflate our custom view
        View snackView = getLayoutInflater().inflate(R.layout.bottom_snackbar, null);


        layout.setPadding(0, 0, 0, 0); //set padding to 0

        layout.setBackgroundColor(Color.parseColor("#ffffff"));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        layout.setLayoutParams(layoutParams);
        snackView.setLayoutParams(layout.getLayoutParams());

        ((ImageView) snackView.findViewById(R.id.option_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                store(count);
            }
        });

        ((ImageView) snackView.findViewById(R.id.option_two)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(count).getData();
                Constants.shareOptions(MainScreen.this, dataBean.getDetail_description_url(), dataBean.getTitle());

            }
        });
        layout.addView(snackView, 0);
// Show the Snackbar
        snackbar.show();
    }

    SQLiteDatabase database;

    void openOrCreateDatabase(){

        database = openOrCreateDatabase(Constants.databaseName, MODE_PRIVATE, null);
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

    void fetchBookMarkIds(){

        bookmarksList.clear();
        if (database != null) {

            Cursor cursor1 = database.rawQuery("select * from '" + Constants.bookmarksTable + "'", null);

            if(cursor1.getCount() > 0){
                cursor1.moveToFirst();
                do{
                    Log.d("cursor>>fetchBook",""+ cursor1.getString(cursor1.getColumnIndex("article_id")));
                    bookmarksList.add(cursor1.getString(cursor1.getColumnIndex("article_id")));

                }while (cursor1.moveToNext());

            }
        }
    }

    void fetchDataFromSqlite() {
        Constants.newsArrayList.clear();
        if (database != null) {
            Cursor cursor = database.rawQuery("select * from '" + Constants.newsTable + "'", null);


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
                    dataBean.setArticle_id(cursor.getString(cursor.getColumnIndex("article_id")));
                    dataBean.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
                    dataBean.setStartFromHere(cursor.getString(cursor.getColumnIndex("startFromHere")));
                    dataBean.setLocalImage(cursor.getString(cursor.getColumnIndex("localImage")));

                    item.setData(dataBean);

                    Constants.newsArrayList.add(item);

                } while (cursor.moveToNext());


                Toast.makeText(getApplicationContext(), "List is Populated..." + Constants.newsArrayList.size(), Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "List is Empty...", Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:

                //  new BackGroundTask(Constants.newsUrl).execute();
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected

            default:
                break;
        }

        return true;
    }

    private void loadUI() {

        linear = (RelativeLayout) findViewById(R.id.linear);
        bottomSnackBar();
        // topSnackbar();


        viewpager = (ViewPager) findViewById(R.id.pager);

        ArrayList<PagerAdapter> verticalAdapters = new ArrayList<PagerAdapter>();
        generateVerticalAdapters(verticalAdapters);



        doubleViewPagerAdapter = new com.appsbl.dmarion.adapter.DoubleViewPagerAdapter(getApplicationContext(), verticalAdapters);

        viewPager.setAdapter(doubleViewPagerAdapter);
        viewPager.setCurrentItem(1);

        viewPager.setOnPageChangeListener(new HorizontalViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0 ) {
                    snackbar.dismiss();
                    toolbar.animate().translationY(-MainScreen.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
                } else {
                    snackbar.show();
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void generateVerticalAdapters(ArrayList<PagerAdapter> verticalAdapters) {
        for (int i=0; i<2; i++){
            if(i == 1){
                verticalAdapters.add(new VerticalPagerAdapter(MainScreen.this));
            }/*else if(i == 2){
                verticalAdapters.add(new DrawerAdapter(MainScreen.this,this, 2, 1,false));
            }*/else{
                verticalAdapters.add(new DrawerAdapter(MainScreen.this,this, 0, 1,true));
            }

        }
    }

    public void store(int i) {

        database = MainScreen.this.openOrCreateDatabase(Constants.databaseName,MODE_PRIVATE,null);
        if (database != null) {

            Cursor cursor = database.rawQuery("select * from '" + Constants.bookmarksTable + "' WHERE article_id = '"
                    +Constants.newsArrayList.get(i).getData().getArticle_id()+"'", null);

            if(cursor.getCount() == 0) {

                ContentValues contentValues = new ContentValues();

                contentValues.put("category_name", Constants.newsArrayList.get(i).getData().getCategory_name());
                contentValues.put("article_id", Constants.newsArrayList.get(i).getData().getArticle_id());
                contentValues.put("description", Constants.newsArrayList.get(i).getData().getDescription());
                contentValues.put("title", Constants.newsArrayList.get(i).getData().getTitle());
                contentValues.put("image_url", Constants.newsArrayList.get(i).getData().getImage_url());
                contentValues.put("detail_description_url", Constants.newsArrayList.get(i).getData().getDetail_description_url());
                contentValues.put("pubDate", Constants.newsArrayList.get(i).getData().getPubDate());
                contentValues.put("localImage", "");
                contentValues.put("startFromHere", "no");
                database.insert(Constants.bookmarksTable, null, contentValues);

                bookmarksList.add(Constants.newsArrayList.get(i).getData().getArticle_id());

                ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                        setImageResource(R.drawable.bookmark_tick);
            }else{

                bookmarksList.remove(Constants.newsArrayList.get(i).getData().getArticle_id());
                ((ImageView) MainScreen.snackbar.getView().findViewById(R.id.option_one)).
                        setImageResource(R.drawable.bookmark);

//                database.rawQuery(
//                        "delete from " + Constants.bookmarksTable + " WHERE article_id = '"
//                        +Constants.newsArrayList.get(i).getData().getArticle_id()+"'", null);

                database.delete(Constants.bookmarksTable, "article_id" + "='" + Constants.newsArrayList.get(i).getData().getArticle_id()
                        +"'", null);

            }
        } else {

            Toast.makeText(getApplicationContext(), "Database Not Found...", Toast.LENGTH_SHORT).show();
        }

        fetchBookMarkIds();

    }

    public View setInfiniteViewPager(int position){

        LayoutInflater layoutInflater = (LayoutInflater) MainScreen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.viewpagetitem,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                MainScreen.this.getResources().getDisplayMetrics().widthPixels*75/100);
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
                builder.setStartAnimations(MainScreen.this, R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setExitAnimations(MainScreen.this, R.anim.slide_in_left, R.anim.slide_out_right);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainScreen.this, Uri.parse(generalNewsBean.getData().getDetail_description_url()));
            }
        });

        Glide.with(MainScreen.this)
                .load(generalNewsBean.getData().getImage_url())
                .into(imageView);



        return view;

    }


    class GetNews extends AsyncTask{

        Retrofit retrofit;
        ApiInterface restInterface;
        NewsModel checkNewsModel;


        Call<NewsModel> callback;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Constants.newsArrayList.clear();

            retrofit = ApiClient.getClient();
            restInterface = retrofit.create(ApiInterface.class);        }

        @Override
        protected Object doInBackground(Object[] params) {

            callback = restInterface.fetchGeneralNews();

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            callback.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                    checkNewsModel = response.body();

                    if(checkNewsModel.getStatus().equals("success")) {
                        Constants.newsArrayList.addAll(checkNewsModel.getGeneral_news());
                        storeNews();

                        fetchBookMarkIds();
                       // VerticalPagerFragment.infiniteViewPager.notifyAll();

                        ArrayList<PagerAdapter> verticalAdapters = new ArrayList<PagerAdapter>();
                        generateVerticalAdapters(verticalAdapters);
                        doubleViewPagerAdapter = new com.appsbl.dmarion.adapter.DoubleViewPagerAdapter(getApplicationContext(), verticalAdapters);

                        viewPager.setAdapter(doubleViewPagerAdapter);
                        viewPager.setCurrentItem(1);


                        Toast.makeText(MainScreen.this, "List is Populated..."+Constants.newsArrayList.size(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Does not match..."+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void storeNews() {

        if (database != null) {

            database.execSQL("DROP TABLE IF EXISTS '" + Constants.newsTable + "'");

            database.execSQL("create table if not exists "+Constants.newsTable +" (category_name text" +
                    ", article_id text" +
                    ", title text" +
                    ", description text" +
                    ", image_url text" +
                    ", detail_description_url text" +
                    ", pubDate text" +
                    ", localImage text" +
                    ", startFromHere text)");

            for(int i = 0 ; i < Constants.newsArrayList.size(); i++){

                Log.d("trackArticleId>>",Constants.newsArrayList.get(i).getData().getArticle_id());
                ContentValues contentValues = new ContentValues();
                contentValues.put("article_id", Constants.newsArrayList.get(i).getData().getArticle_id());
                contentValues.put("category_name", Constants.newsArrayList.get(i).getData().getCategory_name());
                contentValues.put("description", Constants.newsArrayList.get(i).getData().getDescription());
                contentValues.put("title", Constants.newsArrayList.get(i).getData().getTitle());
                contentValues.put("image_url", Constants.newsArrayList.get(i).getData().getImage_url());
                contentValues.put("detail_description_url", Constants.newsArrayList.get(i).getData().getDetail_description_url());
                contentValues.put("pubDate", Constants.newsArrayList.get(i).getData().getPubDate());
                contentValues.put("localImage", "");
                contentValues.put("startFromHere", "no");
                database.insert(Constants.newsTable, null, contentValues);

            }

        } else {

            Toast.makeText(getApplicationContext(), "Database Not Found...", Toast.LENGTH_SHORT).show();
        }

    }

    void showError(final String err) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainScreen.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onResume() {
        super.onResume();


    }

}
