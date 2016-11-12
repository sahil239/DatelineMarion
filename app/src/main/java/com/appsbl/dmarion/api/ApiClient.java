package com.appsbl.dmarion.api;

/**
 * Created by HP on 12-11-2016.
 */

import com.appsbl.dmarion.Constants;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by android on 11/11/2016.
 */

public class ApiClient {


    private static Retrofit retrofit = null;



    public static Retrofit getClient() {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
