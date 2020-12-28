package com.ff.order.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-13
 */
@TableName("tb_order")
public class Order extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("order_id")
	private String orderId;
	@TableField("user_id")
	private Integer userId;
	@TableField("shipping_method")
	private Integer shippingMethod;
	@TableField("payment_method")
	private Integer paymentMethod;
	@TableField("order_status")
	private Integer orderStatus;
	@TableField("goods_amount")
	private BigDecimal goodsAmount;
	@TableField("shipping_amount")
	private BigDecimal shippingAmount;

	@TableField("coupon_money")
	private BigDecimal couponMoney;
	@TableField("order_amount")
	private BigDecimal orderAmount;
	private String memos;
	@TableField("created_date")
	private Long createdDate;
	@TableField("payment_date")
	private Long paymentDate;
	@TableField("thirdparty_trade_id")
	private String thirdpartyTradeId;

	private Integer integral;

	@TableField("rebate_integral")
	private Integer rebateIntegral;

	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public Integer getRebateIntegral() {
		return rebateIntegral;
	}

	public void setRebateIntegral(Integer rebateIntegral) {
		this.rebateIntegral = rebateIntegral;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}

	public Long getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Long paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(Integer shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public BigDecimal getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(BigDecimal shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getMemos() {
		return memos;
	}

	public void setMemos(String memos) {
		this.memos = memos;
	}



	public String getThirdpartyTradeId() {
		return thirdpartyTradeId;
	}

	public void setThirdpartyTradeId(String thirdpartyTradeId) {
		this.thirdpartyTradeId = thirdpartyTradeId;
	}


	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
}
