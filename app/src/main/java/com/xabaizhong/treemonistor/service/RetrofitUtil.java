package com.xabaizhong.treemonistor.service;

import android.util.Log;

import com.xabaizhong.treemonistor.fragment.Fragment_expert;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by admin on 2017/3/1.
 */

public class RetrofitUtil {
    private static Retrofit retrofit;

    public static Retrofit instance() {
        if (retrofit == null)
            synchronized (RetrofitUtil.class) {
                if (retrofit == null)
                    retrofit = getRetrofit();
            }
        return retrofit;
    }

    private static Retrofit getRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://117.34.115.230:8080/spring/")
                .client(client().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create());

        return builder.build();
    }

    private static OkHttpClient.Builder client() {
        return new OkHttpClient.Builder()

                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor());//日志
    }


    static class LogInterceptor implements Interceptor {
        private String TAG = "okhttp-interceptor";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.v(TAG, "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.v(TAG, String.format(
                    Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i(TAG, "response body:" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}