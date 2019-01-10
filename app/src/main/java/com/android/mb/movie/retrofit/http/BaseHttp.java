package com.android.mb.movie.retrofit.http;

import android.support.annotation.NonNull;

import com.android.mb.movie.retrofit.http.entity.HttpResult;
import com.android.mb.movie.retrofit.http.exception.ApiException;

import java.util.Map;

import retrofit2.Retrofit;
import rx.functions.Func1;

public abstract class BaseHttp {
    protected Retrofit mRetrofit;

    public BaseHttp(boolean isLog,boolean isCache, boolean isDotNetDeserializer) {

        mRetrofit = new RetrofitHttpClient.Builder()
                .baseUrl(getBaseUrl())
                .addLog(isLog)
                .addHeader(getHead())
                .addCache(isCache)
                .addDotNetDeserializer(isDotNetDeserializer)
                .build()
                .retrofit();
    }

    /**
     * 获取头部信息
     * @return
     */
    protected abstract Map<String, String> getHead();

    /**
     * 获取baseUrl
     * @return
     */
    protected abstract @NonNull
    String getBaseUrl();

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <T>
     */
    public static class HttpResultFunc<T> implements Func1 {

        @Override
        public Object call(Object o) {

            HttpResult<T> httpResult ;
            if(o instanceof HttpResult){
                httpResult = (HttpResult<T>)o;
                if (httpResult.getCode() == 0) {
                    throw new ApiException(40003,httpResult.getErrorMessage());
                }

                return httpResult.getData();
            }

            return null;
        }
    }
}
