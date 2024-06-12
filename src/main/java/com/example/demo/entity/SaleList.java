package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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

	@Transient
	private String title;

	@Transient
	private Integer price;

	//デフォルトコンストラクタ
	public SaleList() {

	}

	public SaleList(Integer id, Integer student_id, Integer bookinfo_id, LocalDate sale_day, Integer item_status,
			Integer sale_method) {
		this.id = id;
		this.studentId = student_id;
		this.bookInfo = bookinfo_id;
		this.saleDay = sale_day;
		this.itemStatus = item_status;
		this.saleMethod = sale_method;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(Integer bookInfo) {
		this.bookInfo = bookInfo;
	}

	public LocalDate getSaleDay() {
		return saleDay;
	}

	public void setSaleDay(LocalDate saleDay) {
		this.saleDay = saleDay;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getSaleMethod() {
		return saleMethod;
	}

	public void setSaleMethod(Integer saleMethod) {
		this.saleMethod = saleMethod;
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
