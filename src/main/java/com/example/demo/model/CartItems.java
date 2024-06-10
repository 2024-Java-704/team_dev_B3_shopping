package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class CartItems {
	
	private Integer id; //主キー
	
	private String title; //タイトル
	
	//デフォルトコンストラクタ
	public CartItems() {
		
	}
	
	public CartItems(Integer id,String title) {
		this.id = id;
		this.title = title;
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
	
}
