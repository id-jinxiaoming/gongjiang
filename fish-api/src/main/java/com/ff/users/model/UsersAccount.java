package com.ff.users.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

import java.math.BigDecimal;

/**
 * @author Yuzhongxin
 * @since 2017-08-04
 */
@TableName("tb_users_account")
public class UsersAccount extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
	private Integer userId;
	private BigDecimal balance;
	private Integer points;
	private Integer exp;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}


}
