package com.ff.shop.model.ex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ShipingParams implements Serializable {

    private String type;//fixed weight piece
    private String[] area;
    private BigDecimal charges;//固件费用
    private BigDecimal firstCharges;//首次费用
    private BigDecimal addCharges;//续重费用


    private BigDecimal firstWeight; //首次重量
    private BigDecimal addWeight;//续重重量

    private Integer firstPiece;//首次件数
    private Integer addPiece;//续增件数

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    public BigDecimal getFirstCharges() {
        return firstCharges;
    }

    public void setFirstCharges(BigDecimal firstCharges) {
        this.firstCharges = firstCharges;
    }

    public BigDecimal getAddCharges() {
        return addCharges;
    }

    public void setAddCharges(BigDecimal addCharges) {
        this.addCharges = addCharges;
    }

    public BigDecimal getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(BigDecimal firstWeight) {
        this.firstWeight = firstWeight;
    }

    public BigDecimal getAddWeight() {
        return addWeight;
    }

    public void setAddWeight(BigDecimal addWeight) {
        this.addWeight = addWeight;
    }

    public Integer getFirstPiece() {
        return firstPiece;
    }

    public void setFirstPiece(Integer firstPiece) {
        this.firstPiece = firstPiece;
    }

    public Integer getAddPiece() {
        return addPiece;
    }

    public void setAddPiece(Integer addPiece) {
        this.addPiece = addPiece;
    }
}
