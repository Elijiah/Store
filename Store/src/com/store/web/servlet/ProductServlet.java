package com.store.web.servlet;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;



import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;
import com.store.utils.CookieUtils;

/**
 * Servlet implementation class ProductServlet
 */

public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	//根据分类的ID查询某个分类下的商品（分页显示）：findByCid
	public String findByCid(HttpServletRequest request,HttpServletResponse response) throws SQLException {
		try {
			
		
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
		PageBean<Product> pageBean = productService.findByPageCid(cid,currPage);
		
		request.setAttribute("pageBean", pageBean);
		return "/jsp/product_list.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//根据商品ID查询商品详情的执行的方法:findByPid
	public String findByPid(HttpServletRequest request,HttpServletResponse response) {
		try{
			// 接收pid:
			String pid = request.getParameter("pid");
			
			// 调用业务层:
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			System.out.println(product);
			// 记录用户的商品浏览记录: history
			// 从Cookie中获得指定名称的Cookie:
			Cookie[] cookies = request.getCookies();
			Cookie cookie = CookieUtils.findCookie(cookies, "history");
			// 判断Cookie有没有:
			if(cookie == null){
				// 没有浏览记录:
				Cookie c = new Cookie("history",pid);
				c.setPath("/Store");
				c.setMaxAge(7 * 24 * 60 * 60);
				response.addCookie(c);
			}else{
				// 有浏览记录:  1-2-3
				// 判断是否已经浏览过该商品  1-2-3 点击2 2-1-3  如果点击4 4-2-1-3 
				String value = cookie.getValue();
				String[] ids = value.split("-");
				// 将数组转成linkedList的集合:
				LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
				// 判断是否已经在浏览记录中:
				if(list.contains(pid)){
					// 已经浏览过该商品
					list.remove(pid);
					list.addFirst(pid);
				}else{
					//　没有浏览过
					if(list.size() >= 6){
						// 有超过6个 删除最后 将其填到最前面:
						list.removeLast();
						list.addFirst(pid);
					}else{
						// 没有超过6个 只需要添加到最前面:
						list.addFirst(pid);
					}
				}
				StringBuffer sb = new StringBuffer();
				// 遍历List集合 拼接成一个字符串:
				for (String id : list) {
					sb.append(id).append("-");
				}
				
				String idStr = sb.toString().substring(0,sb.length()-1);
				Cookie c = new Cookie("history",idStr);
				c.setPath("/Store");
				c.setMaxAge(7 * 24 * 60 * 60);
				response.addCookie(c);
			}
			
			// 页面跳转:
			request.setAttribute("p", product);
			return "/jsp/product_info.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
}
