package com.ff.order.model.bo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;
import com.ff.order.model.OrderGoods;
import com.ff.order.model.OrderGoodsOptional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-13
 */
public class OrderGoodsBO extends OrderGoods {


	private List<OrderGoodsOptional> orderGoodsOptionals;

	public List<OrderGoodsOptional> getOrderGoodsOptionals() {
		return orderGoodsOptionals;
	}

	public void setOrderGoodsOptionals(List<OrderGoodsOptional> orderGoodsOptionals) {
		this.orderGoodsOptionals = orderGoodsOptionals;
	}
}
