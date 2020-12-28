package com.ff.common.model;

import java.io.Serializable;


/**
 *  极光key
 */
public class JPushConfig implements Serializable {

    private String appKey;
    private String masterSecret;

    public JPushConfig() {
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
