package com.ff.shop.model.bo;

import com.ff.shop.model.Shop;
import com.ff.shop.model.ShopOrder;

public class ShopOrderBO extends ShopOrder{

	private String shopName;

	private String imgUrl;

	private String username;

	private  String paymentName;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
}
