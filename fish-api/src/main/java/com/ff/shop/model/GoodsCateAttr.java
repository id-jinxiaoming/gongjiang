package com.ff.shop.model;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2017-07-24
 */
@TableName("tb_goods_cate_attr")
public class GoodsCateAttr extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="attr_id", type= IdType.AUTO)
	private Integer attrId;
	@TableField("cate_id")
	private Integer cateId;
	private String name;
	private String opts;
	private String uom;
	private Integer filtrate;
	private Integer seq;


	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpts() {
		return opts;
	}

	public void setOpts(String opts) {
		this.opts = opts;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Integer getFiltrate() {
		return filtrate;
	}

	public void setFiltrate(Integer filtrate) {
		this.filtrate = filtrate;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}



}
