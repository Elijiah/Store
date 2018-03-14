package com.store.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.CategoryService;
import com.store.service.ProductService;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;

/**
 * 
 */

public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String findByPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Integer currPage = Integer.parseInt(request.getParameter("currPage"));
			
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			PageBean<Product> pageBean  =  productService.findByPage(currPage);
			
			request.setAttribute("pageBean", pageBean);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
		
	}
	
	public String saveUI(HttpServletRequest request,HttpServletResponse response) {
		try {
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			request.setAttribute("list", list);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}
	
	public String pushDown(HttpServletRequest request,HttpServletResponse response) {
		try {
			String pid = request.getParameter("pid");
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			product.setPflag(1);
			productService.update(product);
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findByPushDown(HttpServletRequest request,HttpServletResponse response) {
		try {
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			List<Product> list = productService.findByPushDown();
			request.setAttribute("list", list);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/pushDown_list.jsp";
	}
	
	public String pushUp(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String pid = request.getParameter("pid");
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			product.setPflag(0);
			productService.update(product);
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
