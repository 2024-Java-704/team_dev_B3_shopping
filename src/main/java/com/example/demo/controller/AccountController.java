package com.example.demo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Student;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	@Autowired
	HttpSession session;
	@Autowired
	AccountAndCart accountandcart;
	@Autowired
	StudentRepository studentRepository;

	// 会員登録画面の表示
	@GetMapping("/new/users")
	public String studentAdd() {
		return "adduser";
	}

	//入力された情報を新規登録確認画面に表示する
	@PostMapping("/new/users")
	public String studentConfirm(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("address") String address,
			@RequestParam("birth") Date birth,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			Model model) {
		List<String> errorList = new ArrayList<>();
		List<Student> userList = studentRepository.findByNumber(number);
		model.addAttribute("name", name);
		model.addAttribute("number", number);
		model.addAttribute("address", address);
		model.addAttribute("birth", birth);
		model.addAttribute("password", password);
		model.addAttribute("email", email);
		//エラーチェック
		if (userList != null && userList.size() > 0) {
			errorList.add("	既に登録されている学籍番号です");
		}
		if (name.length() == 0) {
			errorList.add("名前を入力してください");
		}

		if (number.length() == 0) {
			errorList.add("学籍番号を入力してください");
		}

		if (address.length() == 0) {
			errorList.add("住所を入力してください");
		}

		if (birth == null) {
			errorList.add("生年月日を入力してください");
		}

		if (email.length() == 0) {
			errorList.add("メールアドレスを入力してください");
		}

		if (password.length() == 0) {
			errorList.add("パスワードを入力してください");
		}

		// エラー発生時は新規登録に戻す
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);

			return "adduser";
		}

		return "adduserconfirm";
	}

	@PostMapping("/new/users/complete")
	public String studentAddRequest(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("address") String address,
			@RequestParam("birth") Date birth,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			Model model) {
		model.addAttribute("name", name);
		model.addAttribute("number", number);
		model.addAttribute("address", address);
		model.addAttribute("birth", birth);
		model.addAttribute("password", password);
		model.addAttribute("email", email);

		Student student = new Student(name, number, address, birth, password, email);
		studentRepository.save(student);

		return "redirect:/login";
	}

	//ログイン画面の表示
	@GetMapping({ "/login", "/logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	//ログイン処理
	@PostMapping("/login")
	public String studentLogin(
			@RequestParam("number") String number,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {

		List<String> errorList = new ArrayList<>();

		//エラーチェック
		if (email.length() == 0 || number.length() == 0) {
			errorList.add("メールアドレス又は学籍番号を入力してください");
		}

		if (password.length() == 0) {
			errorList.add("パスワードを入力してください");

		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
		}

		List<Student> emailStudent = studentRepository.findByEmailAndPass(email, password);
		List<Student> numberStudent = studentRepository.findByNumberAndPass(number, password);

		if (emailStudent.size() == 0 || numberStudent.size() == 0) {

			// DBに存在しなかった場合
			model.addAttribute("message", "メールアドレス又は学籍番号とパスワードが一致しませんでした");
			return "login";
		}

		//セッションにIDと名前を登録
		if (emailStudent.size() > 0) {
			Student student = emailStudent.get(0);
			accountandcart.setId(student.getId());
			accountandcart.setName(student.getName());
		}

		if (numberStudent.size() > 0) {
			Student student = numberStudent.get(0);
			accountandcart.setId(student.getId());
			accountandcart.setName(student.getName());
		}

		return "redirect:/items";
	}
}
