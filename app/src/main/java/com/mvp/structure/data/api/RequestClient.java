package com.mvp.structure.data.api;


import android.content.Context;

import com.mvp.structure.BuildConfig;
import com.mvp.structure.application.AppApplication;
import com.mvp.structure.data.api.converter.GsonConverterFactory;
import com.mvp.structure.data.api.converter.NullOnEmptyConverterFactory;
import com.mvp.structure.data.api.interceptor.HeaderInterceptor;
import com.mvp.structure.data.api.interceptor.LoggingInterceptor;
import com.mvp.structure.data.bean.response.UserResponse;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by thai.cao on 2019/07/30
 */
public class RequestClient {

    private static volatile ServerAPI sServerAPI;//Singleton
    private static RequestClient sSharedInstance = null;
    private Context mContext;

    public static synchronized RequestClient getSharedInstance() {
        if (sSharedInstance == null) {
            sSharedInstance = new RequestClient(AppApplication.context());
        }
        return sSharedInstance;
    }

    public RequestClient(Context context) {

        this.mContext = context;

        getServerAPI();
    }

    public static ServerAPI getServerAPI() {
        if (sServerAPI == null) {
            synchronized (RequestClient.class) {
                if (sServerAPI == null) {
                    OkHttpClient.Builder clientBuilder = getClientBuilder();
                    HashMap<String, String> headerMap = new HashMap<>();
                    headerMap.put("appver", String.valueOf(BuildConfig.VERSION_CODE));
                    clientBuilder.addInterceptor(new HeaderInterceptor(headerMap));
                    //Configuring a log interceptor
                    if (BuildConfig.DEBUG) {
                        clientBuilder
                                .addInterceptor(new LoggingInterceptor());
                    }

                    sServerAPI = getRetrofitBuilder("https://api.github.com/", clientBuilder.build()).build().create(ServerAPI.class);
                }
            }
        }
        return sServerAPI;

    }

    /**
     * @param url    domain name
     * @param client okhttp Request client
     * @return retrofit.Builder
     */
    private static Retrofit.Builder getRetrofitBuilder(String url, OkHttpClient client) {
//        Gson gson = new GsonBuilder()
//                .enableComplexMapKeySerialization()
//                .serializeNulls()
//                .setDateFormat(DateFormat.LONG)
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .setPrettyPrinting()
//                .setVersion(1.0)
//                .create();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
    }


    private static OkHttpClient.Builder getClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.CONNECT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//Retry
        // .writeTimeout(HttpConfig.WRITE_TIME_OUT_SECONDS, TimeUnit.SECONDS);
    }

//    public void getCategories( final Callback<List<Category>> cb){
//        sServerAPI.getCategoriesData().enqueue(cb);
//    }
    public Observable<UserResponse> getUsers(String query){
        return sServerAPI.getUsers(query);
    }

}
