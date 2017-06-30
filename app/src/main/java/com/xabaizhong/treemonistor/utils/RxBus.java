package com.xabaizhong.treemonistor.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by admin on 2017/2/28.
 */

/**
 * 自己封装的rxbus
 */
public class RxBus {

    private static volatile RxBus mDefaultInstance;

    private RxBus() {
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    private final Subject<Object> _bus = PublishSubject.create().toSerialized();

    public void send(Object o) {
        _bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> classType) {
        return _bus.ofType(classType);
    }
}
