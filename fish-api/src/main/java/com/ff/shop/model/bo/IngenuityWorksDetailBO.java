package com.ff.shop.model.bo;

import com.ff.shop.model.IngenuityWorks;
import com.ff.user.model.bo.Craftsman;

import java.util.List;

public class IngenuityWorksDetailBO extends IngenuityWorks {

    private List<Craftsman> list;

    private List<String> imgList;

    public List<Craftsman> getList() {
        return list;
    }

    public void setList(List<Craftsman> list) {
        this.list = list;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
