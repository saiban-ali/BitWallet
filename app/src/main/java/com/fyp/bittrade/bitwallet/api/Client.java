package com.fyp.bittrade.bitwallet.api;

import com.fyp.bittrade.bitwallet.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static String TAG = Client.class.getName();
    private static Client INSTANCE = null;

    private Retrofit retrofit;

    private Client() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
    }

    public static Client getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Client();

        return INSTANCE;
    }

    public Service getClient() {
        return retrofit.create(Service.class);
    }
}
