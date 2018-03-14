package com.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.store.domain.User;


public class PrivilegeFilter implements Filter {

  
  
	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("existUser");
		
		if(user == null) {
			req.setAttribute("msg", "您还没有登录！没有权限访问！");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(req, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
