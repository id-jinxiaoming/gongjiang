package com.ff.system.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

/**
 * @author Yuzhongxin
 * @since 2017-07-14
 */
@TableName("sys_setting")
public class Setting extends BaseModel {



	private String k;
	private String v;


	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}



}
