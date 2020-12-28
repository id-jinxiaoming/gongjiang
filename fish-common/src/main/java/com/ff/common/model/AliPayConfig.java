package com.ff.common.model;

        import java.io.Serializable;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-14 10:00
 **/
public class AliPayConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private String AppId;
    private String AlipayPublicKey;
    private String PrivateKey;
    private String Url;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAlipayPublicKey() {
        return AlipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        AlipayPublicKey = alipayPublicKey;
    }

    public String getPrivateKey() {
        return PrivateKey;
    }

    public void setPrivateKey(String privateKey) {
        PrivateKey = privateKey;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
