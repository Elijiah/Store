package com.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.impl.CategoryServiceImpl;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */

public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	public String findAll(HttpServletRequest request,HttpServletResponse response) {
		
		try{
			CategoryService categoryService =(CategoryService) BeanFactory.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			// 将list转成JSON: 
			JSONArray jsonArray = JSONArray.fromObject(list);
			System.out.println(jsonArray.toString());
			
			response.getWriter().println(jsonArray.toString());
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
		
	
	
	
	
}
