package com.xabaizhong.treemonistor.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
//        private final static String url = "http://v.juhe.cn/";
        private final static String url = "http://192.168.0.118:8055/";

//    private final static String url = "http://baizhong.applinzi.com/";

    public static Retrofit instance() {
        if (retrofit == null)
            synchronized (RetrofitUtil.class) {
                if (retrofit == null)
                    retrofit = getRetrofit();
            }
        return retrofit;
    }

    /**
     * "http://117.34.115.230:8080/spring/"
     *
     * @return
     */
    private static Retrofit getRetrofit() {
       /* Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);*/

        Gson gson = new GsonBuilder().setLenient().create();/////
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(url)
                .client(client().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                /*.addConverterFactory(SimpleXmlConverterFactory.create(serializer))*/
                .addConverterFactory(GsonConverterFactory.create(gson))
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
            request.newBuilder()
                    .header("Content-Type", "application/soap+xml;charset=UTF-8")//添加请求头
                    .method(request.method(), request.body());

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
