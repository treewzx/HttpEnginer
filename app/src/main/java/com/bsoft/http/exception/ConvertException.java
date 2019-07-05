package com.bsoft.http.exception;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/10.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class ConvertException extends Exception{


    private final String mMessage;

    public ConvertException( String message) {
        this.mMessage = message;
    }


    @Override
    public String getMessage() {
        return mMessage;
    }
}
