package com.store.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.group.interceptors.OrderInterceptor;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.PageBean;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;
import com.store.utils.PaymentUtil;
import com.store.utils.UUIDUtils;

public class OrderServlet extends BaseServlet{

	public String saveOrder(HttpServletRequest request,HttpServletResponse response) {
		
		//封装对象
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setState(1);
		order.setOrdertime(new Date());
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		if(cart == null) {
			request.setAttribute("msg", "购物车是空的，请去选择物品");
			return "/jsp/msg.jsp";
		}
		
		order.setTotal(cart.getTotal());
		
		User existUser = (User) request.getSession().getAttribute("existUser");
		if(existUser == null) {
			request.setAttribute("msg", "你还没有登陆，请登录");
			return "jsp/login.jsp";
		}
		
		order.setUser(existUser);
		
		for(CartItem cartItem:cart.getMap().values()) {
			OrderItem orderItem = new OrderItem();	
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			order.getOrderItems().add(orderItem);
		}
		
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		orderService.saveOrder(order);
		
		cart.clearCart();
		request.setAttribute("order", order);
		return "/jsp/order_info.jsp";
		
	}
	
	public String findByUid(HttpServletRequest request,HttpServletResponse response) {
		try {
		Integer currPage = Integer.parseInt(request.getParameter("currPage"));
		User user = (User) request.getSession().getAttribute("existUser");
		
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		PageBean<Order> pageBean = orderService.findByUid(user.getUid(),currPage);
		
		request.setAttribute("pageBean", pageBean);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_list.jsp";
	}
	
	public String findByOid(HttpServletRequest request,HttpServletResponse response) {
		try {
			String oid = request.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			request.setAttribute("order", order);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "/jsp/order_info.jsp";
		
		
	}
	
	public String payOrder(HttpServletRequest request,HttpServletResponse response) {
		try {
			String oid = request.getParameter("oid");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String telephone = request.getParameter("telephone");
			String pd_FrpId = request.getParameter("pd_FrpId");
			
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setName(name);
			order.setAddress(address);
			order.setTelephone(telephone);
			
			orderService.update(order);
			
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080/Store/OrderServlet?method=callBack";
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			response.sendRedirect(sb.toString());
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public String callBack(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String oid = request.getParameter("r6_Order");
			String money = request.getParameter("r3_Amt");
			
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(2);
			orderService.update(order);
			
			
			request.setAttribute("msg", "您的订单:"+oid+"付款成功,付款的金额为:"+money);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return "jsp/msg.jsp";
		
	}
}
