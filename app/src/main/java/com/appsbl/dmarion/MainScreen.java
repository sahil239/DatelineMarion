package com.appsbl.dmarion;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import com.appsbl.dmarion.fragments.CustomWebFragment;
import com.appsbl.dmarion.fragments.DrawerFragment;
import com.appsbl.dmarion.fragments.VerticalPagerFragment;
import com.appsbl.dmarion.model.NewsModel;
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

public class MainScreen extends AppCompatActivity {

    public static ViewPager viewpager;
    File SDCardRoot = Environment.getExternalStorageDirectory();
    File folder = new File(SDCardRoot+Constants.dataFilePath);
    public static Snackbar snackbar;
    RelativeLayout linear;
    public  static Toolbar toolbar;

    public static ImageView refreshortop;
    ImageView opendrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        refreshortop = (ImageView)findViewById(R.id.refreshortop);
        opendrawer = (ImageView)findViewById(R.id.opendrawer);

        opendrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(0);
            }
        });
        refreshortop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(VerticalPagerFragment.infiniteViewPager.getCurrentPage() == 0){

                }else{
                    VerticalPagerFragment.infiniteViewPager.snapToPage(0);
                }


            }
        });


       // setSupportActionBar(toolbar);

       // getSupportActionBar().setShowHideAnimationEnabled(false);

        openOrCreateDatabase();
        fetchDataFromSqlite();
        loadUI();
    }

    void bottomSnackBar(){

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

        layout.setBackgroundColor(Color.parseColor("#38000000"));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        layout.setLayoutParams(layoutParams);
        snackView.setLayoutParams(layout.getLayoutParams());

        ((ImageView)snackView.findViewById(R.id.option_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        ((ImageView)snackView.findViewById(R.id.option_two)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = VerticalPagerFragment.infiniteViewPager.getCurrentPage();
                NewsModel.GeneralNewsBean.DataBean dataBean = Constants.newsArrayList.get(count).getData();
                Constants.shareOptions(MainScreen.this,dataBean.getDetail_description_url(),dataBean.getTitle());

            }
        });
        layout.addView(snackView, 0);
// Show the Snackbar
        snackbar.show();
    }

    SQLiteDatabase database;

    void openOrCreateDatabase(){

        database = openOrCreateDatabase(Constants.databaseName, MODE_PRIVATE, null);
        database.execSQL("create table if not exists "+Constants.newsTable +"(id  INTEGER PRIMARY KEY AUTOINCREMENT, category_name text, title text, description text" +
                ", image_url text" +
                ", detail_description_url text" +
                ", pubDate text" +
                ", localImage text" +
                ", startFromHere text)");

    }


    void fetchDataFromSqlite(){
        Constants.newsArrayList.clear();
        if (database != null) {
            Cursor cursor = database.rawQuery("select * from '" + Constants.newsTable + "'", null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    //Getting data using column name

                    Log.d("cursor",cursor.getColumnNames()[0]);
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


                Toast.makeText(getApplicationContext(), "List is Populated..."+Constants.newsArrayList.size(), Toast.LENGTH_SHORT).show();

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

        List<Fragment> fragments = getFragments();

        viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(new MyPageAdapter(getSupportFragmentManager(),fragments));
        viewpager.setCurrentItem(1);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0 || position == 2){
                    snackbar.dismiss();
                    toolbar.animate().translationY(-MainScreen.toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
                }else{
                    snackbar.show();
                    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private List<Fragment> getFragments(){

        List<Fragment> fList = new ArrayList<Fragment>();



        fList.add(DrawerFragment.newInstance("Fragment 1"));

        fList.add(VerticalPagerFragment.newInstance("Fragment 2"));

        fList.add(CustomWebFragment.newInstance("Fragment 3"));



        return fList;

    }


    class MyPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;


        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {

            super(fm);

            this.fragments = fragments;

        }

        @Override

        public Fragment getItem(int position) {

            return this.fragments.get(position);

        }


        @Override

        public int getCount() {

            return this.fragments.size();

        }

    }


    public void store() {

        if (database != null) {

            database.execSQL("DROP TABLE IF EXISTS '" + Constants.newsTable + "'");

            database.execSQL("create table if not exists "+Constants.newsTable +"(category_name text," +
                    " title text" +
                    ", description text" +
                    ", image_url text" +
                    ", detail_description_url text" +
                    ", pubDate text" +
                    ", localImage text" +
                    ", startFromHere text)");

            for(int i = 0 ; i < Constants.newsArrayList.size(); i++){

                ContentValues contentValues = new ContentValues();
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

    void downloadFile(String dwnload_file_path){

        try {
            // showProgress(dwnload_file_path);

            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file

            if(!folder.exists()){
                folder.mkdirs();
            }
            //create a new file, to save the downloaded file
            File file = new File(folder,dwnload_file_path.split("/")[dwnload_file_path.split("/").length-1]);

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);

                // update the progressbar //

            }
            //close the output stream when complete //

            //   showError("Downloaded " + file.exists());
            fileOutput.close();


        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            //    dialog.dismiss();// if you want close it..
            e.printStackTrace();
        } catch (final IOException e) {
            //   showError("Error : IOException " + e);
         //   dialog.dismiss();// if you want close it..
            e.printStackTrace();
        }
        catch (final Exception e) {
//            dialog.dismiss();// if you want close it..
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MainScreen.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }
}
