package com.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.CategoryDao;
import com.store.dao.impl.CategoryDaoImpl;
import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService{

	@Override
	public List<Category> findAll() throws SQLException {
		
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		// 判断缓存中是否有list集合:
		Element element = cache.get("list");
		List<Category> list = null;
		if(element == null){
			// 缓存中没有数据
			System.out.println("缓存中没有数据 ,查询数据库=====");
			
			CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
			list = categoryDao.findAll();
			element = new Element("list",list); 
			cache.put(element);
		}else{
			// 缓存中已经存在数据
			System.out.println("缓存中有数据 ,没有查询数据库=====");
			list = (List<Category>)element.getObjectValue();
			
		}
		return list;
	}

	@Override
	public void save(Category category) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.save(category);
		
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		// 从缓存中移除:
		cache.remove("list");
	
	}

	@Override
	public Category findById(String cid) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		
		return categoryDao.findById(cid);
	}

	@Override
	public void update(Category category) throws SQLException {
		
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.update(category);
		
		// 清空缓存:
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		// 从缓存中移除:
		cache.remove("list");
	}

	@Override
	public void delete(String cid) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.delete(cid);
		// 清空缓存:
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		// 从缓存中移除:
		cache.remove("list");
	}
	

}
