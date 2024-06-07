package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookinfo")
public class Bookinfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	private String title;
	
	private String author;
	
	private String publisher;
	
	private String isbn;
	
	private Integer grade;
	
	private String lecture;
	
	private String condition;
	
	private Integer price;
	
	//デフォルトコンストラクタ
	public Bookinfo() {
		
	}

}