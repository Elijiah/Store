package com.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.ProductDao;
import com.store.dao.impl.ProductDaoImpl;
import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService{

	@Override
	public List<Product> findByNew() throws SQLException {
		
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		
		return productDao.findByNew();
	}

	@Override
	public List<Product> findByHot() throws SQLException {
		
       ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
       
		return productDao.findByHot();
	}

	@Override
	public Product findByPid(String pid) throws SQLException {
		ProductDao productDao =(ProductDao) BeanFactory.getBean("productDao");
		
		return productDao.findByPid(pid);
	}

	@Override
	public PageBean<Product> findByPageCid(String cid, Integer currPage) throws SQLException {
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setCurrPage(currPage);
		
		Integer pageSize = 12;
		pageBean.setPageSize(pageSize);
		
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		Integer totalCount = productDao.findCountByCid(cid);
		pageBean.setTotalCount(totalCount);
		
		double tc = totalCount;
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		
		int begin = (currPage - 1) * pageSize;
		List<Product> list = productDao.findPageByCid(cid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public PageBean<Product> findByPage(Integer currPage) throws SQLException {
		
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setCurrPage(currPage);
		
		Integer  pageSize = 10;
		pageBean.setPageSize(pageSize);
		
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		Integer totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		
		double tc = totalCount;
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		
		int begin = (currPage - 1) * pageSize;
		List<Product> list  = productDao.findByPage(begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public void update(Product product) throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		productDao.update(product);
	}

	@Override
	public List<Product> findByPushDown() throws SQLException{
		
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		return productDao.findByPushDown();
		
	}

	@Override
	public void save(Product product) throws SQLException{
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		productDao.save(product);
	}

}
