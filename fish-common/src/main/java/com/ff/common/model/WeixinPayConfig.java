package com.ff.common.model;

import java.io.Serializable;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-14 10:00
 **/
public class WeixinPayConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private String AppId;
    private String MchId;
    private String PaternerKey;

    private String Url;


    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getMchId() {
        return MchId;
    }

    public void setMchId(String mchId) {
        MchId = mchId;
    }

    public String getPaternerKey() {
        return PaternerKey;
    }

    public void setPaternerKey(String paternerKey) {
        PaternerKey = paternerKey;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
