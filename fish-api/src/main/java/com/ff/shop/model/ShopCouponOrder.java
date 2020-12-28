package com.ff.shop.model;

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
 * @since 2017-09-26
 */
@TableName("tb_shop_coupon_order")
public class ShopCouponOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="order_id", type= IdType.AUTO)
	private Integer orderId;
	@TableField("order_sn")
	private String orderSn;
	@TableField("shop_id")
	private Integer shopId;
	@TableField("coupon_id")
	private Integer couponId;
	private BigDecimal money;
	@TableField("total_money")
	private BigDecimal totalMoney;
	@TableField("payment_method")
	private Integer paymentMethod;
	@TableField("user_id")
	private Integer userId;
	@TableField("create_time")
	private Date createTime;
	@TableField("pay_type")
	private Integer payType;
	@TableField("pay_time")
	private Date payTime;
	@TableField("pay_ip")
	private String payIp;
	@TableField("is_pay")
	private Integer isPay;
	@TableField("thirdparty_trade_id")
	private String thirdpartyTradeId;
	private Integer closed;
	private Integer integral;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayIp() {
		return payIp;
	}

	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public String getThirdpartyTradeId() {
		return thirdpartyTradeId;
	}

	public void setThirdpartyTradeId(String thirdpartyTradeId) {
		this.thirdpartyTradeId = thirdpartyTradeId;
	}

	public Integer getClosed() {
		return closed;
	}

	public void setClosed(Integer closed) {
		this.closed = closed;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
}
