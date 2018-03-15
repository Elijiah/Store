package com.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.service.OrderService;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class AdminOrderServlet
 */

public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request,HttpServletResponse response) {
		try {
			String state = request.getParameter("state");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			List<Order> list = null;
			if(state == null) {
				list = orderService.findAll();
			}else {
				int pstate = Integer.parseInt(state);
				list = orderService.findByState(pstate);
			}
			request.setAttribute("list", list);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/admin/order/list.jsp";
	}
	
	public String showDetail(HttpServletRequest request,HttpServletResponse response) {
		
		String oid = request.getParameter("oid");
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		try {
			List<OrderItem> list = orderService.showDetail(oid);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"order"});
			JSONArray jsonArray = JSONArray.fromObject(list);
		
			response.getWriter().println(jsonArray.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
