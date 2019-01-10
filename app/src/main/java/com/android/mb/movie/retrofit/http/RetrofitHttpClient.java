package com.android.mb.movie.retrofit.http;

import android.text.TextUtils;

import com.android.mb.movie.app.MBApplication;
import com.android.mb.movie.retrofit.cache.RetrofitCache;
import com.android.mb.movie.retrofit.cache.intercept.CacheForceInterceptorNoNet;
import com.android.mb.movie.retrofit.cache.intercept.CacheInterceptorOnNet;
import com.android.mb.movie.retrofit.http.interceptor.HeaderInterceptor;
import com.android.mb.movie.retrofit.http.interceptor.LogInterceptor;
import com.android.mb.movie.retrofit.http.interceptor.ParameterInterceptor;
import com.android.mb.movie.retrofit.http.util.ArrayUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/12/26
 *     desc  :
 * </pre>
 */

public class RetrofitHttpClient {

    private static String CACHE_DIR = "mbCache";

    // 请求超时时长
    private static int CONNECT_TIMEOUT = 15 * 1000;
    // 读取超时时长
    private static int READ_TIMEOUT = 20 * 1000;
    //缓存大小
    private static int CACHE_SIZE = 500 * 1024 * 1024;


    private final Map<String, String> headers;  // 公共请求头
    private final Map<String, String> commonParams;  //公共参数
    private final int connectTime;  //请求超时时长
    private final int readTime;  //读取超时时长
    private final int cacheSize; //缓存大小
    private final boolean isCache;  //是否缓存
    private String baseUrl; //base URL
    private final boolean isLog; // 是否开启log
    private final boolean isEncry; //请求头加密.
    private final boolean isDotNetDeserializer;  // 是否c#时间反序列化.
    private boolean isNoCache;  //是否强制从网络获取
    final List<Interceptor> interceptors; //自定义拦截器.

    private static Retrofit retrofit;


    public RetrofitHttpClient() {
        this(new Builder());
    }

    private RetrofitHttpClient(Builder builder) {
        this.headers = builder.headers;
        this.commonParams = builder.commonParams;
        this.connectTime = builder.connectTime;
        this.readTime = builder.readTime;
        this.cacheSize = builder.cacheSize;
        this.isCache = builder.isCache;
        this.baseUrl = builder.baseUrl;
        this.isEncry = builder.isEncry;
        this.isLog = builder.isLog;
        this.isNoCache = builder.isNoCache;
        this.isDotNetDeserializer = builder.isDotNetDeserializer;
        this.interceptors = builder.interceptors;

    }

    public Retrofit retrofit(){

        if(TextUtils.isEmpty(baseUrl)) {
            baseUrl = "https://mobileioa.99.com/";
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时时间
        builder.connectTimeout(connectTime, TimeUnit.SECONDS);
        builder.readTimeout(readTime, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);

        //请求头
        builder.addInterceptor(new ParameterInterceptor(commonParams));
        //设置公共参数
        builder.addInterceptor(new HeaderInterceptor(headers));
        //是否开启缓存.
        if(isCache) {
            //设置缓存.
            RetrofitCache.getInatance().init(MBApplication.getInstance());
            File cacheDirectory = new File(MBApplication.getInstance().getCacheDir(), CACHE_DIR);
            Cache cache = new Cache(cacheDirectory, cacheSize);
            //设置缓存大小.
            builder.cache(cache);
            //添加缓存拦截器
            builder.addInterceptor(new CacheForceInterceptorNoNet());
            builder.addNetworkInterceptor(new CacheInterceptorOnNet(isNoCache));
        }

        Converter.Factory factory;

        //是否开启C#时间反序列化
        if(isDotNetDeserializer) {
            factory = ResponseConvertFactory.createDotNet();
        }else {
            factory = ResponseConvertFactory.create();
        }

        //开启日志.
        if(isLog) {
            builder.addNetworkInterceptor(LogInterceptor.getLogInterceptor());
        }

        //添加自定义拦截器
        if(!ArrayUtil.isEmpty(interceptors)){
            for(Interceptor cept : interceptors){
                builder.addInterceptor(cept) ;
            }
        }

        OkHttpClient okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        if(isCache) {
            RetrofitCache.getInatance().addRetrofit(retrofit);
        }

        return retrofit;
    }


    public static final class Builder {
        Map<String, String> headers;  // 公共请求头
        Map<String, String> commonParams;  //公共参数
        int connectTime;  //请求超时时长
        int readTime;  //读取超时时长
        int cacheSize; //缓存大小
        boolean isCache;  //是否缓存
        String baseUrl; //base URL
        boolean isLog; // 是否开启log
        boolean isEncry; //请求头加密.
        boolean isNoCache; //是否强制从网络获取
        boolean isDotNetDeserializer;  // 是否c#时间反序列化.
        List<Interceptor> interceptors; //自定义拦截器.

        //设置默认值.
        public Builder() {
            headers = new HashMap<>();
            commonParams = new HashMap<>();
            connectTime = CONNECT_TIMEOUT;
            readTime = READ_TIMEOUT;
            cacheSize = CACHE_SIZE;
            isCache = true;
            isLog = false;
            isEncry = true;
            isNoCache = false;
            isDotNetDeserializer = false;
            interceptors = new ArrayList<>();
        }

        /**
         * 添加公共请求头
         * @param headers
         * @return
         */
        public Builder addHeader(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * 添加公共参数
         * @param commonParams
         * @return
         */
        public Builder addParameters(Map<String, String> commonParams) {
            this.commonParams = commonParams;
            return this;
        }

        /**
         * 请求超时时长
         * @param time
         * @return
         */
        public Builder connectTimeout(int time) {
            this.connectTime = time;
            return this;
        }

        /**
         * 读取超时时长
         * @param time
         * @return
         */
        public Builder readTimeout(int time) {
            this.readTime = time;
            return this;
        }

        /**
         * 缓存大小.
         * @param size
         * @return
         */
        public Builder cacheSize(int size) {
            this.cacheSize = size;
            return this;
        }

        /**
         *  是否开启缓存 默认开启
         * @param isCache
         * @return
         */
        public Builder addCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        /**
         * 是否强制使用网络请求
         * @param isNoCache
         * @return
         */
        public Builder addNoCache(boolean isNoCache) {
            this.isNoCache = isNoCache;
            return this;
        }

        /**
         * 是否开启Log
         * @param isLog 默认不开启
         * @return
         */
        public Builder addLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }

        /**
         * 请求头是否加密.默认加密
         * @param isEncry
         * @return
         */
        public Builder addEncry(boolean isEncry) {
            this.isEncry = isEncry;
            return this;
        }

        /**
         * 是否开启C#时间反序列化. 默认不开启
         * @param isDotNetDeserializer
         * @return
         */
        public Builder addDotNetDeserializer(boolean isDotNetDeserializer) {
            this.isDotNetDeserializer = isDotNetDeserializer;
            return this;
        }

        /**
         * 添加自定义拦截器.
         * @param interceptorList
         * @return
         */
        public Builder addInterceptor(List<Interceptor> interceptorList) {
            this.interceptors = interceptorList;
            return this;
        }

        /**
         * baseUrl
         * @param baseUrl
         * @return
         */
        public Builder baseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public RetrofitHttpClient build() {
            return new RetrofitHttpClient(this);
        }

    }
}
