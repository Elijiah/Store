package com.store.service.impl;

import java.sql.SQLException;

import com.store.dao.UserDao;
import com.store.dao.impl.UserDaoImpl;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.utils.BeanFactory;
import com.store.utils.MailUtils;
import com.store.utils.UUIDUtils;

public class UserServiceImpl implements UserService{

	@Override
	public User findByUsername(String username) throws SQLException {
		
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		return userDao.findByUsername(username);
	}

	@Override
	public void save(User user) throws SQLException {
		
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		user.setUid(UUIDUtils.getUUID());
		user.setState(2);
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);	
		MailUtils.sendMail(user.getEmail(), code);
	}

	@Override
	public User findByCode(String code) throws SQLException {
		
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		
		return userDao.findByCode(code);
	}

	@Override
	public void update(User existUser) throws SQLException {
		UserDao userDao =(UserDao) BeanFactory.getBean("userDao");
		userDao.update(existUser);
	}

	@Override
	public User login(User user) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		return userDao.login(user);
	}

}
