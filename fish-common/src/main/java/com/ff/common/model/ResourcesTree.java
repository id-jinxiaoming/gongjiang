package com.ff.common.model;

import java.io.Serializable;
import java.util.List;

public class ResourcesTree implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;       
    public String pid;     
    public String name;
    public String url;
    public String permission;
    public String icon;
    public String type;
    public String sort;

    public List<ResourcesTree> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<ResourcesTree> getChildren() {
        return children;
    }

    public void setChildren(List<ResourcesTree> children) {
        this.children = children;
    }
}