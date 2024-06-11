package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="staff")
public class Staff {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id; //教員ID
	
	@Column(name="staff_name")
	private String staffName; //教員名
	
	@Column(name="staff_email")
	private String staffEmail; //メールアドレス
	
	@Column(name="staff_pass")
	private String staffPass; //パスワード
	
	@Column(name="staff_number")
	private String staffNumber; //学籍番号
	
	//デフォルトコンストラクタ
	public Staff() {
	}
	
	public Staff(String name, String email, String pass, String number) {
		staffName = name;
		staffEmail = email;
		staffPass = pass;
		staffNumber = number;
	}
	
	public Staff(Integer id, String name, String email, String pass, String number) {
		this.id = id;
		staffName = name;
		staffEmail = email;
		staffPass = pass;
		staffNumber = number;
	}

	//ゲッター　セッター
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}

	public String getStaffPass() {
		return staffPass;
	}

	public void setStaffPass(String staffPass) {
		this.staffPass = staffPass;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}
	
	

}
