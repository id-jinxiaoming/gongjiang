package com.ff.common.freemarker;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;



public class FreeMarkerViewExtend extends FreeMarkerView{
	


	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request){
		
		try {
			super.exposeHelpers(model, request);
		} catch (Exception e) {
			
		}
		
		model.put("_time", new Date().getTime());
		model.put("basePath", request.getContextPath());//base目录。
		
	}
}
