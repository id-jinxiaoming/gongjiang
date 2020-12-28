package com.ff.ad.model;

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
 * @since 2017-09-26
 */
@TableName("tb_ad")
public class Ad extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="ad_id", type= IdType.AUTO)
	private Integer adId;
	private String title;
	@TableField("image_url")
	private String imageUrl;
	private Integer status;
	private Integer sort;
	private Integer type;
	@TableField("ad_type")
	private Integer adType;
	@TableField("link_id")
	private Integer linkId;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}
}
