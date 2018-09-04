package com.demo.plp.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.plp.po.User;
import com.demo.plp.service.IUserService;

import net.minidev.json.JSONObject;

@Controller
public class UserController {
	@Autowired
	private IUserService userService;
	@RequestMapping(value="user/register.do",method={RequestMethod.POST})
	public String register(String username, String password){
		User user = userService.getUser(username);
		if(user==null){
			userService.addUser(username, password);
		}
		return "redirect:/index.html";
	}
	@RequestMapping(value="user/exists",method={RequestMethod.POST})
	@ResponseBody
	public String exists(String username){
		User user = userService.getUser(username);
		if(user==null)
			return "{\"message\":\"success\"}";
		else
			return "{\"message\":\"failure\"}";
		
	}
	@RequestMapping("user/register")
	public String toPage(){
		return "/user/register.html";
	}
}
