package com.ff.coupon.model;

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
@TableName("tb_coupon_logs")
public class CouponLogs extends BaseModel {
    private static final long serialVersionUID = 1L;
	@TableId(value="log_id", type= IdType.AUTO)
	private Integer logId;
	@TableField("coupon_id")
	private Integer couponId;
	@TableField("user_id")
	private Integer userId;
	private BigDecimal price;
	@TableField("min_price")
	private BigDecimal minPrice;
	@TableField("create_time")
	private Date createTime;
	private Integer type;
	@TableField("limit_time")
	private Date limitTime;
	@TableField("use_time")
	private Date useTime;
	private Integer status;


	@TableField("use_order_sn")
	private String useOrderSn;

	public String getUseOrderSn() {
		return useOrderSn;
	}

	public void setUseOrderSn(String useOrderSn) {
		this.useOrderSn = useOrderSn;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Date limitTime) {
		this.limitTime = limitTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}
