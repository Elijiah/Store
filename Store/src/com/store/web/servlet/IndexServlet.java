package com.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;


public class IndexServlet extends BaseServlet{

	
	private static final long serialVersionUID = 1L;

	public String index(HttpServletRequest request,HttpServletResponse response) throws SQLException {
		
		try {
			ProductService productService  = (ProductService) BeanFactory.getBean("productService");
			
			List<Product> newList = productService.findByNew();
			List<Product> hotList = productService.findByHot();
			
			request.setAttribute("newList", newList);
			request.setAttribute("hotList", hotList);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
		
		
		return "/jsp/index.jsp";
		
	}
}
