package com.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;

/**
 * Servlet implementation class CartServlet
 */

public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	public String addCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
		String pid = request.getParameter("pid");
		Integer count = Integer.parseInt(request.getParameter("count"));
		
		CartItem cartItem = new CartItem();
		cartItem.setCount(count);
		
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
	
			Product product = productService.findByPid(pid);
			cartItem.setProduct(product);
			
			Cart cart = getCart(request);
			cart.addCart(cartItem);
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
		
	}
	
	
	public String removeCart(HttpServletRequest request,HttpServletResponse response) {
		try {
		String pid = request.getParameter("pid");
		Cart cart = getCart(request);
		cart.removeCart(pid);

	    response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public String clearCart(HttpServletRequest request,HttpServletResponse response) {
		try {
		Cart cart = getCart(request);
		cart.clearCart();
		
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	
	private Cart getCart(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
}
