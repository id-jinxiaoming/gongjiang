package com.ff.user.model.bo;


import com.ff.user.model.UseredExperience;
import com.ff.user.model.UseredLabel;
import com.ff.user.model.UseredPhoto;
import com.ff.user.model.UseredWorkType;

import java.util.List;
import java.util.Map;

public class UserSingle {

    private Integer id;

    private String avatar;

    private String realName;

    //信用评价1234567
    private double creditRating;

    private String hometown;

    private String telephone;

    private String qrCode;

    private List<UseredLabel> useredLabelList;

    private Integer employmentTime;

    private Integer projectNumber;

    private String teamSize;

    private List<UseredWorkType> useredWorkTypeList;

    private String postCertificate;

    private List<UseredExperience> useredExperienceList;

    private List<StrengthCraftsman> strengthCraftsmanList;

    private List<UseredPhoto> useredPhotoList;

    //工长详情业绩列表 Todo
    private Map<String ,Object> map;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public double getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(double creditRating) {
        this.creditRating = creditRating;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public List<UseredLabel> getUseredLabelList() {
        return useredLabelList;
    }

    public void setUseredLabelList(List<UseredLabel> useredLabelList) {
        this.useredLabelList = useredLabelList;
    }

    public Integer getEmploymentTime() {
        return employmentTime;
    }

    public void setEmploymentTime(Integer employmentTime) {
        this.employmentTime = employmentTime;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public List<UseredWorkType> getUseredWorkTypeList() {
        return useredWorkTypeList;
    }

    public void setUseredWorkTypeList(List<UseredWorkType> useredWorkTypeList) {
        this.useredWorkTypeList = useredWorkTypeList;
    }

    public String getPostCertificate() {
        return postCertificate;
    }

    public void setPostCertificate(String postCertificate) {
        this.postCertificate = postCertificate;
    }

    public List<UseredExperience> getUseredExperienceList() {
        return useredExperienceList;
    }

    public void setUseredExperienceList(List<UseredExperience> useredExperienceList) {
        this.useredExperienceList = useredExperienceList;
    }

    public List<StrengthCraftsman> getStrengthCraftsmanList() {
        return strengthCraftsmanList;
    }

    public void setStrengthCraftsmanList(List<StrengthCraftsman> strengthCraftsmanList) {
        this.strengthCraftsmanList = strengthCraftsmanList;
    }

    public List<UseredPhoto> getUseredPhotoList() {
        return useredPhotoList;
    }

    public void setUseredPhotoList(List<UseredPhoto> useredPhotoList) {
        this.useredPhotoList = useredPhotoList;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
