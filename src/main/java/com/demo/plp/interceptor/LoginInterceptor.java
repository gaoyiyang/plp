package com.demo.plp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.demo.plp.service.IUserService;
import com.demo.plp.service.impl.UserServiceImpl;

/**
 * 权限验证拦截器
 * @author gaoyiyang
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private IUserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isOnline = userService.isOnline(request);
		if(isOnline){
			response.sendRedirect("/main.html");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj,
			ModelAndView modelandview) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object obj, Exception exception) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(httpservletrequest, httpservletresponse, obj, exception);
	}
	
}
