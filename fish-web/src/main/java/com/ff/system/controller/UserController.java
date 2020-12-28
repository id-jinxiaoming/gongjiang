package com.ff.system.controller;


import java.util.*;


import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.model.ZTree;
import com.ff.common.util.StringUtils;
import com.ff.system.model.Role;
import com.ff.system.service.RoleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ff.common.base.BaseController;
import com.ff.system.model.User;

import com.ff.system.service.UserService;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController{
	@Reference
	private UserService userService;

	@Reference
	private RoleService roleService;



	@RequiresPermissions(value = "system:user:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("system/user/list",map);

	}


	@RequiresPermissions(value = "system:user:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {
		Map<String, Object> resultMap = new LinkedHashMap();
		Page<User> page = getPage(request);
		EntityWrapper<User> ew=new EntityWrapper();
		ew.like("account","%"+key+"%");
		Page<User> data= userService.selectPage(page,ew);
		resultMap.put("data",data);
		return resultMap;
	}


	@RequiresPermissions("system:user:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		Map<String, Object> resultMap = new LinkedHashMap();
		for (String string : id) {
			User user=userService.findByPrimaryKey(string);
			if(user.getAccount().equals("admin"))
			{
				resultMap.put("msg","admin帐号禁示删除");
				resultMap.put("status",500);
				return resultMap;
			}
		}


		if(userService.deleteByPrimaryKey(id)>0)
		{

			resultMap.put("msg","删除成功");
			resultMap.put("status",200);
		}
		else
		{

			resultMap.put("msg","删除失败");
			resultMap.put("status",500);

		}

		return resultMap;
	}


	@RequiresPermissions("system:user:add")
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){

		return "/system/user/add";
	}
	@RequiresPermissions("system:user:add")
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doAdd(User user){
		Map<String, Object> resultMap = new LinkedHashMap();
		user.setCreateTime(new Date());
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));


		if(userService.insert(user)!=0){

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

	@RequiresPermissions("system:user:edit")
	@RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		User user = userService.findByPrimaryKey(id);
		map.put("item", user);
		return new ModelAndView("/system/user/edit",map);

	}
	@RequiresPermissions("system:user:edit")
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEdit(User user){
		Map<String, Object> resultMap = new LinkedHashMap();
		if(StringUtils.isNotEmpty(user.getPassword()))
		{
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		}
		else
		{
			user.setPassword(null);
		}

		if(userService.updateByPrimaryKey(user)!=0){

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


	@RequiresPermissions("system:user:editRole")
	@RequestMapping(value="/editRole",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> editRole(String id){


		List<Role> roleList= roleService.selectAll();
		List<Role> currentRoleList=roleService.findListByUserId(id);

		List<ZTree> treeList=new ArrayList<>();

		for (Role role : roleList) {
			ZTree t=new ZTree();
			for (Role currentRole : currentRoleList) {
				if(role.getId().equals(currentRole.getId()))
				{

					t.setChecked(1);
				}
			}
			t.setId(role.getId());
			t.setName(role.getName());
			t.setpId(0);
			treeList.add(t);
		}
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("status", 200);
		resultMap.put("data", treeList);
		resultMap.put("message", "");
		return resultMap;
	}
	@RequiresPermissions("system:user:editRole")
	@RequestMapping(value="/editRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doEditRole(String id,String[] rule){
		Map<String, Object> resultMap = new LinkedHashMap();
		roleService.updateByUserId(id,rule);
		resultMap.put("status", 200);
		resultMap.put("message", "操作成功");
		return resultMap;
	}


}
