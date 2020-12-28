package com.ff.shop.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-08-01
 */
@TableName("tb_goods_attr")
public class GoodsAttr extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableField("goods_id")
	private Integer goodsId;
	@TableField("attr_id")
	private Integer attrId;
	private String value;


	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



}
