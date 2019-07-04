package com.bsoft.http.rxjava;

import android.util.Log;


import com.bsoft.http.exception.ApiException;
import com.bsoft.http.exception.ExceptionEnginer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public abstract class BaseObserver<T> implements Observer<Optional<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Optional<T> tOptional) {
        onSuccess(tOptional.getIncludeNull());
    }

    @Override
    public void onError(Throwable e) {
        onError(ExceptionEnginer.handleException(e));
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);
    
    public abstract void onError(ApiException exception);

}
