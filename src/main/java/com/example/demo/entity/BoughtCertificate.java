package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "bought_certificate")
public class BoughtCertificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "salelist_id")
	private Integer saleListId;

	@Column(name = "bought_day")
	private LocalDateTime boughtDay;

	public BoughtCertificate() {

	}

	public BoughtCertificate(Integer saleListId, LocalDateTime boughtDay) {
		this.saleListId = saleListId;
		this.boughtDay = boughtDay;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSaleListId() {
		return saleListId;
	}

	public void setSaleListId(Integer saleListId) {
		this.saleListId = saleListId;
	}

	public LocalDateTime getBoughtDay() {
		return boughtDay;
	}

	public void setBoughtDay(LocalDateTime boughtDay) {
		this.boughtDay = boughtDay;
	}

}
