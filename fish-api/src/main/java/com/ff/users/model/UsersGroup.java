package com.ff.users.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-02
 */
@TableName("tb_users_group")
public class UsersGroup extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="group_id", type= IdType.AUTO)
	private Integer groupId;
	@Length(min=1,max=20,message = "{lenght.message}")
	@TableField("group_name")
	private String groupName;
	@Range(max=999999, min=0,message = "{lenght.message}")
	@TableField("min_exp")
	private Integer minExp;
	@TableField("discount_rate")
	@Range(max=100, min=0,message = "{lenght.message}")
	private Integer discountRate;


	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getMinExp() {
		return minExp;
	}

	public void setMinExp(Integer minExp) {
		this.minExp = minExp;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}



}
