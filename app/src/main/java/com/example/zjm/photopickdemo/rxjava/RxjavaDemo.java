package com.example.zjm.photopickdemo.rxjava;


import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zjm on 16-10-17.
 */

public class RxjavaDemo {
    private final static String tag = RxjavaDemo.class.getSimpleName();
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });

    public void ss() {
        observable.subscribe(observer);
//        observable.subscribe(subscriber);
    }
    public void getName(){
        final String[] name={"xxx","sss","aaa","qqq"};
        Observable.from(name)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(tag,s);
                    }
                });

    }


}
