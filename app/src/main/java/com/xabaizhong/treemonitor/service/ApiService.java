package com.xabaizhong.treemonitor.service;

import com.xabaizhong.treemonitor.entity.HttpResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by admin on 2017/2/28.
 */

public interface ApiService {
    @GET("login.do")
    Observable<HttpResult> login(@Query("username") String name, @Query("password") String pwd);

    @Multipart
    @POST
    Observable<String> uploads(@Url String url, @Body RequestBody body, @Part("part2") RequestBody body2);
}
