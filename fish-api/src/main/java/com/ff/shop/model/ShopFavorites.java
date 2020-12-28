package com.ff.shop.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.ff.common.base.BaseModel;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-10-06
 */
@TableName("tb_shop_favorites")
public class ShopFavorites extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="favorites_id", type= IdType.AUTO)
	private Integer favoritesId;
	@TableField("user_id")
	private Integer userId;
	@TableField("shop_id")
	private Integer shopId;
	@TableField("create_time")
	private Date createTime;

	public Integer getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Integer favoritesId) {
		this.favoritesId = favoritesId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
