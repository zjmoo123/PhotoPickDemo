package com.example.zjm.photopickdemo.rxjava;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.lunxun.PollingService;
import com.example.zjm.photopickdemo.lunxun.PollingUtils;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.internal.schedulers.NewThreadWorker;
import rx.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {
    private final static String tag = RxjavaActivity.class.getSimpleName();
    private ImageView rxjavaImg;
    private int drawableRes = R.mipmap.bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        //Start polling service
        System.out.println("Start polling service...");
        PollingUtils.startPollingService(this, 90, PollingService.class, PollingService.ACTION);
        rxjavaImg = (ImageView) findViewById(R.id.rxjava_image);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxjavaActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                Logger.d("ERROR!");
            }

            @Override
            public void onNext(Drawable drawable) {
                rxjavaImg.setImageDrawable(drawable);
            }
        });

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(tag, "number: " + integer);
                    }
                });
        NetworkRequest.getInstance().getScenic("北京", new Action1() {
            @Override
            public void call(Object o) {
                HeWeatherBean heWeatherBean=(HeWeatherBean)o;
                Log.d("networkrequest",heWeatherBean.toString());
            }
        });
        NetworkRequest.getService().getWeatherService("北京",NetworkRequest.HEFENG_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<HeWeatherBean>() {
                    @Override
                    public void call(HeWeatherBean heWeatherBean) {
                        Log.d("networkrequest",heWeatherBean.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HeWeatherBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("networkrequest","请求失败");
                    }

                    @Override
                    public void onNext(HeWeatherBean heWeatherBean) {
                        Logger.d("networkrequest  %s","请求成功");
                        Logger.e("hello");
                        Logger.w("hello");
                        Logger.v("hello");
                        Logger.wtf("hello");
                        Logger.t("   nihao ceshi   ").d("hello");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop polling service
        System.out.println("Stop polling service...");
        PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
    }
}
