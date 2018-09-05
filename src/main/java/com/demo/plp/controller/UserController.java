package com.demo.plp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	public String exists(String username) {
		User user = userService.getUser(username);
		if (user == null)
			return "{\"message\":\"success\"}";
		else
			return "{\"message\":\"failuter\"}";

	}

	@RequestMapping("user/register")
	public String toPage() {
		return "/user/register.html";
	}

	@RequestMapping(value="user/login",method=RequestMethod.POST)
	@ResponseBody
	public Message login(String username, String password, HttpServletRequest request){
		Message message = new Message();
		User user = userService.getUser(username, password);
		if(user==null){
			message.setStatus(Message.FAILURE);
			message.setMessage("用户名或密码错误!");
		}else{
			userService.login(user, IPUtil.getIpAddr(request));
		}
		return message;
	}

}
