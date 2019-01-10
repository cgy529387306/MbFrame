package com.android.mb.movie.retrofit.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.mb.movie.retrofit.cache.anno.Cache;
import com.android.mb.movie.retrofit.cache.anno.Mock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import retrofit2.Retrofit;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/9/5
 *     desc  : Retrofit缓存处理
 * </pre>
 */

public class RetrofitCache {
    private static String TAG = RetrofitCache.class.getSimpleName();

    private static volatile RetrofitCache mRetrofit;
    private Vector<Map> mVector;
    private Map<String,Long> mUrlMap;

    private Context mContext;
    private Long mDefaultTime = 0L;
    private TimeUnit mDefaultTimeUnit = TimeUnit.SECONDS;

    private Map mUrlAragsMap =null;

    private RetrofitCache(){
        clear();
        mUrlAragsMap = new HashMap();
    }

    public Context getContext(){
        return mContext;
    }

    public static RetrofitCache getInatance(){
        if (mRetrofit == null){
            synchronized (RetrofitCache.class){
                if (mRetrofit == null){
                    mRetrofit = new RetrofitCache();
                }
            }
        }
        return mRetrofit;
    }

    public RetrofitCache init(Context context){
        mContext = context.getApplicationContext();
        return this;
    }

    public void addMethodInfo(Object serviceMethod, Object[] args){
        String url = "";
        try {
            url =  buildRequestUrl(serviceMethod,args);
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
        if (!TextUtils.isEmpty(url)){
            if (!mUrlAragsMap.containsKey(url)){
                mUrlAragsMap.put(url,args);
            }
        }
    }

    /**
     * 设置缓存时间.
     * @param time
     * @return
     */
    public RetrofitCache setDefaultTime(long time){
        mDefaultTime = time;
        return this;
    }

    /**
     * 设置缓存时间.
     * @param timeUnit
     * @return
     */
    public RetrofitCache setDefaultTimeUnit(TimeUnit timeUnit){
        mDefaultTimeUnit = timeUnit;
        return this;
    }

    public long getDaultTime(){
        return mDefaultTime;
    }
    public TimeUnit getDefaultTimeUnit(){
        return mDefaultTimeUnit;
    }

    public String getMockData(String url){
        for (Map serviceMethodCache:mVector) {

            for (Object entry:serviceMethodCache.keySet()){
                Object o = serviceMethodCache.get(entry);
                try {

                    if (mUrlAragsMap.containsKey(url)){
                        Object[] args = (Object[]) mUrlAragsMap.get(url);
                        String reqUrl =  buildRequestUrl(o,args);
                        if (reqUrl.equals(url)){
                            Method m = (Method) entry;
                            Mock mock =  m.getAnnotation(Mock.class);
                            if (mock!=null){
                                return mock.value();
                            }
                            return null;
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                }
            }
        }

        return null;
    }

    public Long getCacheTime(String url){
        if (mUrlMap!=null){
            Long type = mUrlMap.get(url);
            if (type!=null){
                return type;
            }
        }
        for (Map serviceMethodCache:mVector) {

            for (Object entry:serviceMethodCache.keySet()){
                Object o = serviceMethodCache.get(entry);
                try {

                    if (mUrlAragsMap.containsKey(url)){
                        Object[] args = (Object[]) mUrlAragsMap.get(url);
                        String reqUrl =  buildRequestUrl(o,args);
                        if (reqUrl.equals(url)){
                            Method m = (Method) entry;
                            Cache cache =  m.getAnnotation(Cache.class);
                            if (cache!=null){
                                TimeUnit timeUnit =  mDefaultTimeUnit;
                                if (cache.timeUnit() != TimeUnit.NANOSECONDS){
                                    timeUnit = cache.timeUnit();
                                }
                                long t = mDefaultTime;
                                if (cache.time() != -1){
                                    t = cache.time();
                                }
                                long tm =  timeUnit.toSeconds(t);
                                getUrlMap().put(url, tm);
                                return tm;
                            }else{
                                getUrlMap().put(url, 0L);
                                return 0L;
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                }
            }
        }
        getUrlMap().put(url, 0L);
        return 0L;
    }
    private Map getUrlMap(){
        if (mUrlMap==null){
            mUrlMap = new HashMap<String, Long>();
        }
        return  mUrlMap;
    }

    private String buildRequestUrl(Object serviceMethod, Object[] args) throws Exception {
        Class clsServiceMethod =  Class.forName("retrofit2.ServiceMethod");
        Method toRequestMethod =  clsServiceMethod.getDeclaredMethod("toRequest", Object[].class );
        toRequestMethod.setAccessible(true);
        try {
            Request request = (Request) toRequestMethod.invoke(serviceMethod,new Object[]{args});
            return request.url().toString();
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return "";
    }

    public RetrofitCache addRetrofit(Retrofit retrofit){
        try {

            Class cls = retrofit.getClass();
            Field field =  cls.getDeclaredField("serviceMethodCache");
            field.setAccessible(true);
            if (mVector == null){
                mVector = new Vector<Map>();
            }
            Map m = (Map) field.get(retrofit);
            mVector.add(m);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public void clear(){
        if (mVector!=null){
            mVector.clear();
            mVector =null;
        }
        if (mUrlMap!=null){
            mUrlMap.clear();
            mUrlMap =null;
        }
        if (mUrlAragsMap!=null){
            mUrlAragsMap.clear();
            mUrlAragsMap =null;
        }
        mRetrofit = null;
    }
}
