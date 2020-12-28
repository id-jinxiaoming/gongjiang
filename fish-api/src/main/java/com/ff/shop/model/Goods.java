package com.ff.shop.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;


/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-07-25
 */
@TableName("tb_goods")
public class Goods extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="goods_id", type= IdType.AUTO)
	private Integer goodsId;
	@TableField("cate_id")
	private Integer cateId;
	@TableField("brand_id")
	private Integer brandId;
	@TableField("goods_name")
	private String goodsName;
	@TableField("goods_sn")
	private String goodsSn;
	@TableField("now_price")
	private BigDecimal nowPrice;
	@TableField("original_price")
	private BigDecimal originalPrice;
	@TableField("goods_image")
	private String goodsImage;
	@TableField("goods_brief")
	private String goodsBrief;
	@TableField("goods_content")
	private String goodsContent;
	@TableField("goods_weight")
	private BigDecimal goodsWeight;
	@TableField("stock_qty")
	private Integer stockQty;
	@TableField("meta_keywords")
	private String metaKeywords;
	@TableField("meta_description")
	private String metaDescription;
	@TableField("created_date")
	private long createdDate;
	private Integer newarrival=0;
	private Integer recommend=0;
	private Integer bargain=0;
	private Integer status;


	private Integer integral;


	@TableField("rebate_integral")
	private Integer rebateIntegral;
	public Integer getRebateIntegral() {
		return rebateIntegral;
	}

	public void setRebateIntegral(Integer rebateIntegral) {
		this.rebateIntegral = rebateIntegral;
	}


	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public BigDecimal getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(BigDecimal nowPrice) {
		this.nowPrice = nowPrice;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getGoodsBrief() {
		return goodsBrief;
	}

	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getNewarrival() {
		return newarrival;
	}

	public void setNewarrival(Integer newarrival) {
		this.newarrival = newarrival;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getBargain() {
		return bargain;
	}

	public void setBargain(Integer bargain) {
		this.bargain = bargain;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
}
