package com.android.mb.movie.retrofit.cache.intercept;

import android.util.Log;

import com.android.mb.movie.retrofit.cache.RetrofitCache;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yale on 2017/6/13.
 */

public class CacheInterceptorOnNet extends com.android.mb.movie.retrofit.cache.intercept.BaseInterceptor implements Interceptor {

    private static String TAG = CacheInterceptorOnNet.class.getSimpleName();

    private boolean isNoCache;

    public CacheInterceptorOnNet(){}

    public CacheInterceptorOnNet(boolean isNoCache) {
        this.isNoCache = isNoCache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response  mockResponse = mockResponse(chain);
        if (mockResponse!=null){
            return mockResponse;
        }
        String url = request.url().url().toString();
        Response response = chain.proceed(request);
        Log.d(TAG,"get data from net = "+response.code());
        Long maxAge = RetrofitCache.getInatance().getCacheTime(url);


        if(isNoCache) {
            return   response.newBuilder()
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "no-cache")
                    .removeHeader("Pragma")
                    .build();
        }else {
            return   response.newBuilder()
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public,max-age="+maxAge)
                    .removeHeader("Pragma")
                    .build();
        }


    }
}
