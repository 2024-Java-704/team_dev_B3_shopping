package com.example.demo.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //ユーザーID

	@Column(name = "student_name")
	private String name; //学生名

	@Column(name = "birthday")
	private Date birth; //生年月日

	private String address; //住所

	@Column(name = "student_email")
	private String email; //メールアドレス

	@Column(name = "student_pass")
	private String pass; //パスワード

	@Column(name = "student_number")
	private String number; //学籍番号

	@Column(name = "bank_account")
	private String bankAccount; //口座情報

	@Column(name = "ban_day")
	private Date banDay; //凍結日

	@Column(name = "student_status")
	private Integer status; //ステータス

	//デフォルトコンストラクタ
	public Student() {
	}

	//ゲッター セッター
  
	public Student(String name, String number, String address, Date birth, String pass, String email,Integer status) {
		this.name=name;
		this.number=number;
		this.address=address;
		this.birth=birth;
		this.pass=pass;
		this.email=email;
		this.status=status;
	
	}
	
	public Student(Integer id, String name, String number, String address, Date birth, String pass, String email,Integer status) {
		this.id = id;
		this.name=name;
		this.number=number;
		this.address=address;
		this.birth=birth;
		this.pass=pass;
		this.email=email;
		this.status=status;
	
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Student(Integer status) {
		this.status = status;
	
	public Date getBanDay() {
		return banDay;
	}


	public void setBanDay(Date banDay) {
		this.banDay = banDay;

	}

}
