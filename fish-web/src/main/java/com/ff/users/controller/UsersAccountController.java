package com.ff.users.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ff.common.base.BaseController;
import com.ff.common.util.MD5Utils;
import com.ff.common.util.keyword.KeywordFilter;
import com.ff.common.util.keyword.KeywordFilterBuilder;
import com.ff.common.util.keyword.ReplaceStrategy;
import com.ff.system.model.User;
import com.ff.users.model.Users;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.model.UsersRecord;
import com.ff.users.service.UsersAccountService;
import com.ff.users.service.UsersRecordService;
import com.ff.users.service.UsersService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author yuzhongxin
 * @Description 用户管理
 * @Create 2017-07-23 21:02
 **/
@Controller
@RequestMapping("/users/usersAccount")
public class UsersAccountController extends BaseController{

	@Reference
	private UsersAccountService usersAccountService;



	@RequiresPermissions(value = "users:usersAccount:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {

		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("users/usersAccount/list",map);

	}


	@RequiresPermissions(value = "users:usersAccount:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Map<String,Object>> page = getPage(request);
		Page<Map<String,Object>> data= usersAccountService.selectPageByUsersName(page, key);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}






}
