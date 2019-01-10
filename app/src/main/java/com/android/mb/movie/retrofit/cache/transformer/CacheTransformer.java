package com.android.mb.movie.retrofit.cache.transformer;


import android.util.Log;

import com.android.mb.movie.retrofit.cache.RetrofitCache;

import java.lang.reflect.Field;

import rx.Observable;
import rx.internal.operators.OnSubscribeLift;


/**
 *
 * <pre>
 *     author: cgy
 *     time  : 2017/9/5
 *     desc  :
 * </pre>
 *
 */
public class CacheTransformer {

    private static String TAG = CacheTransformer.class.getSimpleName();


    public static <T> Observable.Transformer<T, T> emptyTransformer() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                Field fdOnSubscribe = null;
                Object serviceMethodObj;
                Object[] args;
                try {

                    long startTime = System.currentTimeMillis();
                    fdOnSubscribe = tObservable.getClass().getDeclaredField("onSubscribe");
                    fdOnSubscribe.setAccessible(true);
                    Object object = fdOnSubscribe.get(tObservable);
                    if (object instanceof OnSubscribeLift){
                        OnSubscribeLift onSubscribe = (OnSubscribeLift) fdOnSubscribe.get(tObservable);

                        Field fdparent =  onSubscribe.getClass().getDeclaredField("parent");
                        fdparent.setAccessible(true);
                        Object onSubscribeObj =  fdparent.get(onSubscribe);

                        Class cls  = Class.forName("retrofit2.adapter.rxjava.RxJavaCallAdapterFactory$CallOnSubscribe");

                        Field foriginalCall = cls.getDeclaredField("originalCall");
                        foriginalCall.setAccessible(true);

                        Object OkhttpCallObj  = foriginalCall.get(onSubscribeObj);

                        Class clsOkhttpCall = Class.forName("retrofit2.OkHttpCall");
                        Field fdArgs = clsOkhttpCall.getDeclaredField("args");
                        fdArgs.setAccessible(true);
                        args = (Object[]) fdArgs.get(OkhttpCallObj);

                        Field fdserviceMethod  = clsOkhttpCall.getDeclaredField("serviceMethod");
                        fdserviceMethod.setAccessible(true);
                        serviceMethodObj =  fdserviceMethod.get(OkhttpCallObj);

                        Log.d(TAG,"CacheTransformer refelect time cost: "+(System.currentTimeMillis()-startTime)+"ms");
                        if (serviceMethodObj!=null){
                            RetrofitCache.getInatance().addMethodInfo(serviceMethodObj,args);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                }
                return tObservable;
            }
        };
    }
}
