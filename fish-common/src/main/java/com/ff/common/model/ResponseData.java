package com.ff.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class ResponseData implements Serializable {

	//返回数据
    @ApiModelProperty(value = "数据 ", required = true)
	private Object datas;
	//返回信息
    @ApiModelProperty(value = "消息", required = true)
	private String message;
	//状态  200:OK  500:服务器内部错误
    @ApiModelProperty(value = "状态 200:OK", required = true)
	private int state;
    
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
