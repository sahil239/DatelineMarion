package com.appsbl.dmarion;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbl.dmarion.api.ApiClient;
import com.appsbl.dmarion.api.ApiInterface;
import com.appsbl.dmarion.model.CheckNewsModel;
import com.appsbl.dmarion.model.LeftPanel;
import com.appsbl.dmarion.model.NewsModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {

    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
    RelativeLayout activity_splash;
    SQLiteDatabase database;
    File SDCardRoot = Environment.getExternalStorageDirectory();
    File folder = new File(SDCardRoot+Constants.dataFilePath);

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;
    String lastData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        editor = sharedPreferences.edit();

        openOrCreateDatabase();
        fetchDataFromSqlite(Constants.newsTable);
        activity_splash = (RelativeLayout)findViewById(R.id.activity_splash);

        if(checkPermission()){

            new GetLeftPanel().execute();

        }else{
            requestPermission();
        }
    }



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

    void fetchDataFromSqlite(String tableName){

        SQLiteDatabase database = openOrCreateDatabase(Constants.databaseName, MODE_PRIVATE, null);

        Constants.newsArrayList.clear();
        if (database != null) {

            Cursor cursor  = database.rawQuery("select * from '" + tableName + "'", null);


            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {

                    Log.d("cursor",cursor.getString(cursor.getColumnIndex("article_id")));

                    lastData = cursor.getString(cursor.getColumnIndex("article_id"));
                    break;

                } while (cursor.moveToNext());


            } else {

                lastData = "";

            }
        }
    }

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
          //  totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                 //   pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
              //  downloadedSize += bufferLength;
                // update the progressbar //

            }
            //close the output stream when complete //

         //   showError("Downloaded " + file.exists());
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                 //   dialog.dismiss();// if you want close it..
                }
            });

        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
        //    dialog.dismiss();// if you want close it..
            e.printStackTrace();
        } catch (final IOException e) {
         //   showError("Error : IOException " + e);
          //  dialog.dismiss();// if you want close it..
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
                Toast.makeText(SplashActivity.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress(String file_path){
        dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.myprogressdialog);
        dialog.setTitle("Download Progress");

        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();

        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
    }

    private boolean checkPermission() {

        int read_external = ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_external = ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (read_external == PackageManager.PERMISSION_GRANTED
                && write_external == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {
            //   requestPermission();
            return false;

        }
    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 10001);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //  Snackbar.make(rel, "Permission Granted.", Snackbar.LENGTH_LONG).show();
                   // new BackGroundTask(Constants.newsUrl).execute();

                    new CheckData().execute();

                } else {

                      Snackbar.make(activity_splash, "Permission cannot be denied.", Snackbar.LENGTH_INDEFINITE)
                              .setAction("ALLOW", new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              requestPermission();
                          }
                      }).show();

                }
                break;
        }
    }

    class CheckData extends AsyncTask{

        Retrofit retrofit;
        ApiInterface restInterface;
        CheckNewsModel checkNewsModel;


        Call<CheckNewsModel> callback;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            retrofit = ApiClient.getClient();
            restInterface = retrofit.create(ApiInterface.class);        }

        @Override
        protected Object doInBackground(Object[] params) {

            callback = restInterface.checkToUpdate();

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            callback.enqueue(new Callback<CheckNewsModel>() {
                @Override
                public void onResponse(Call<CheckNewsModel> call, Response<CheckNewsModel> response) {

                    checkNewsModel = response.body();

                    if(lastData.equals(checkNewsModel.getSetting_info().getLast_updated_id())){
                        Intent intent  = new Intent(SplashActivity.this,MainScreen.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        Toast.makeText(getApplicationContext(), "Match...", Toast.LENGTH_SHORT).show();

                    }else{

                        new GetNews().execute();
                        Toast.makeText(getApplicationContext(), "Updating Data. ..", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<CheckNewsModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Does not match..."+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class GetNews extends AsyncTask{

        Retrofit retrofit;
        ApiInterface restInterface;
        NewsModel checkNewsModel;


        Call<NewsModel> callback;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


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
                        store();

                        Intent intent  = new Intent(SplashActivity.this,MainScreen.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Does not match..."+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    class GetLeftPanel extends AsyncTask{

        Retrofit retrofit;
        ApiInterface restInterface;
        LeftPanel leftPanel;


        Call<LeftPanel> callback;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            retrofit = ApiClient.getClient();
            restInterface = retrofit.create(ApiInterface.class);        }

        @Override
        protected Object doInBackground(Object[] params) {

            callback = restInterface.leftPanel();

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            callback.enqueue(new Callback<LeftPanel>() {
                @Override
                public void onResponse(Call<LeftPanel> call, Response<LeftPanel> response) {

                    leftPanel = response.body();

                    if(leftPanel.getStatus().equals("success")) {

                        editor.putString("leftPanel",new Gson().toJson(leftPanel));
                        editor.commit();
                    }

                    new CheckData().execute();
                }

                @Override
                public void onFailure(Call<LeftPanel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Does not match..."+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void store() {

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

/*

    class BackGroundTask extends AsyncTask{

        String fileToDownload;

        public BackGroundTask(String fileToDownload) {
            this.fileToDownload = fileToDownload;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //  showProgress(fileToDownload);
        }

        @Override
        protected Object doInBackground(Object[] params) {



            downloadFile(fileToDownload);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            //   dialog.dismiss();

            if(fileToDownload.equals(Constants.newsUrl)){
                try {

                    Log.d("Constants.newsArrayList",folder.getAbsolutePath());
                    JSONObject obj = new JSONObject(loadJSONFromAsset(folder.getAbsolutePath()+"/"+Constants.newsFile));
                    Gson gson = new Gson();
                    NewsModel dataModel = gson.fromJson(obj.toString(),NewsModel.class);
                    Constants.newsArrayList = dataModel.getGeneral_news();

                    Log.d("Constants.newsArrayList",Constants.newsArrayList.size()+" size of list");

                } catch (Exception e) {
                    Log.d("Constants.newsArrayList",e.getMessage());
                    e.printStackTrace();
                }
                new BackGroundTask(Constants.leftPanelUrl).execute();
            }else{

                Toast.makeText(SplashActivity.this, "Data updated", Toast.LENGTH_LONG).show();

                store();
                Intent intent = new Intent(SplashActivity.this,MainScreen.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        }
    }
*/


}

