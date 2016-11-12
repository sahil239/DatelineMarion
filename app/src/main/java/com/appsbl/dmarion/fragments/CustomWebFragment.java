package com.appsbl.dmarion.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsbl.dmarion.Constants;
import com.appsbl.dmarion.MainScreen;
import com.appsbl.dmarion.R;
import com.appsbl.dmarion.model.LeftPanel;
import com.appsbl.dmarion.model.NewsModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by HP on 12-11-2016.
 */

public class CustomWebFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;

    public static final CustomWebFragment newInstance(String message)

    {

        CustomWebFragment f = new CustomWebFragment();

        Bundle bdl = new Bundle(1);

        bdl.putString(EXTRA_MESSAGE, message);

        f.setArguments(bdl);

        return f;

    }


    public static WebView webView;

    ImageView back;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        String message = getArguments().getString(EXTRA_MESSAGE);

        View view = inflater.inflate(R.layout.webviewfragment, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        back = (ImageView)view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainScreen.viewpager.setCurrentItem(1);
            }
        });
            webView = (WebView)view.findViewById(R.id.webview);
    webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://www.google.com");


        return view;

    }


}
