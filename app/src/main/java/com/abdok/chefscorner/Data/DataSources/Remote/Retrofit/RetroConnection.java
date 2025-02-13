package com.abdok.chefscorner.Data.DataSources.Remote.Retrofit;

import com.abdok.chefscorner.Utils.Consts;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroConnection {

    private static Retrofit retrofit;

    private static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetroServices getServices(){
        return getInstance().create(RetroServices.class);
    }

}
