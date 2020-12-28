package com.ff.shop.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

@TableName("tb_ingenuity_usered")
public class IngenuityUsered extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("usered_id")
    private Integer useredId;

    @TableField("ingenuity_id")
    private Integer ingenuityId;

    @TableField("usered_name")
    private String useredName;

    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUseredId() {
        return useredId;
    }

    public void setUseredId(Integer useredId) {
        this.useredId = useredId;
    }

    public Integer getIngenuityId() {
        return ingenuityId;
    }

    public void setIngenuityId(Integer ingenuityId) {
        this.ingenuityId = ingenuityId;
    }

    public String getUseredName() {
        return useredName;
    }

    public void setUseredName(String useredName) {
        this.useredName = useredName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

