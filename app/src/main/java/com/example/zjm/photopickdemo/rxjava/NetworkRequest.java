package com.example.zjm.photopickdemo.rxjava;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zjm on 16-10-27.
 */

public class NetworkRequest {
    public static final String HEFENG_KEY = "7e98176448394ae89cb4046842208fd1";

    private static class NetworkRequestSingleonHolder {
        private static final NetworkRequest instance = new NetworkRequest();
        private static final NetworkService service = NetworkService.Factory.create();
    }

    public static final NetworkRequest getInstance() {
        return NetworkRequestSingleonHolder.instance;
    }

    public static final NetworkService getService() {
        return NetworkRequestSingleonHolder.service;
    }

    private class ComposeThread<T> implements Observable.Transformer<T, T> {

        @Override
        public Observable<T> call(Observable<T> tObservable) {
            return tObservable
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public void getWeather(String city, Action1 onNext) {
        getService().getWeatherService(city, HEFENG_KEY).compose(new ComposeThread<HeWeatherBean>()).subscribe(onNext);
    }

    public void getScenic(String cityid, Action1 onNext) {
        getService().getScenicService(cityid, HEFENG_KEY).compose(new ComposeThread<HeWeatherBean>()).subscribe(onNext);
    }
}
