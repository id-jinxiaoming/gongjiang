package com.ff.system.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.system.model.Dict;
import com.ff.system.model.Sms;
import com.ff.system.service.DictService;
import com.ff.system.service.SmsService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@RequestMapping("/system/sms")
public class SmsController extends BaseController{
	@Reference
	private SmsService smsService;
	

	@RequiresPermissions(value = "system:sms:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		map.put("active", "system:sms:list");
		return new ModelAndView("system/sms/list",map);

	}


	@RequiresPermissions(value = "system:sms:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Sms> page = getPage(request);
		EntityWrapper<Sms> ew=new EntityWrapper();
		ew.like("sms_key","%"+key+"%");
		Page<Sms> data= smsService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("system:sms:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		smsService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("system:sms:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/system/sms/add";
	}
	@RequiresPermissions("system:sms:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(Sms model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(smsService.insert(model)!=0){

			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		}
		else
		{
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败");
		}
		return resultMap;
	}

	@RequiresPermissions("system:sms:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		Sms model = smsService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/system/sms/edit",map);

	}

	@RequiresPermissions("system:sms:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(Sms t){

		Map<String, Object> resultMap = new LinkedHashMap();
		if(smsService.updateByPrimaryKey(t)!=0){

			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		}
		else
		{
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败");
		}
		return resultMap;
	}



	

}
