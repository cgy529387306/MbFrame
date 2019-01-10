package com.android.mb.movie.retrofit.http.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/12/26
 *     desc  : 公共参数拦截器.
 * </pre>
 */

public class ParameterInterceptor implements Interceptor {

    private Map<String,String> mParams;

    public ParameterInterceptor(Map<String,String> commonParams) {
        mParams = commonParams;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        HttpUrl.Builder builder = originalRequest.url().newBuilder();
        if(mParams != null){
            for (String key : mParams.keySet()) {
                builder.addQueryParameter(key, mParams.get(key));
            }
        }
        HttpUrl modifiedUrl = builder.build();
        request = originalRequest.newBuilder().url(modifiedUrl).build();
        return chain.proceed(request);
    }
}
