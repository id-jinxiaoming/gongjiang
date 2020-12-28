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
 * @since 2017-08-01
 */
@TableName("tb_goods_favorites")
public class GoodsFavorites extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableId(value="favorites_id", type= IdType.AUTO)
	private Integer favoritesId;
	@TableField("goods_id")
	private Integer goodsId;
	@TableField("user_id")
	private Integer userId;
	@TableField("create_time")
	private Date createTime;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Integer favoritesId) {
		this.favoritesId = favoritesId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
