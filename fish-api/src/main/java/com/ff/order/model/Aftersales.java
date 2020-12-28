package com.ff.order.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-13
 */
@TableName("tb_aftersales")
public class Aftersales extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="as_id", type= IdType.AUTO)
	private Integer asId;
	@TableField("user_id")
	private Integer userId;
	@TableField("order_id")
	private String orderId;
	@TableField("goods_id")
	private Integer goodsId;
	@TableField("goods_name")
	private String goodsName;
	@TableField("goods_image")
	private String goodsImage;
	@TableField("goods_spec")
	private String goodsSpec;

	@TableField("goods_price")
	private BigDecimal goodsPrice;
	@TableField("goods_qty")
	private Integer goodsQty;
	@TableField("type")
	private String type;
	@TableField("cause")
	private String cause;
	@TableField("mobile")
	private String mobile;
	@TableField("create_time")
	private Date createTime;
	@TableField("status")
	private Integer status;

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public Integer getAsId() {
		return asId;
	}

	public void setAsId(Integer asId) {
		this.asId = asId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
