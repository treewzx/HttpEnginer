package com.bsoft.http.https;

import java.io.InputStream;
import java.security.PublicKey;

public class KeyStoreInfo {
    public String storeType;
    public String alias;
    public String storePwd;
    public InputStream keyFileIs;

    public KeyStoreInfo(String keyStoreType, String alias, String storePwd, InputStream is) {
        this.storeType = keyStoreType;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyFileIs = is;
    }
}
