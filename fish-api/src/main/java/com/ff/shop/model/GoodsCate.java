package com.ff.shop.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;


/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-07-24
 */
@TableName("tb_goods_cate")
public class GoodsCate extends BaseModel implements Comparable<GoodsCate> {

    private static final long serialVersionUID = 1L;

	@TableId(value="cate_id", type= IdType.AUTO)
	private Integer cateId;
	@TableField("parent_id")
	private Integer parentId;
	@TableField("cate_name")
	private String cateName;
	@TableField("meta_keywords")
	private String metaKeywords;
	@TableField("meta_description")
	private String metaDescription;
	@TableField("img_url")
	private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	private Integer seq;


	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public int compareTo(GoodsCate o) {

		int i = this.getSeq() - o.getSeq();//先按照年龄排序

		return i;
	}
}
