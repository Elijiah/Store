package com.store.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BaseServlet;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;

/**
 * Servlet implementation class AddProductServlet
 */

public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			Product product = new Product();
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setSizeThreshold(3*1024*1024);
			
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			fileUpload.setHeaderEncoding("UTF-8");
			
			List<FileItem> list = fileUpload.parseRequest(req);
			
			Map<String, String> map = new HashMap<String,String>();
			String fileName = null;
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()) {
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					System.out.println("--------------------------------");
					System.out.println(name+"   "+value);
					map.put(name, value);
				}else {
					 fileName = fileItem.getName();
					 InputStream is = fileItem.getInputStream();
					 String realPath = this.getServletContext().getRealPath("/products/1");
					 
					 OutputStream os = new FileOutputStream(realPath+"/"+fileName);
					 
					 byte[] b = new byte[1024];
					 int len = 0;
					 while((len = is.read(b)) != -1) {
						 os.write(b, 0, len);
					 }
					is.close();
					os.close();
				}
			}
			
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getUUID());
			product.setPdate(new Date());
			product.setPimage("/products/1/"+fileName);
			product.setPflag(0);
			
			Category category = new Category();
			category.setCid(map.get("cid"));
			product.setCategory(category);
			
			
			
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			productService.save(product);
			
			// 页面跳转:
			resp.sendRedirect(req.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	
	
}
