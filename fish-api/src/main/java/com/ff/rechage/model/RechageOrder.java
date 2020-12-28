package com.ff.rechage.model;

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
 * @since 2017-10-07
 */
@TableName("tb_rechage_order")
public class RechageOrder extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="order_id", type= IdType.AUTO)
	private Integer orderId;
	@TableField("order_sn")
	private String orderSn;
	@TableField("rechage_id")
	private Integer rechageId;
	@TableField("user_id")
	private Integer userId;
	private String title;
	private BigDecimal money;
	private BigDecimal coupon;
	@TableField("is_pay")
	private Integer isPay;
	@TableField("pay_time")
	private Date payTime;
	@TableField("create_time")
	private Date createTime;

	@TableField("pay_ip")
	private String payIp;

	@TableField("create_ip")
	private String createIp;

	@TableField("payment_method")
	private Integer paymentMethod;

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@TableField("thirdparty_trade_id")
	private String thirdpartyTradeId;

	public String getThirdpartyTradeId() {
		return thirdpartyTradeId;
	}

	public void setThirdpartyTradeId(String thirdpartyTradeId) {
		this.thirdpartyTradeId = thirdpartyTradeId;
	}

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

	public Integer getRechageId() {
		return rechageId;
	}

	public void setRechageId(Integer rechageId) {
		this.rechageId = rechageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getCoupon() {
		return coupon;
	}

	public void setCoupon(BigDecimal coupon) {
		this.coupon = coupon;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
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

	public String getPayIp() {
		return payIp;
	}

	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}
}
