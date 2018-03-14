package com.store.service.impl;




import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.store.dao.OrderDao;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.PageBean;
import com.store.service.OrderService;
import com.store.utils.BeanFactory;
import com.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void saveOrder(Order order) {
		
		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
			orderDao.saveOrder(conn,order);
			
			for(OrderItem orderItem:order.getOrderItems()) {
				orderDao.saveOrderItem(conn,orderItem);
			}
			DbUtils.commitAndCloseQuietly(conn);
		}catch(Exception e) {
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}

	@Override
	public PageBean<Order> findByUid(String uid, Integer currPage) throws Exception {
		
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setCurrPage(currPage);
		
		Integer pageSize = 5;
		pageBean.setPageSize(pageSize);
		
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		Integer totalCount = orderDao.findCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		
		double tc = totalCount;
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		
		//设置总页数
		int begin = (currPage-1) * pageSize;
		List<Order> list = orderDao.findPageByUid(uid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		return orderDao.findByOid(oid);
	}

	@Override
	public void update(Order order) throws SQLException {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		orderDao.update(order);
	}

}
