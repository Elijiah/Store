package com.store.domain;

public class CartItem {

	private Product product;
	private Double subtotal;
	private Integer count;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getSubtotal() {
		return count * product.getShop_price();
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
