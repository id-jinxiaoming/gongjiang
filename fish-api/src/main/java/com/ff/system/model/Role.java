package com.ff.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

@TableName("sys_role")
public class Role  extends BaseModel {

	private static final long serialVersionUID = 3582588209589180635L;
    @TableId(value = "id")
	private String id;
    @TableField("name")
    private String name;
    @TableField("type")
    private String type;
   

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
}