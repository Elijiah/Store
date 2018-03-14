package com.store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

	private Double total = 0d;
	private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>() ;
	public Double getTotal() {
		return total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	
	public void addCart(CartItem cartItem) {
		String pid = cartItem.getProduct().getPid();
		if(map.containsKey(pid)) {
			//已经有了这个商品
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
		}else {
			//没有这个商品
			map.put(pid,cartItem);
		}
		total += cartItem.getSubtotal();
	}
	
	public void clearCart() {
		map.clear();
		total = 0d;
	}
	
	public void removeCart(String pid) {
		CartItem cartItem = map.remove(pid);
		total -= cartItem.getSubtotal();
	}
}
