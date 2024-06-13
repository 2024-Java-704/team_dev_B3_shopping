package com.example.demo.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class StaffOperationController {

	@Autowired
	HttpSession session;
	
	@Autowired
	StudentRepository studentRepository;
	
	//学生アカウント一覧
	@GetMapping("/account")
	public String accountAccess(Model model) {
		List<Student> studentList = studentRepository.findAll();
		model.addAttribute("studentList", studentList);
		return "SOC_accountList";
	}

	//学生アカウント詳細
	@GetMapping("/account/{id}/detail")
	public String accountDetail(
			@PathVariable("id") Integer id,
			Model model
			) {
		Student student = studentRepository.findById(id).get();
		model.addAttribute("student",student);
		return "SOC_accountDetail";
	}

	//アカウント申請一覧
	@GetMapping("/account/registerList")
	public String accountAddAccess(Model model) {
		List<Student> tempRegiList = studentRepository.findByStatusIs(1);
		model.addAttribute("student", tempRegiList);
		return "SOC_accountRegisterList";
	}
	
	//申請詳細
	@GetMapping("/account/registerList/{id}/detail")
	public String accountAddDetail(
			@PathVariable("id") Integer id,
			Model model
			) {
		Student student = studentRepository.findById(id).get();
		model.addAttribute("student",student);
		return "SOC_accountRegisterDetail";
	}

	//申請承認→完了画面
	@PostMapping("/account/registerList/{id}/confirm")
	public String accountConfirmComplete(
			@PathVariable("id") Integer id
			) {
		Student student = studentRepository.findById(id).get();
		student.setStatus(2);
		studentRepository.save(student);
		return "SOC_accountRegisterComplete";
	}

	//申請却下→完了画面
	@PostMapping("/account/registerList/{id}/deny")
	public String accountRejectAccess(
			@PathVariable("id") Integer id
			) {
		Student student = studentRepository.findById(id).get();
		student.setStatus(5);
		studentRepository.save(student);
		return "SOC_accountRegisterDenyComplete";
	}

	//更新画面の表示
	@GetMapping("/account/{id}/update")
	public String accountUpdateAccess(
			@PathVariable("id") Integer id,
			Model model
			) {
		Student student = studentRepository.findById(id).get();
		model.addAttribute("student", student);
		return "SOC_accountUpdate";
	}
	
	//更新確認画面の表示
	@GetMapping("/account/{id}/update/confirm")
	public String accountUpdateConfirm(
			@PathVariable("id") Integer id,
			@RequestParam(name = "number", defaultValue = "") String number,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "pass", defaultValue = "") String pass,
			@RequestParam(name = "birth", defaultValue = "") Date birth,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "bankAccount", defaultValue = "") String bankAccount,
			Model model
			) {
		//Nullチェックの処理を後で記述
		
		//更新前の情報
		Student student = studentRepository.findById(id).get();
		model.addAttribute("lastInfo", student);
		
		//更新後の情報
		model.addAttribute("number", number);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("pass", pass);
		model.addAttribute("birth", birth);
		model.addAttribute("address", address);
		model.addAttribute("bankAccount", bankAccount);
		
		return "SOC_accountUpdateConfirm";
	}

	@PostMapping("/account/{id}/update/complete")
	public String accountUpdate(
			@PathVariable("id") Integer id,
			@RequestParam(name = "number", defaultValue = "") String number,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "pass", defaultValue = "") String pass,
			@RequestParam(name = "birth", defaultValue = "") Date birth,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "bankAccount", defaultValue = "") String bankAccount,
			Model model
			) {
		
		//Nullチェックをしないとエラーが出るので後ほど記述
		
		Student uniqueCheck = studentRepository.findById(id).get();
		//動きはするけど無駄コード
//		
//		//メールアドレスと学籍番号どちらも変わっていない場合
//		if(uniqueCheck.getEmail().equals(email) && uniqueCheck.getNumber().equals(number)) {
//			
//			Student student = new Student(id, name, uniqueCheck.getNumber(), address, birth, pass, uniqueCheck.getEmail(), uniqueCheck.getStatus());
//			studentRepository.save(student);
//		}
//		//メールアドレスが変わっていない場合
//		if(uniqueCheck.getEmail().equals(email)) {
//			
//			Student student = new Student(id, name, number, address, birth, pass, uniqueCheck.getEmail(), uniqueCheck.getStatus());
//			studentRepository.save(student);
//		}
//		//学籍番号が変わっていない場合
//		if(uniqueCheck.getEmail().equals(email)) {
//			
//			Student student = new Student(id, name, uniqueCheck.getNumber(), address, birth, pass, email, uniqueCheck.getStatus());
//			studentRepository.save(student);
//		}
//		
		//ユニークチェック
		try {
		Student student = new Student(id, name, number, address, birth, pass, email, uniqueCheck.getStatus());
		studentRepository.save(student);
		return "redirect:/account";
		} catch(Exception E) {
			//エラーメッセージ
			model.addAttribute("uniqueError", "！学籍番号やメールアドレスが既に存在しているものとかぶっています");
			
			//再度情報を入れる
			Student student = studentRepository.findById(id).get();
			model.addAttribute("student", student);
			return "SOC_accountUpdate";
			}

	}


	@GetMapping("/account/{id}/freeze")
	public String accountBanConfirm(
			@PathVariable("id") Integer id,
			Model model
			) {
		Student student = studentRepository.findById(id).get();
		model.addAttribute(student);
		return "SOC_accountFreezeConfirm";
	}

	@PostMapping("/account/{id}/freeze")
	public String accountBan(
			@PathVariable("id") Integer id,
			Model model
			) {
		Student student = studentRepository.findById(id).get();
		
		java.util.Date utilDate = new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Date.valueOf(df.format(utilDate));
		
		student.setStatus(3);
		student.setBanDay(date);
		studentRepository.save(student);
		return "redirect:/account";
	}
}
