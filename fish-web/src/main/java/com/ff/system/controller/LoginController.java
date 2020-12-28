package com.ff.system.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ff.common.annotation.SysLog;
import com.ff.common.base.BaseController;
import com.ff.system.model.Role;
import com.ff.system.model.User;
import com.ff.system.service.ResourcesService;
import com.ff.system.service.RoleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;





/**
 * 登录Controller
 * @author Yzx
 *
 */
@Controller
public class LoginController  extends BaseController {

	@Reference
	private ResourcesService resourcesService;

	@Reference
	private RoleService roleService;
	
	//首页
	@SysLog(value="首页")
	@RequestMapping(value = "/")
    public ModelAndView index() {
		ModelAndView mav= new ModelAndView("index");
		
		User token = (User)SecurityUtils.getSubject().getPrincipal();
		
		List<Role> roleList= roleService.findListByUserId(token.getId());
		Set<String> roleSet = new HashSet<String>();
		boolean superFlag=false;
		for (Role roleId : roleList) {
			if("888888".equals(roleId.getType()))
			{
				superFlag=true;
				
			}
			roleSet.add(roleId.getName());
		}
		
		if(superFlag)
		{

			mav.addObject("menu", resourcesService.selectResources());
			
			
		}else
		{
			mav.addObject("menu", resourcesService.selectResourcesByUser(token));
			
		}
		
		
		
		mav.addObject("role",roleSet);
        return mav;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String  login(HttpServletRequest request,HttpServletResponse response) throws Exception {


		if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

	@SysLog(value="登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(String username, String password,HttpServletRequest request) {
    	Subject user = SecurityUtils.getSubject();
    	UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password).toCharArray());
    	Map<String, Object>	map=new HashMap<>();
        try {
            user.login(token);
        } catch (UnknownAccountException e) {
        	e.printStackTrace();
        	map.put("msg", "账号不存在");
        } catch (DisabledAccountException e) {
        	e.printStackTrace();
        	map.put("msg", "账号未启用");
        } catch (IncorrectCredentialsException e) {
        	e.printStackTrace();
        	map.put("msg", "密码错误");
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	map.put("msg", "未知错误,请联系管理员");
        }
      
        return map;
    }

    @RequestMapping(value = "/logout")
    public String logout() {
    	Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
       
    }
}
