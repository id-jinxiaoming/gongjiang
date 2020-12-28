package com.ff.users.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.model.Validate;
import com.ff.users.model.Users;
import com.ff.users.model.UsersGroup;
import com.ff.users.service.UsersGroupService;
import com.ff.users.service.UsersService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 用户组管理
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/users/usersGroup")
public class UsersGroupController extends BaseController{
	@Reference
	private UsersGroupService usersGroupService;



	@RequiresPermissions(value = "users:usersGroup:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {


		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("users/usersGroup/list",map);

	}


	@RequiresPermissions(value = "users:usersGroup:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<UsersGroup> page = getPage(request);
		EntityWrapper<UsersGroup> ew=new EntityWrapper();
		ew.like("group_name","%"+key+"%");
		Page<UsersGroup> data= usersGroupService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("users:usersGroup:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		usersGroupService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("users:usersGroup:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/users/usersGroup/add";
	}
	@RequiresPermissions("users:usersGroup:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(@Valid UsersGroup model, BindingResult result){

		if(result.hasErrors()){
			//如果没有通过,跳转提示
			List<Validate> errs= getErrors(result);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("status", 400);
			resultMap.put("data", errs);
			return resultMap;
		}else{
			Map<String, Object> resultMap = new LinkedHashMap();
			if(usersGroupService.insert(model)!=0){

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


	@RequiresPermissions("users:usersGroup:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		UsersGroup model = usersGroupService.findByPrimaryKey(id);
		map.put("item", model);
		return new ModelAndView("/users/usersGroup/edit",map);

	}
	@RequiresPermissions("users:usersGroup:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(UsersGroup model){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(usersGroupService.updateByPrimaryKey(model)!=0){

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
