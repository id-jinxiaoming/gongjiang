package com.ff.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

@TableName("sys_dict")
public class Dict  extends BaseModel {

	private static final long serialVersionUID = 3138060792826655366L;
    @TableId(value = "id")
	private String id;
    @TableField("name")
    private String name;
    @TableField("value")
    private String value;
    @TableField("code")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}