package com.ff.common.base;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ff.common.model.ResponseData;
import com.ff.common.model.Validate;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.util.DateUtils;
import com.ff.common.util.StringUtils;

@Controller
public abstract class BaseController {
	ResponseData result = new ResponseData();
	protected List<Validate> getErrors(BindingResult result) {
		List<Validate> map = new ArrayList<Validate>();
		List<FieldError> list = result.getFieldErrors();
		for (FieldError error : list) {
			Validate temp=new Validate();
			temp.setName(error.getField());
			temp.setValue(error.getDefaultMessage());
			map.add(temp);
		}
		return map;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
		
		// Timestamp 类型转换
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date date = DateUtils.parseDate(text);
				setValue(date==null?null:new Timestamp(date.getTime()));
			}
		});
	}
	
	/**
	 * 获取page对象
	 * @param request
	 * @return page对象
	 */
	public <T> Page<T>  getPage(HttpServletRequest request){
		int pageNo=1;	//当前页码
		int pageSize=10;	//每页行数


		if(StringUtils.isNotEmpty(request.getParameter("page")))
			pageNo=Integer.valueOf(request.getParameter("page"));
		if(StringUtils.isNotEmpty(request.getParameter("rows")))
			pageSize=Integer.valueOf(request.getParameter("rows"));
		
		return new Page<T> (pageNo, pageSize);
	}
	



	/**
	 * 获取page对象
	 * @param request
	 * @return page对象
	 */
	public <T> com.ff.common.model.Page<T>  getBasePage(HttpServletRequest request){
		int pageNo=1;	//当前页码
		int pageSize=20;	//每页行数

		if(StringUtils.isNotEmpty(request.getParameter("page")))
			pageNo=Integer.valueOf(request.getParameter("page"));
		if(StringUtils.isNotEmpty(request.getParameter("rows")))
			pageSize=Integer.valueOf(request.getParameter("rows"));

		return new com.ff.common.model.Page<T> (pageNo, pageSize);
	}

}
