package com.demo.plp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.plp.po.Message;
import com.demo.plp.po.User;
import com.demo.plp.service.IUserService;
import com.demo.plp.utils.IPUtil;

@RestController
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "user/register.do", method = { RequestMethod.POST })
	public String register(String username, String password) {
		User user = userService.getUser(username);
		if (user == null) {
			userService.addUser(username, password);
		}
		return "redirect:/index.html";
	}

	@RequestMapping(value = "user/exists", method = { RequestMethod.POST })
	@ResponseBody
	public Message exists(String username) {
		User user = userService.getUser(username);
		if (user == null)
			return new Message();
		else
			return new Message("不存在该用户",Message.FAILURE);

	}

	@RequestMapping("user/register")
	public ModelAndView toPage() {
		return new ModelAndView("/user/register.html");
	}

	@RequestMapping(value="user/login",method=RequestMethod.POST)
	@ResponseBody
	public Message login(String username, String password, HttpServletRequest request){
		Message message = new Message();
		if(userService.isOnline(request)){
			message.setStatus(Message.FAILURE);
			message.setMessage("请勿重复登陆!");
			return message;
		}
		User user = userService.getUser(username, password);
		if(user==null){
			message.setStatus(Message.FAILURE);
			message.setMessage("用户名或密码错误!");
		}else{
			request.getSession().setAttribute("user", user);
			userService.login(request);
		}
		return message;
	}
	
	@RequestMapping(value="user/userinfo",method=RequestMethod.POST)
	public User getUserInfo(HttpServletRequest request){
		return userService.getUserInfo(request);
	}
	
	@RequestMapping(value="user/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response){
		userService.logout(request);
		try {
			response.sendRedirect("/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
