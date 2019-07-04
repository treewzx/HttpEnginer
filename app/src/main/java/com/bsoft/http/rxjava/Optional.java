package com.bsoft.http.rxjava;

import java.util.NoSuchElementException;

import io.reactivex.annotations.Nullable;

public class Optional<T> {

    private final T value; // 接收到的返回结果

    private Optional(@Nullable T value) {
        this.value = value;
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    // 判断返回结果是否为null
    public boolean isEmpty() {
        return this.value == null;
    }

    // 获取不能为null的返回结果，如果为null，直接抛异常，经过二次封装之后，这个异常走向RxJava的onError()
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    // 获取可以为null的返回结果
    public T getIncludeNull() {
        return value;
    }
}
