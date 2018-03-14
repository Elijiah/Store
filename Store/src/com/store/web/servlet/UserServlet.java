package com.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.store.domain.User;
import com.store.service.UserService;
import com.store.service.impl.UserServiceImpl;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;
import com.store.utils.MyDateConverter;


public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	//跳转到注册页面的执行的方法：registUI
	public String registUI(HttpServletRequest request,HttpServletResponse response) {

		
		return "/jsp/regist.jsp";
	}
	
	
	//异步校验用户名的执行的方法: checkUsername
	public String checkUsername(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		try {
		String username = request.getParameter("username");
		
		UserService userService = (UserService) BeanFactory.getBean("userService");
		
		User existuser;
		existuser = userService.findByUsername(username);
		
		if(existuser == null) {
			response.getWriter().println("1");
		}else {
			response.getWriter().println("2");
		}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
				
		return null;
		
	}
	
	//注册
	
	public String regist(HttpServletRequest request,HttpServletResponse response) throws SQLException {	
		try {
			String code1 = request.getParameter("code");
			String code2 = (String) request.getSession().getAttribute("code");
			request.getSession().removeAttribute("code");
			
			if(!code1.equalsIgnoreCase(code2)) {
				request.setAttribute("msg", "验证码有误！");
				return "/jsp/regist.jsp";
			}
			Map<String, String[]> map = request.getParameterMap();
			
			User user = new User();
			ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);
			
			UserService userService = (UserService) BeanFactory.getBean("userService");
			userService.save(user);
			request.setAttribute("msg", "注册成功！请去邮箱激活!");
			return "/jsp/msg.jsp";
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
		return null;
		
		
		
	}
	
	//邮箱激活方法
	public String active(HttpServletRequest request,HttpServletResponse response) {
		
		String code = request.getParameter("code");
		
		UserService userService = (UserService) BeanFactory.getBean("userService");
		
		try {
			
			User existUser = userService.findByCode(code);
			
			if(existUser == null) {
				
				request.setAttribute("msg", "激活码有误！");
			}else {
				
				existUser.setState(2);
				existUser.setCode(null);
				userService.update(existUser);
				request.setAttribute("msg", "激活成功！");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}	
		return "/jsp/msg.jsp";
	}
	
	
	//跳转到登陆页面
	public String loginUI(HttpServletRequest request,HttpServletResponse response) {
		
		return "/jsp/login.jsp";
	}
	
	
	//登陆的方法
	public String login(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		try {
			
			String code1 = request.getParameter("code");
			String code2 = (String) request.getSession().getAttribute("code");
			request.getSession().removeAttribute("code");
			
			if(!code1.equalsIgnoreCase(code2)) {
				request.setAttribute("msg", "验证码有误！");
				return "/jsp/login.jsp";
			}
			
			Map<String, String[]> map = request.getParameterMap();
			
			User user = new User();
			BeanUtils.populate(user, map);
			
			UserService userService =(UserService) BeanFactory.getBean("userService");
			User existUser = userService.login(user);
			if(existUser == null) {
				request.setAttribute("msg", "用户名或密码错误！");
				return "/jsp/login.jsp";
			}else {
				
				//自动登录
				String autoLogin = request.getParameter("autoLogin");
				if("true".equalsIgnoreCase(autoLogin)) {
					Cookie cookie = new Cookie("autoLogin", existUser.getName()+"#"+existUser.getPassword());
					cookie.setPath("/Store");
					cookie.setMaxAge(7*24*60*60);
					response.addCookie(cookie);
				}
				
				//记住用户名
				String remember = request.getParameter("remember");
				if("true".equalsIgnoreCase(autoLogin)) {
					Cookie cookie = new Cookie("username", existUser.getUsername());
					cookie.setPath("/Store");
					cookie.setMaxAge(7*24*60*60);
					response.addCookie(cookie);
				}
				
				
				request.getSession().setAttribute("existUser", existUser);
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		throw new RuntimeException();
	  }
	}

	
	//退出功能
	public String logOut(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		
		try {
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		return null;
		
	}
}
