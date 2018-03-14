package com.store.service;

import java.sql.SQLException;

import com.store.domain.Order;
import com.store.domain.PageBean;

public interface OrderService {

	void saveOrder(Order order);

	PageBean<Order> findByUid(String uid, Integer currPage) throws  Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws SQLException;

}
