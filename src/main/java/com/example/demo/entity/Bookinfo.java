package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Component
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

	//	private Integer bookmark;//1:ブックマークではない 2:ブックマーク済

	@Column(name = "image_id")
	private Integer imageId;

	@Transient
	private Integer itemStatus;

	@Transient
	private String imageName;

	@Transient
	private boolean isBookmark;

	public boolean isBookmark() {
		return isBookmark;
	}

	public void setBookmark(boolean isBookmark) {
		this.isBookmark = isBookmark;
	}

	//デフォルトコンストラクタ
	public Bookinfo() {

	}

	public Bookinfo(String title, String publisher, String isbn, String condition, Integer grade, String lecture,
			String author, Integer categoryId) {
		this.title = title;
		this.publisher = publisher;
		this.isbn = isbn;
		this.condition = condition;
		this.grade = grade;
		this.lecture = lecture;
		this.author = author;
		this.categoryId = categoryId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getLecture() {
		return lecture;
	}

	public void setLecture(String lecture) {
		this.lecture = lecture;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}