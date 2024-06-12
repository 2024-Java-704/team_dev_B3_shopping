package com.example.demo.entity;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Component
@Entity
@Table(name = "sale_list")
public class SaleList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "bookinfo_id")
	private Integer bookInfoId;
	
	@ManyToOne
    @JoinColumn(name = "bookinfo_id", insertable = false, updatable = false)
	private Bookinfo bookInfo;

	@Column(name = "sale_day")
	private LocalDate saleDay;

	@Column(name = "item_status")
	private Integer itemStatus;

	@Column(name = "sale_method")
	private Integer saleMethod;

	@Transient
	private String title;

	@Transient
	private String Author;
	
	@Transient
	private String isbn;

	@Transient
	private Integer price;

	@Transient
	private Integer accept;

	@Transient
	private Integer delivery;
	
	@Transient
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public Integer getBookInfoId() {
		return bookInfoId;
	}

	public void setBookInfoId(Integer bookInfoId) {
		this.bookInfoId = bookInfoId;
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

	public Integer getAccept() {
		return accept;
	}

	public void setAccept(Integer accept) {
		this.accept = accept;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}
	
	public Bookinfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(Bookinfo bookInfo) {
        this.bookInfo = bookInfo;
    }

}
