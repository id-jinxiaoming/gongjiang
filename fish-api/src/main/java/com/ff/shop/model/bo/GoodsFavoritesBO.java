package com.ff.shop.model.bo;

import com.ff.shop.model.GoodsFavorites;
import com.ff.shop.model.ShopFavorites;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yuzhongxin
 * @since 2017-10-06
 */
public class GoodsFavoritesBO extends GoodsFavorites {

	private String goodsName;
	private String nowPrice;

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	private String goodsImage;

}
