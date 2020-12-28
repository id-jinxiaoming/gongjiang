package com.ff.system.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ff.common.annotation.SysLog;
import com.ff.common.util.HttpContextUtils;
import com.ff.system.model.Log;
import com.ff.system.model.User;
import com.ff.system.service.LogService;


/**
 * 系统日志，切面处理类
 * @author Yzx
 *
 */
@Aspect
@Component
public class SysLogAspect {
	private Date beginTime;// 1、开始时间
	private Date endTime;// 2、结束时间
	@Reference
	private LogService logService;
	@Pointcut("@annotation(com.ff.common.annotation.SysLog)") 
	public void logPointCut() { 
		
	}
	@Before("logPointCut()")
	public void before(JoinPoint joinPoint) {
		beginTime = new Date();
	}
	
	@After("logPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		
		endTime = new Date();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		
		
		Log log=new Log();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述 
			log.setName(syslog.value());
		}
		
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		
		log.setUrl(request.getRequestURL().toString());
		log.setParameter(JSONObject.toJSONString(request.getParameterMap()));
		log.setRemoteAddr(request.getRemoteAddr());
		log.setAgent(request.getHeader("user-agent"));
		log.setBeginTime(beginTime);
		log.setEndTime(endTime);
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		if(user!=null){
			log.setUserId(user.getId());
		}
		
		logService.insert(log);
	}
	

}
