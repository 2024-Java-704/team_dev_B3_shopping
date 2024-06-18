package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
@Table(name = "bought_history")
public class BoughtHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "student_id")
	private Integer studentId;
	@Column(name = "salelist_id")
	private Integer salelistId;

	@ManyToOne
	@JoinColumn(name = "salelist_id", insertable = false, updatable = false)
	private SaleList saleList;

	private Integer payment;
	private Integer accept;
	private Integer delivery;

	@Transient
	private LocalDateTime boughtDay;

	public String getBoughtDay() {
		if (boughtDay == null) {
			return "なし";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		return boughtDay.format(formatter);
	}

	public void setBoughtDay(LocalDateTime boughtDay) {
		this.boughtDay = boughtDay;
	}

	public BoughtHistory() {

	}

	public BoughtHistory(Integer payment, Integer accept) {
		this.payment = payment;
		this.accept = accept;
	}

	public BoughtHistory(Integer studentId, Integer salelistId, Integer payment, Integer accept, Integer delivery) {
		this.studentId = studentId;
		this.salelistId = salelistId;
		this.payment = payment;
		this.accept = accept;
		this.delivery = delivery;
	}

	public BoughtHistory(Integer studentId, Integer salelistId, Integer payment, Integer accept) {
		this.studentId = studentId;
		this.salelistId = salelistId;
		this.payment = payment;
		this.accept = accept;
		// TODO 自動生成されたコンストラクター・スタブ
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

	public Integer getSalelistId() {
		return salelistId;
	}

	public void setSalelistId(Integer salelistId) {
		this.salelistId = salelistId;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
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

	public SaleList getSaleList() {
		return saleList;
	}

	public void setSaleList(SaleList saleList) {
		this.saleList = saleList;
	}

}
