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
@TableName("tb_shop_order")
public class ShopOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="order_id", type= IdType.AUTO)
	private Integer orderId;
	@TableField("order_sn")
	private String orderSn;
	@TableField("shop_id")
	private Integer shopId;
	private BigDecimal money;
	@TableField("user_id")
	private Integer userId;
	@TableField("create_time")
	private Date createTime;
	@TableField("create_ip")
	private String createIp;
	@TableField("pay_time")
	private Date payTime;
	@TableField("pay_ip")
	private String payIp;
	@TableField("is_pay")
	private Integer isPay;
	@TableField("pay_type")
	private Integer payType;

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	private Integer closed;
	@TableField("payment_method")
	private Integer paymentMethod;

	@TableField("thirdparty_trade_id")
	private String thirdpartyTradeId;

	private Integer integral;

	private BigDecimal allmoney;

	public String getThirdpartyTradeId() {
		return thirdpartyTradeId;
	}

	public void setThirdpartyTradeId(String thirdpartyTradeId) {
		this.thirdpartyTradeId = thirdpartyTradeId;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
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

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
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

	public BigDecimal getAllmoney() {
		return allmoney;
	}

	public void setAllmoney(BigDecimal allmoney) {
		this.allmoney = allmoney;
	}
}
