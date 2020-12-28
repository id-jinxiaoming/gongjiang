package com.ff.common.model;

import java.io.Serializable;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-14 10:00
 **/
public class SmsConfig implements Serializable {
    /**
     * 扩展实体
     */
    private static final long serialVersionUID = 1L;
    private String key;//应用的app key
    private String secret;//应用的app secret
    private String signName;//应用签名
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSignName() {
        return signName;
    }
    public void setSignName(String signName) {
        this.signName = signName;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
