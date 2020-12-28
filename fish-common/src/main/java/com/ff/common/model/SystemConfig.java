package com.ff.common.model;

import java.io.Serializable;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-14 10:00
 **/
public class SystemConfig implements Serializable {

    private String android;

    private String ios;

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    private Integer isIntegralToFreight;//积分抵扣运费 1  是 0 否

    private String kuaidi100Key;
    private Integer upload;//上传方式

    private String serviceInfo;//服务信息

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public Integer getIsIntegralToFreight() {
        return isIntegralToFreight;
    }

    public void setIsIntegralToFreight(Integer isIntegralToFreight) {
        this.isIntegralToFreight = isIntegralToFreight;
    }

    public String getKuaidi100Key() {
        return kuaidi100Key;
    }

    public void setKuaidi100Key(String kuaidi100Key) {
        this.kuaidi100Key = kuaidi100Key;
    }

    public Integer getUpload() {
        return upload;
    }

    public void setUpload(Integer upload) {
        this.upload = upload;
    }
}
