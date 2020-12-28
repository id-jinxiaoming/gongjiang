package com.ff.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

import java.math.BigInteger;
import java.util.Date;

@TableName("tb_user")
public class Usered extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String username;

    @TableField("real_name")
    private String realName;

    private String avatar;

    private String hometown;

    private String card;

    @TableField("employment_time")
    private Integer employmentTime;

    private Integer whether;

    @TableField("qr_code")
    private String qrCode;

    @TableField("post_certificate")
    private String postCertificate;

    @TableField("team_size")
    private String teamSize;

    private Integer integral;

    @TableField("foreman_status")
    private Integer foremanStatus;

    @TableField("team_information_status")
    private Integer teamInformationStatus;

    private String token;

    @TableField("user_status")
    private Integer userStatus;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date upateTime;

    @TableField("invited_by")
    private String invitedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getForemanStatus() {
        return foremanStatus;
    }

    public void setForemanStatus(Integer foremanStatus) {
        this.foremanStatus = foremanStatus;
    }

    public Integer getTeamInformationStatus() {
        return teamInformationStatus;
    }

    public void setTeamInformationStatus(Integer teamInformationStatus) {
        this.teamInformationStatus = teamInformationStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpateTime() {
        return upateTime;
    }

    public void setUpateTime(Date upateTime) {
        this.upateTime = upateTime;
    }

    public String getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }
}
