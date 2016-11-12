package com.appsbl.dmarion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.appsbl.dmarion.model.NewsModel;
import com.pddstudio.urlshortener.URLShortener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 04-11-2016.
 */

public class Constants {

    public static String dataFilePath = "/Dateline marion/data/";


    public static String databaseName = "Dateline_Marion.db";
    public static String newsTable = "news";
    public static String bookmarksTable = "bookmarks";

    public static List<NewsModel.GeneralNewsBean> newsArrayList = new ArrayList<>();


    //http://020103050.com/clients/020103050/generalnews-a2b.txt
    //http://020103050.com/clients/020103050/leftpanel-a2b.txt
    public static final String BASE_URL = "http://020103050.com/clients/020103050/";
    public static final String GENERAL_NEWS = "generalnews-a2b.txt";
    public static final String LEFT_PANEL = "leftpanel-a2b.txt";
    public static final String CHECK_UPDATE = "settings-a2b.txt";

    public static void shareOptions(final Context context, final String shareString,final String title){

        final ProgressDialog progressBar = new ProgressDialog(context);
        URLShortener.shortUrl(shareString, new URLShortener.LoadingCallback() {
            @Override
            public void startedLoading() {


                progressBar.setIndeterminate(true);
                progressBar.show();



            }

            @Override
            public void finishedLoading(@Nullable String shortUrl) {

                progressBar.dismiss();
                if(shortUrl != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, title+"\n\n"+shortUrl+"\n\n--Shared Via Dateline Marion App");

                    context.startActivity(sendIntent);
                }else{
                    Toast.makeText(context,"Could not generate Url", Toast.LENGTH_LONG).show();
                }}
        });


        // Log.d("shortenUrl",shortenUrl(shareString)+ "\n\n- - Shared Via - - \n\n" + "Heze App");

    }

}
