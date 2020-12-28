package com.ff.common.model;

import java.io.Serializable;

public class Attributes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3273566044649971158L;
	private String url;
	private String icon;
	//排序号
	private Integer seq;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
