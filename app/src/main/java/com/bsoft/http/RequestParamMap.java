package com.bsoft.http;

import java.util.Map;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/15.
 * Description:对网络请求头和请求参数集中起来，主要用于添加公共请求头和参数的时候使用
 * PS: Not easy to write code, please indicate.
 */
public class RequestParamMap {
    private Map<String, Object> mParams;
    private Map<String, String> mHeaders;

    public RequestParamMap(Map<String, String> headers, Map<String, Object> params) {
        this.mParams = params;
        this.mHeaders = headers;
    }

    public Map<String, Object> getParams() {
        return mParams;
    }

    public void setParams(Map<String, Object> params) {
        this.mParams = params;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }

}
