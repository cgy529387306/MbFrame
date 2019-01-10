package com.android.mb.movie.service;

import com.android.mb.movie.retrofit.http.entity.HttpResult;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @created by cgy on 2017/6/19
 */
public interface IScheduleService {
    @GET("/api/skill/hot/list")
    Observable<HttpResult<Object>> getHotList();

}
