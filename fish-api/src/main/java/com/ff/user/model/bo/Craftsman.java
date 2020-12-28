package com.ff.user.model.bo;

import com.ff.user.model.UseredWorkType;

import java.util.List;

//数据返回pojo类
public class Craftsman {

    private Integer id;

    private String photo;

    private String realName;

    private String teamSize;

    private Integer employmentTime;

    private List<UseredWorkType> list;

    private String token;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public Integer getEmploymentTime() {
        return employmentTime;
    }

    public void setEmploymentTime(Integer employmentTime) {
        this.employmentTime = employmentTime;
    }

    public List<UseredWorkType> getList() {
        return list;
    }

    public void setList(List<UseredWorkType> list) {
        this.list = list;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
