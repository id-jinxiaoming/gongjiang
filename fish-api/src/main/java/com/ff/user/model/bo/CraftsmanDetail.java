package com.ff.user.model.bo;

import com.ff.user.model.UseredExperience;

import java.util.List;

public class CraftsmanDetail{

    private Integer id;
    private String token;
    private String realName;
    private String hometown;
    private String avatar;
    private String idCard;
    private Integer employmentTime;
    private Integer whether;//0显示1不显示
    private String qrCode;
    private String postCertificate;
    private List<UseredExperience> userExperiences;
    private List<String> userLabelList;//用户标签
    private List<String> userWorkTypeList;//团队工种

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getEmploymentTime() {
        return employmentTime;
    }

    public void setEmploymentTime(Integer employmentTime) {
        this.employmentTime = employmentTime;
    }

    public Integer getWhether() {
        return whether;
    }

    public void setWhether(Integer whether) {
        this.whether = whether;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getPostCertificate() {
        return postCertificate;
    }

    public void setPostCertificate(String postCertificate) {
        this.postCertificate = postCertificate;
    }

    public List<UseredExperience> getUserExperiences() {
        return userExperiences;
    }

    public void setUserExperiences(List<UseredExperience> userExperiences) {
        this.userExperiences = userExperiences;
    }

    public List<String> getUserLabelList() {
        return userLabelList;
    }

    public void setUserLabelList(List<String> userLabelList) {
        this.userLabelList = userLabelList;
    }

    public List<String> getUserWorkTypeList() {
        return userWorkTypeList;
    }

    public void setUserWorkTypeList(List<String> userWorkTypeList) {
        this.userWorkTypeList = userWorkTypeList;
    }
}
