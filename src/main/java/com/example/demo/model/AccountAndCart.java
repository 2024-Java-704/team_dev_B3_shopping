package com.example.demo.model;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AccountAndCart {
	private Integer id; //主キー
	
	private String name; //名前
	
	List<CartItems> cartItems;
	
	
	//デフォルトコンストラクタ
	public AccountAndCart(){
	}
	
	public AccountAndCart(Integer id, String name) {
		this.id=id;
		this.name=name;
	}
	
	
	public AccountAndCart(List<CartItems> cartItems) {
		
	}
	
	
	//ゲッター　セッター

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}
	
}
