package com.ff.shop.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-07-23
 */
@TableName("tb_goods_optional_type")
public class GoodsOptionalType extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="type_id", type= IdType.AUTO)
	private Integer typeId;
	private String name;


	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
