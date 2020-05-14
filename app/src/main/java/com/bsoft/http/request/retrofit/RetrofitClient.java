package com.bsoft.http.request.retrofit;


import android.app.Application;

import com.bsoft.http.HttpEnginerConfig;
import com.bsoft.http.https.ClientKeyStoreException;
import com.bsoft.http.https.HttpsUtils;
import com.bsoft.http.https.KeyStoreInfo;
import com.bsoft.http.request.retrofit.interceptor.NullInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/3/11.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class RetrofitClient {
    private static ServiceApi mServiceApi;

    public static ServiceApi getServiceApi(HttpEnginerConfig config) {
        if (mServiceApi == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .readTimeout(config.getReadTimeout(), config.getTimeoutUnit())
                    .writeTimeout(config.getWriteTimeout(), config.getTimeoutUnit())
                    .connectTimeout(config.getConnectTimeout(), config.getTimeoutUnit())
                    .addNetworkInterceptor((config.isDebug()) ? (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) : new NullInterceptor());
            if (config.getBaseUrl().startsWith("https")) {
                SSLSocketFactory sslSocketFactory = null;
                if (config.getKeyStoreInfo() != null) {
                    KeyStoreInfo keyStoreInfo = config.getKeyStoreInfo();
                    try {
                        sslSocketFactory = HttpsUtils.getSSLSocketFactory_KeyStore(keyStoreInfo.storeType, keyStoreInfo.alias, keyStoreInfo.keyFileIs, keyStoreInfo.storePwd);
                    } catch (ClientKeyStoreException e) {
                        e.printStackTrace();
                    }
                } else if (config.getCertInputStream() != null) {

                    try {
                        sslSocketFactory = HttpsUtils.getSSLSocketFactory_Certificate(null, config.getCertInputStream());
                    } catch (ClientKeyStoreException e) {
                        e.printStackTrace();
                    }
                }
                if (sslSocketFactory != null) {
                    okHttpClientBuilder.sslSocketFactory(sslSocketFactory);
                }

            }
            OkHttpClient okHttpClient = okHttpClientBuilder.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(config.getBaseUrl())
                    .client(okHttpClient)
                    .build();

            mServiceApi = retrofit.create(ServiceApi.class);
        }
        return mServiceApi;
    }


}
