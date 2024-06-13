package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CartItems {
	
	private Integer id; //主キー
	
	private String title; //タイトル
	
	private Integer price; // 値段
	
	//デフォルトコンストラクタ
	public CartItems() {
		
	}
	
	public CartItems(Integer id,String title) {
		this.id = id;
		this.title = title;
	}
	
	public CartItems(Integer id,String title,Integer price) {
		this.id = id;
		this.title = title;
		this.price = price;
	}
	
	//ゲッター　セッター
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
