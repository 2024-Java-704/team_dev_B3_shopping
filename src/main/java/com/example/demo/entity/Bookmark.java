package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookmark")

public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	@Column(name = "student_id")
	private Integer studentId;
	@Column(name = "salelist_id")
	private Integer salelistId;

	public Bookmark() {

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

}
