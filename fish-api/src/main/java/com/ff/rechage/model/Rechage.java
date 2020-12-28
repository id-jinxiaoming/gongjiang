package com.ff.rechage.model;

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
 * @since 2017-10-07
 */
@TableName("tb_rechage")
public class Rechage extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="rechage_id", type= IdType.AUTO)
	private Integer rechageId;
	private String title;
	private BigDecimal money;
	private BigDecimal coupon;


	public Integer getRechageId() {
		return rechageId;
	}

	public void setRechageId(Integer rechageId) {
		this.rechageId = rechageId;
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



}
