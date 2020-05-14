package com.bsoft.http.https;


import android.text.TextUtils;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


public class HttpsUtils {
    private static String mClientPublicKey;

    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                        if (certs != null && certs.length > 0) {
                            certs[0].checkValidity();
                        } else {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {

                        if (certs != null && certs.length > 0) {
                            certs[0].checkValidity();
                            //校验本地证书和服务证书的公钥和域名是否一致
                            verify(certs[0]);
                        } else {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        }

                    }
                }
        };
    }

    /**
     * 调用cer证书的时候调用
     *
     * @param keyStoreType
     * @return
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLSocketFactory getSSLSocketFactory_Certificate(String keyStoreType, InputStream caInput) throws ClientKeyStoreException {
        try {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            Certificate ca = cf.generateCertificate(caInput);
            caInput.close();

            if (keyStoreType == null || keyStoreType.length() == 0) {
                keyStoreType = KeyStore.getDefaultType();
            }
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            caInput.close();
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, wrappedTrustManagers, null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new ClientKeyStoreException();
        }
    }

    /**
     * 调用BKS证书的时候调用
     *
     * @param keyStoreType
     * @param keyPassword
     * @return
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLSocketFactory getSSLSocketFactory_KeyStore(String keyStoreType, String alias, InputStream caInput, String keyPassword) throws ClientKeyStoreException {
        try {
            // 创建包含可信任证书的keystore
            if (keyStoreType == null || keyStoreType.length() == 0) {
                keyStoreType = KeyStore.getDefaultType();
            }
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            try {
                keyStore.load(caInput, keyPassword.toCharArray());
            } finally {
                caInput.close();
            }
            // creating a TrustManager that trusts the CAs in the KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            //获取本地证书的公钥信息，用于校验服务器返回的证书
            Certificate certificate = keyStore.getCertificate(alias);
            PublicKey publicKey = certificate.getPublicKey();
            mClientPublicKey = publicKey.toString();

            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, wrappedTrustManagers, null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new ClientKeyStoreException();
        }

    }


    private static void verify(X509Certificate cert) throws CertificateNotYetValidException {
        String serverPublickyStr = cert.getPublicKey().toString();
        Principal principal = cert.getSubjectDN();
        String domainNameInfo = principal.getName();
        if (TextUtils.equals(serverPublickyStr, mClientPublicKey) && domainNameInfo.contains("weixinapp.pcscn.org.cn")) {
            return;
        }
        throw new CertificateNotYetValidException();
    }
}
