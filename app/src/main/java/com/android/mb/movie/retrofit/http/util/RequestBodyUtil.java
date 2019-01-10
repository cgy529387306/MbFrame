package com.android.mb.movie.retrofit.http.util;

import com.google.gson.Gson;

import okhttp3.RequestBody;

/**
 * @version v1.0
 * @Description
 * @Created by cgy on 2017/6/23
 */

public class RequestBodyUtil {

    private static Gson mGson=new Gson();

    public static RequestBody getRequestBody(Object o){
        String strEntity = mGson.toJson(o);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),strEntity);

        return body;
    }
}
