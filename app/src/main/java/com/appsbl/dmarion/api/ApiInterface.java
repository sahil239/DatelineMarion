package com.appsbl.dmarion.api;

import com.appsbl.dmarion.Constants;
import com.appsbl.dmarion.model.CheckNewsModel;
import com.appsbl.dmarion.model.LeftPanel;
import com.appsbl.dmarion.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by HP on 12-11-2016.
 */

public interface ApiInterface {

    @GET(Constants.GENERAL_NEWS)
    Call<NewsModel> fetchGeneralNews();

    @GET(Constants.LEFT_PANEL)
    Call<LeftPanel> leftPanel();

    @GET(Constants.CHECK_UPDATE)
    Call<CheckNewsModel> checkToUpdate();

}
