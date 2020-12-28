package com.ff.users.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-04
 */
@TableName("tb_users_record")
public class UsersRecord extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
	private Integer userId;
	@TableField("created_date")
	private String createdDate;
	@TableField("created_ip")
	private String createdIp;
	@TableField("last_date")
	private String lastDate;
	@TableField("last_ip")
	private String lastIp;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedIp() {
		return createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}



}
