package com.ff.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

@TableName("tb_authentication")
public class Authentication extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("authenticator_id")
    private Integer authenticatorId;

    @TableField("authenticated_person_id")
    private Integer authenticatedPersonId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthenticatorId() {
        return authenticatorId;
    }

    public void setAuthenticatorId(Integer authenticatorId) {
        this.authenticatorId = authenticatorId;
    }

    public Integer getAuthenticatedPersonId() {
        return authenticatedPersonId;
    }

    public void setAuthenticatedPersonId(Integer authenticatedPersonId) {
        this.authenticatedPersonId = authenticatedPersonId;
    }
}
