package com.android.mb.movie.retrofit.cache.intercept;

import android.text.TextUtils;
import android.util.Log;

import com.android.mb.movie.retrofit.cache.RetrofitCache;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/9/5
 *     desc  : 缓存基类.
 * </pre>
 */

public class BaseInterceptor {

    private static String TAG = BaseInterceptor.class.getSimpleName();
    private final static int RESPONSE_CODE = 200;

    protected Response mockResponse(Interceptor.Chain chain){
        Request request = chain.request();
        try{
            String url = request.url().url().toString();
            String mockData = RetrofitCache.getInatance().getMockData(url);
            if (!TextUtils.isEmpty(mockData)){
                Log.d(TAG,"get data from mock");
                Response response = new Response.Builder().protocol(Protocol.HTTP_1_0)
                        .code(RESPONSE_CODE)
                        .request(request)
                        .body(ResponseBody.create(null,mockData))
                        .build();
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
