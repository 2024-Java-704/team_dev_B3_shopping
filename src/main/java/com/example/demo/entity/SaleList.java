package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sale_list")
public class SaleList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "student_id")
	private Integer studentId;
	
	@Column(name = "bookinfo_id")
	private Integer bookInfo;
	
	@Column(name = "sale_day")
	private LocalDate saleDay;
	
	@Column(name = "item_status")
	private Integer itemStatus;
	
	@Column(name = "sale_method")
	private Integer saleMethod;
	
	//デフォルトコンストラクタ
	public SaleList() {
		
	}
}
