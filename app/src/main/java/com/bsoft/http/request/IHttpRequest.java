package com.bsoft.http.request;

import android.content.Context;


import com.bsoft.http.HttpEnginerConfig;
import com.bsoft.http.converter.IConverter;
import com.bsoft.http.entity.ProgressInfo;
import com.bsoft.http.httpcallback.IHttpCallback;
import com.bsoft.http.request.listener.OnLoadProgressListener;
import com.bsoft.http.rxjava.Optional;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public interface IHttpRequest {

    /**********************************Get请求*****************************************/

    void get(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback);

    Observable<String> get(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params);

    <T> Observable<Optional<T>> get(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IConverter<T> converter);


    /***********************************Post请求***************************************/

    void post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback);

    Observable<String> post(final HttpEnginerConfig config, final String url, final Map<String, String> headers, final Map<String, String> params);

    //<T> Observable<T> post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IConverter<T> converter);
    //采用Optional<T>是因为RxJava2已不允许发Null事件，操作符都会在发送前判断发送内容是否为null，如果是就是抛出NPE，走onError，所以对其包裹一层，以便正常传递
    <T> Observable<Optional<T>> post(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IConverter<T> converter);

    //异步请求，回调在主线程执行
    <T> Observable<Optional<T>> postAsync(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IConverter<T> converter);

    /*****************************************下载文件*************************************/

    void downloadAsync(Context context, HttpEnginerConfig config, String url, String saveRootPath, @Nullable String fileName, @Nullable OnLoadProgressListener listener);

    Observable<ProgressInfo> download(Context context, HttpEnginerConfig config, String url, String saveRootPath, @Nullable String fileName);


    /********************************************上传文件*************************************/

    void uploadAsync(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, IHttpCallback callback, @Nullable OnLoadProgressListener listener, File... files);

    Observable<ProgressInfo> upload(HttpEnginerConfig config, String url, Map<String, String> headers, Map<String, String> params, File... files);


}
