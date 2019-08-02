package com.mvp.structure.data.api.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestCacheInterceptor implements Interceptor
{

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
//        if (!SystemTool.checkNet(AppApplication.context())) {
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
//        }

        Response originalResponse = chain.proceed(request);
//        if (SystemTool.checkNet(AppApplication.context())) {
//            //When there is a network, read the configuration in @Headers on the interface, you can make a unified setting here.
//            String cacheControl = request.cacheControl().toString();
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", cacheControl)
//                    .removeHeader("Pragma")
//                    .build();
//        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
//        }
    }
}
