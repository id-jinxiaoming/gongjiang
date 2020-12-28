package com.ff.shop.model.bo;

import java.util.List;

public class IngenuityWorksBO {

    private Integer ingenuityId;

    private String projectName;

    private String engineeringCost;

    private List<String> list;

    private List<String> imgList;

    public Integer getIngenuityId() {
        return ingenuityId;
    }

    public void setIngenuityId(Integer ingenuityId) {
        this.ingenuityId = ingenuityId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEngineeringCost() {
        return engineeringCost;
    }

    public void setEngineeringCost(String engineeringCost) {
        this.engineeringCost = engineeringCost;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
