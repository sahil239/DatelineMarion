package com.appsbl.dmarion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.appsbl.dmarion.model.LeftPanel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ToggleButton toggleButton;

    TextView about_us, about_app, terms_of_service, contact_us, subscribe_url, rate_application_text, share_application_text, android_app_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        editor = sp.edit();

        toggleButton = (ToggleButton) findViewById(R.id.toggle2);

        if (AppCompatDelegate.getDefaultNightMode()
                == AppCompatDelegate.MODE_NIGHT_YES) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }

        about_us = (TextView) findViewById(R.id.about_us);
        about_app = (TextView) findViewById(R.id.about_app);
        terms_of_service = (TextView) findViewById(R.id.terms_of_service);
        contact_us = (TextView) findViewById(R.id.contact_us);
        subscribe_url = (TextView) findViewById(R.id.subscribe_url);
        rate_application_text = (TextView) findViewById(R.id.rate_application_text);
        share_application_text = (TextView) findViewById(R.id.share_application_text);
        android_app_version = (TextView) findViewById(R.id.android_app_version);

        Gson gson = new Gson();
        LeftPanel leftPanel = gson.fromJson(sp.getString("leftPanel",""),LeftPanel.class);
        final LeftPanel.SettingInfoBean settingInfoBean = leftPanel.getSetting_info();

        android_app_version.setText(settingInfoBean.getAndroid_app_version());

        share_application_text.setText(settingInfoBean.getShare_application_text());
        share_application_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Constants.shareOptions(SettingsActivity.this,settingInfoBean.getApplication_share_url_android(),settingInfoBean.getApplication_share_text());
            }
        });
        rate_application_text.setText(settingInfoBean.getRate_application_text());
        rate_application_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

            }
        });

        subscribe_url.setText(settingInfoBean.getSubscribe_url_text());
        subscribe_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                settingInfoBean.getSubscribe_url();
            }
        });
        about_us.setText(settingInfoBean.getAbout_us_text());
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OPen Link
                loadUrl(settingInfoBean.getAbout_us());
            }
        });

        about_app.setText(settingInfoBean.getAbout_app_text());

        terms_of_service.setText(settingInfoBean.getTerms_of_service_text());
        terms_of_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl(settingInfoBean.getTerms_of_service());
            }
        });

        contact_us.setText(settingInfoBean.getContact_us_text());
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl(settingInfoBean.getContact_us_text());
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (AppCompatDelegate.getDefaultNightMode()
                        == AppCompatDelegate.MODE_NIGHT_YES) {
                    editor.putBoolean("nigthMode",true);
                    editor.commit();
                    setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    editor.putBoolean("nigthMode",false);
                    editor.commit();
                    setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

              /*  Intent intent = getIntent();
                finish();
                startActivity(intent);
*/

             //   setNightMode();

            }
        });
        try {
            JSONObject jsonObject = new JSONObject(sp.getString("leftPanel", ""));
            Log.d("setting_info", "" + jsonObject.getJSONObject("setting_info"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }


    }


    void loadUrl(String url){
        if(url!=null || !url.isEmpty()){
        Intent intent = new Intent(SettingsActivity.this,WebpageFromSetting.class);
        intent.putExtra("url",url);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
    }


}
