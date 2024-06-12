package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	private Integer payment;
	private Integer accept;
	private Integer delivery;

	public BoughtHistory() {

	}

	public BoughtHistory(Integer payment, Integer accept) {
		this.payment = payment;
		this.accept = accept;
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

}
