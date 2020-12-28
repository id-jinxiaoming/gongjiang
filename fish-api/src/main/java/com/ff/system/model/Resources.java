package com.ff.system.model;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;
@TableName("sys_resources")
public class Resources extends BaseModel{

	private static final long serialVersionUID = 4125580367922291000L;
	@TableId(value = "id")
	private String id;
	@TableField("pid")
    private String pid;
	@TableField("name")
    private String name;
	@TableField("url")
    private String url;
	@TableField("type")
    private String type;
	@TableField("sort")
    private String sort;
	@TableField("permission")
    private String permission;
	@TableField("icon")
    private String icon;
    
    public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		 this.type = type == null ? null : type.trim();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		 this.sort = sort == null ? null : sort.trim();	
	}
}