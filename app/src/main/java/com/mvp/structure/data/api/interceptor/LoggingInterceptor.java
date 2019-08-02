package com.mvp.structure.data.api.interceptor;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggingInterceptor implements Interceptor {
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TAG = "LoggingInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.i(TAG, String.format("Sending %s request %s on %s%n%s",
                        request.method(),
                        request.url(),
                        chain.connection(),
                        request.headers())
                );

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if(responseBody==null){
            return response;
        }
        String bodyString =responseBody.string();
        long t2 = System.nanoTime();
        Log.i(TAG, String.format("Received response for %s in %.1fms%n%s" + SINGLE_DIVIDER + SINGLE_DIVIDER + "\n %s",
                        response.request().url(),
                        (t2 - t1) / 1e6d,
                        response.headers(),
                bodyString)
                );

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }
}
