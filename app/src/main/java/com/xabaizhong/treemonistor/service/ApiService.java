package com.xabaizhong.treemonistor.service;

import com.xabaizhong.treemonistor.entity.HttpResult;
import com.xabaizhong.treemonistor.entity.ResultMessage;
import com.xabaizhong.treemonistor.service.entity.Response_news;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    /**
     * news request
     */
    @GET("toutiao/index")
    Observable<Response_news> obtainNews(@Query("type") String type, @Query("key") String key);

    @Multipart
    @POST
    Observable<ResultMessage> treeBaseInfo(@Url String url, @Query("json") String josn, @PartMap Map<String,RequestBody> map);
}
