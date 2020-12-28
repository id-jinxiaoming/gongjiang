package com.ff.users.model;

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
@TableName("tb_users_coupon")
public class UsersCoupon extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="coupon_id", type= IdType.AUTO)
	private Integer couponId;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 充值套餐ID
     */
	@TableField("order_id")
	private Integer orderId;
    /**
     * 金额
     */
	private BigDecimal money;
    /**
     * 0不可用 1 可用
     */
	private Integer status;
    /**
     * 是否使用
     */
	@TableField("is_used")
	private Integer isUsed;
    /**
     * 使用时间
     */
	@TableField("use_time")
	private Date useTime;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	@TableField("shop_id")
	private Integer shopId;


	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}


}
