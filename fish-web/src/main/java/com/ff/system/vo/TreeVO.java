package com.ff.system.vo;

/**
 * @Author yuzhongxin
 * @Description
 * @Create 2017-07-14 1:27
 **/
public class TreeVO {
    private String id;
    private String pId;
    private String name;
    private Integer checked;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getChecked() {
        return checked;
    }
    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
