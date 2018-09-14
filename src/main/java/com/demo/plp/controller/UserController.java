package com.demo.plp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.plp.mapper.UserLogMapper;
import com.demo.plp.po.Message;
import com.demo.plp.po.User;
import com.demo.plp.service.IUserFullService;
import com.demo.plp.service.IUserService;
import com.demo.plp.utils.IPUtil;
import com.demo.plp.utils.VerifyCodeUtils;
import com.demo.plp.utils.superclass.LoggerSuper;

@RestController
public class UserController extends LoggerSuper{
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserFullService userFullService;
	@Autowired
	private UserLogMapper userLogMapper;
	
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @param code 验证码
	 * @return
	 */
	@RequestMapping(value = "user/register.do", method = { RequestMethod.POST })
	public Message register(String username, String password, String code, HttpServletRequest request) {
		Message message = new Message();
		if(username==null||password==null||username.replace(" ", "").equals("")||password.equals("")){
			message.setStatus(Message.FAILURE);
			message.setMessage("用户名或密码为空");
			return message;
		}
		User user = userService.getUser(username);
		if (user == null) {
			HttpSession session = request.getSession();
			String verifycode = (String) session.getAttribute("verifycode");
			if(code!=null&&code.equalsIgnoreCase(verifycode)){
				userService.addUser(username, password);
				log.info("注册用户["+userService.getUser(username).getUsername()+"]");
			}else{
				message.setStatus(Message.FAILURE);
				message.setMessage("验证码错误");
			}
		}else{
			message.setStatus(Message.FAILURE);
			message.setMessage("用户已存在");
		}
		return message;
	}
	
	@RequestMapping(value = "user/verifycode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String code = VerifyCodeUtils.generateVerifyCode(4);
		session.setAttribute("verifycode", code);
		try {
			VerifyCodeUtils.outputImage(200, 50, response.getOutputStream(), code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "user/exists", method = { RequestMethod.POST })
	@ResponseBody
	public Message exists(String username) {
		User user = userService.getUser(username);
		if (user != null)
			return new Message();
		else
			return new Message("不存在该用户",Message.FAILURE);

	}

	/**
	 * 跳转注册页面
	 * @return
	 */
	@RequestMapping("user/register")
	public ModelAndView toPage() {
		return new ModelAndView("/user/register.html");
	}

	/**
	 * 用户登陆
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
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
			String ipAddr = request.getRemoteAddr();
			log.info("用户["+user.getUsername()+"]登陆,登陆ip["+ipAddr+"]");
			userLogMapper.insert(user, ipAddr);
		}
		return message;
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="user/userinfo",method=RequestMethod.POST)
	public User getUserInfo(HttpServletRequest request){
		User user = userService.getUserInfo(request);
		if(user==null)
			user = new User();
		return user;
	}
	
	/**
	 * 注销登陆
	 * @param request
	 * @param response
	 */
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
