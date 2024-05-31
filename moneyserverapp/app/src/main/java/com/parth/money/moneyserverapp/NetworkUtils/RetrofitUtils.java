package com.parth.money.moneyserverapp.NetworkUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parth.money.moneyserverapp.ApiUtils.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {

    private static RetrofitUtils instance = null;
    private ApiService apiService;


    private RetrofitUtils(){
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.29.217:9100/")
                .baseUrl("http://192.168.29.179:8080/parth-moneyserver-services/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitUtils getInstance(){
        if (instance == null) {
            instance = new RetrofitUtils();
        }
        return instance;
    }

    public ApiService getApiService() {
        return apiService;
    }

}
