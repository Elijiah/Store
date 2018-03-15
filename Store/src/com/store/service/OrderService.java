package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.PageBean;

public interface OrderService {

	void saveOrder(Order order);

	PageBean<Order> findByUid(String uid, Integer currPage) throws  Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws SQLException;

	List<Order> findAll() throws Exception;

	List<Order> findByState(int pstate) throws Exception;

	List<OrderItem> showDetail(String oid)throws Exception;

}
