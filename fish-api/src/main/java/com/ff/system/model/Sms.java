package com.ff.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ff.common.base.BaseModel;

/**
 *
 * @author Yuzhongxin
 * @since 2017-07-14
 */
@TableName("sys_sms")
public class Sms extends BaseModel {



    @TableId("sms_id")
	private String smsId;
	@TableField("sms_key")
	private String smsKey;
	@TableField("sms_explain")
	private String smsExplain;
	@TableField("sms_tmpl")
	private String smsTmpl;
	@TableField("is_open")
	private Integer isOpen;
    /**
     * 阿里大于模板id
     */
	@TableField("sms_template_id")
	private String smsTemplateId;


	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getSmsKey() {
		return smsKey;
	}

	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}

	public String getSmsExplain() {
		return smsExplain;
	}

	public void setSmsExplain(String smsExplain) {
		this.smsExplain = smsExplain;
	}

	public String getSmsTmpl() {
		return smsTmpl;
	}

	public void setSmsTmpl(String smsTmpl) {
		this.smsTmpl = smsTmpl;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}



}
