package com.ff.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

@TableName("sys_role_resources")
public class RoleResources  extends BaseModel {

	private static final long serialVersionUID = 2814639517974791520L;
    @TableId(value = "id")
	private String id;
    @TableField("resources_id")
    private String resourcesId;
    @TableField("role_id")
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId == null ? null : resourcesId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}