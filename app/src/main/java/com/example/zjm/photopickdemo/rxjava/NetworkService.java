package com.example.zjm.photopickdemo.rxjava;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zjm on 16-10-27.
 */

public interface NetworkService {
    String BASE_URL = "http://api.heweather.com";

    class Factory{
        public static final NetworkService create(){
            HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client=new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            return chain.proceed(chain.request());
                        }
                    })
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(NetworkService.class);
        }
    }
    @GET("x3/weather")
    Observable<HeWeatherBean> getWeatherService(@Query("city") String city, @Query("key") String key);

    @GET("x3/attractions")
    Observable<HeWeatherBean> getScenicService(@Query("cityid") String cityid, @Query("key") String key);
}
