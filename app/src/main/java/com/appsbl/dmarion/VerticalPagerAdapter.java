package com.appsbl.dmarion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.appsbl.dmarion.model.NewsModel;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class VerticalPagerAdapter extends PagerAdapter {


    private Activity mActivity;

    public VerticalPagerAdapter(Activity activity){
        mActivity = activity;

    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return Constants.newsArrayList.size();
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

        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.viewpagetitem,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                mActivity.getResources().getDisplayMetrics().widthPixels*75/100);
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
                builder.setStartAnimations(mActivity, R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setExitAnimations(mActivity, R.anim.slide_in_left, R.anim.slide_out_right);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(mActivity, Uri.parse(generalNewsBean.getData().getDetail_description_url()));
            }
        });

        Glide.with(mActivity)
                .load(generalNewsBean.getData().getImage_url())
                .into(imageView);


        container.addView(view);
        return view;
    }



}