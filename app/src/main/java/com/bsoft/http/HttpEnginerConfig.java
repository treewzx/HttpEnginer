package com.bsoft.http;

import android.app.Application;

import com.bsoft.http.https.KeyStoreInfo;
import com.bsoft.http.parser.DefaultCallbackParser;
import com.bsoft.http.parser.ICallbackParser;
import com.bsoft.http.request.IHttpRequest;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public final class HttpEnginerConfig {

    public static final long DEFAULT_SECONDS = 15;      //默认的超时时间
    final IHttpRequest httpRequest;
    final String baseUrl;
    final boolean isDebug;
    final long readTimeout;
    final long writeTimeout;
    final long connectTimeout;
    final TimeUnit timeoutUnit;
    final ICallbackParser parser;
    final MediaType mediaType;
    final InputStream certInputStream;
    final Application context;
    final KeyStoreInfo keyStoreInfo;

    public enum MediaType {
        FORM, JSON
    }

    private HttpEnginerConfig(Builder builder) {
        httpRequest = builder.mHttpRequest;
        baseUrl = builder.mBaseUrl;
        isDebug = builder.mIsDebug;
        readTimeout = builder.mReadTimeout;
        writeTimeout = builder.mWriteTimeout;
        connectTimeout = builder.mConnectTimeout;
        timeoutUnit = builder.mTimeoutUnit;
        parser = builder.mParser;
        mediaType = builder.mMediaType;
        certInputStream = builder.mCertInputStream;
        context = builder.mContext;
        keyStoreInfo = builder.mKeyStoreInfo;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public IHttpRequest getHttpRequest() {
        return httpRequest;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public TimeUnit getTimeoutUnit() {
        return timeoutUnit;
    }

    public ICallbackParser getCallbackParser() {
        return parser;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public InputStream getCertInputStream() {
        return certInputStream;
    }

    public KeyStoreInfo getKeyStoreInfo() {
        return keyStoreInfo;
    }


    public Application getContext() {
        return context;
    }


    public Builder newBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private IHttpRequest mHttpRequest;
        private String mBaseUrl;
        private boolean mIsDebug;
        private long mReadTimeout;
        private long mWriteTimeout;
        private long mConnectTimeout;
        private TimeUnit mTimeoutUnit;
        private ICallbackParser mParser;
        private MediaType mMediaType;
        private InputStream mCertInputStream;
        private Application mContext;
        private KeyStoreInfo mKeyStoreInfo;



        public Builder() {

        }

        public Builder(HttpEnginerConfig httpEnginerConfig) {
            mHttpRequest = httpEnginerConfig.httpRequest;
            mBaseUrl = httpEnginerConfig.baseUrl;
            mIsDebug = httpEnginerConfig.isDebug;
            mReadTimeout = httpEnginerConfig.readTimeout;
            mWriteTimeout = httpEnginerConfig.writeTimeout;
            mConnectTimeout = httpEnginerConfig.connectTimeout;
            mTimeoutUnit = httpEnginerConfig.timeoutUnit;
            mParser = httpEnginerConfig.parser;
            mCertInputStream = httpEnginerConfig.certInputStream;
            mContext = httpEnginerConfig.context;
        }


        public Builder httpRequest(IHttpRequest httpRequest) {
            this.mHttpRequest = httpRequest;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.mBaseUrl = baseUrl;
            return this;
        }

        public Builder addCertRes(InputStream certInputStream) {
            this.mCertInputStream = certInputStream;
            return this;
        }
        public Builder addKeyStore(KeyStoreInfo keyStoreInfo) {
            this.mKeyStoreInfo = keyStoreInfo;
            return this;
        }

        public Builder context(Application context){
            this.mContext = context;
            return this;
        }

        public Builder debug(boolean isDebug) {
            this.mIsDebug = isDebug;
            return this;
        }

        public Builder readTimeOut(long readTimeout) {
            this.mReadTimeout = readTimeout;
            return this;
        }

        public Builder writeTimeout(long writeTimeout) {
            this.mWriteTimeout = writeTimeout;
            return this;
        }

        public Builder connectTimeout(long connectTimeout) {
            this.mConnectTimeout = connectTimeout;
            return this;
        }

        public Builder timeoutUnit(TimeUnit timeoutUnit) {
            this.mTimeoutUnit = timeoutUnit;
            return this;
        }

        public Builder parser(ICallbackParser parser) {
            this.mParser = parser;
            return this;
        }

        public Builder mediaType(MediaType mediaType) {
            this.mMediaType = mediaType;
            return this;
        }


        public HttpEnginerConfig build() {
            if (mReadTimeout <= 0) {
                readTimeOut(DEFAULT_SECONDS);
            }
            if (mWriteTimeout <= 0) {
                writeTimeout(DEFAULT_SECONDS);
            }
            if (mConnectTimeout <= 0) {
                connectTimeout(DEFAULT_SECONDS);
            }
            if (mTimeoutUnit == null) {
                timeoutUnit(TimeUnit.SECONDS);
            }
            if (mParser == null) {
                parser(new DefaultCallbackParser());
            }
            if (mMediaType == null) {
                mMediaType = MediaType.JSON;
            }
            return new HttpEnginerConfig(this);
        }


    }
}
