package com.ff.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yzx on 2017/5/16.
 */
public class Kuaidi100Data implements Serializable {

    private String message;
    private String nu;
    private Integer ischeck;
    private String condition;
    private String com;
    private Integer status;
    private Integer state;
    private String comcontact;
    private String comurl;
    private List<KuaidiInfo> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public Integer getIscheck() {
        return ischeck;
    }

    public void setIscheck(Integer ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getComcontact() {
        return comcontact;
    }

    public void setComcontact(String comcontact) {
        this.comcontact = comcontact;
    }

    public String getComurl() {
        return comurl;
    }

    public void setComurl(String comurl) {
        this.comurl = comurl;
    }

    public List<KuaidiInfo> getData() {
        return data;
    }

    public void setData(List<KuaidiInfo> data) {
        this.data = data;
    }
}

 class KuaidiInfo implements Serializable {

    private String time;
    private String context;
     private String location;

     public String getTime() {
         return time;
     }

     public void setTime(String time) {
         this.time = time;
     }

     public String getContext() {
         return context;
     }

     public void setContext(String context) {
         this.context = context;
     }

     public String getLocation() {
         return location;
     }

     public void setLocation(String location) {
         this.location = location;
     }
 }
