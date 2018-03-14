package com.store.web.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;

/**
 * Servlet implementation class AdminCategoryServlet
 */

public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request,HttpServletResponse response) {
		
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		try {	
			List<Category> list = categoryService.findAll();
			request.setAttribute("list", list);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/admin/category/list.jsp";
	}
	
	public String saveUI(HttpServletRequest request,HttpServletResponse response) {
		return "/admin/category/add.jsp";
	}
	
	public String save(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String cname = request.getParameter("cname");
			Category category = new Category();
			category.setCid(UUIDUtils.getUUID());
			category.setCname(cname);
			
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			categoryService.save(category);
			response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAll");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String edit(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String cid = request.getParameter("cid");
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			Category category = categoryService.findById(cid);
			request.setAttribute("category", category);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/admin/category/edit.jsp";
	}
	
	public String update(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String, String[]> map = request.getParameterMap();
			
			Category category = new Category();
			BeanUtils.populate(category, map);
			
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			categoryService.update(category);
			// 页面跳转:
			response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAll");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String delete(HttpServletRequest request,HttpServletResponse response){
		try {
			String cid = request.getParameter("cid");
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			categoryService.delete(cid);
			
			// 页面跳转:
			response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAll");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
