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
import com.ff.users.model.UsersAccount;
import com.ff.users.model.UsersAccountLog;
import com.ff.users.model.UsersRecord;
import com.ff.users.service.UsersAccountService;
import com.ff.users.service.UsersRecordService;
import com.ff.users.service.UsersService;
import io.swagger.models.auth.In;
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
@RequestMapping("/users/users")
public class UsersController extends BaseController{
	@Reference
	private UsersService usersService;
	@Reference
	private UsersRecordService usersRecordService;

	@Reference
	private UsersAccountService usersAccountService;



	@RequiresPermissions(value = "users:users:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String key,ModelMap map) {
		//测试关键词
		KeywordFilterBuilder builder = new KeywordFilterBuilder();
		// 设置关键词
		builder.setKeywords( Arrays.asList("心情", "哈哈", "敏感词1", "敏感词2") );
		// 设置跳过字符
		builder.setSkipChars( Arrays.asList('*', '_',  ',',  '.',  '-') );
		final KeywordFilter filter = builder.build();

		// 替换关键字
		String info="买彩票中奖了，哈哈";
		String test=filter.replace( info, new ReplaceStrategy() {
			public String replaceWith(String keywords) {
				return "**";
			}
		});

		// MQ测试
		usersService.testJMS();
		key= StringEscapeUtils.unescapeHtml(key);
		try {
			key = new String(key.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}

		map.put("key", key);
		return new ModelAndView("users/users/list",map);

	}


	@RequiresPermissions(value = "users:users:list")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doList(HttpServletRequest request, String key) {

		Page<Users> page = getPage(request);
		EntityWrapper<Users> ew=new EntityWrapper();
		ew.like("username","%"+key+"%");
		Page<Users> data= usersService.selectPage(page,ew);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("data",data);
		return resultMap;
	}

	@RequiresPermissions("users:users:delete")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String[] id) {
		usersService.deleteByPrimaryKey(id);
		Map<String, Object> resultMap = new LinkedHashMap();
		resultMap.put("msg","删除成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("users:users:show")
	@RequestMapping(value="/show/{id}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id")String id, ModelMap map){
		UsersRecord record= usersRecordService.findByPrimaryKey(id);
		map.put("record", record);
		Users model = usersService.findByPrimaryKey(id);
		map.put("item", model);
		map.put("account", usersAccountService.findByPrimaryKey(id));

		return new ModelAndView("/users/users/show",map);

	}

	@RequiresPermissions("users:users:pwd")
	@RequestMapping(value = "/pwd")
	@ResponseBody
	public Object pwd(Integer userId,String pwd,String pwd1) {

		Map<String, Object> resultMap = new LinkedHashMap();
		Users users=new Users();
		users.setUserId(userId);
		if(!pwd.equals(pwd1))
		{
			resultMap.put("message","两次密码不一致");
			resultMap.put("status",500);
			return resultMap;
		}
		users.setPassword(MD5Utils.getPwd(pwd));
		usersService.updateByPrimaryKey(users);

		resultMap.put("message","操作成功");
		resultMap.put("status",200);
		return resultMap;
	}

	@RequiresPermissions("users:users:account")
	@RequestMapping(value = "/account")
	@ResponseBody
	public Object account(UsersAccountLog log) {
		Subject subject = SecurityUtils.getSubject();
		User user=(User) subject.getPrincipal();
		log.setAdminId(user.getId());
		Long time=new Date().getTime()/1000;
		log.setDateline(Integer.parseInt(time.toString()));
		Map<String, Object> resultMap = new LinkedHashMap();
		if(usersAccountService.updateAccount(log))
		{

			resultMap.put("message","操作成功");
			resultMap.put("status",200);
		}
		else
		{
			resultMap.put("message","操作失败");
			resultMap.put("status",500);
		}

		return resultMap;
	}







}
