package com.android.mb.movie.retrofit.http.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/12/26
 *     desc  : 头部信息拦截器
 * </pre>
 */

public class HeaderInterceptor implements Interceptor {

    private Map<String, String> mHeaders;

    public HeaderInterceptor(Map<String, String> headers) {
        mHeaders = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();
        requestBuilder.header("Charset", "UTF-8");
        requestBuilder.header("Connection", "Keep-Alive");
        requestBuilder.header("Charset", "UTF-8");
        requestBuilder.header("Content-type", "application/json");

        if(mHeaders != null){
            for (String key : mHeaders.keySet()) {
                requestBuilder.header(key, mHeaders.get(key));
            }
        }
        requestBuilder.method(originalRequest.method(), originalRequest.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
