package com.android.mb.movie.retrofit.cache.intercept;

import android.util.Log;

import com.android.mb.movie.retrofit.cache.RetrofitCache;
import com.android.mb.movie.retrofit.cache.util.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * <pre>
 *     author: cgy
 *     time  : 2017/9/5
 *     desc  : 无网时强制走缓存
 * </pre>
 *
 */
public class CacheForceInterceptorNoNet extends com.android.mb.movie.retrofit.cache.intercept.BaseInterceptor implements Interceptor  {

    private static String TAG = CacheForceInterceptorNoNet.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response  mockResponse = mockResponse(chain);
        if (mockResponse!=null){
            return mockResponse;
        }

        //无网络强制从缓存获取.
       if (!NetUtils.isConnectNet(RetrofitCache.getInatance().getContext())){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();

            Log.d(TAG,"get data from cache");
        }
        Response response = chain.proceed(request);
        if (response.code() == 504){
            Log.d(TAG,"not find in cache, go to chain");
            response = chain.proceed(chain.request());
        }
        return response;
    }
}
