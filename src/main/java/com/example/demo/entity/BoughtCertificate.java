package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bought_certificate")
public class BoughtCertificate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer saleListId;
	
	private LocalDateTime boughtDay;
	
	
	public BoughtCertificate() {
		
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
