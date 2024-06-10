package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminFunctionController {//returnはhtml完成次第追記

	//管理者用のマイページの表示
	@GetMapping("/adminpage")
	public String adminPage() {
		return "login";
	}

	//職員一覧画面の表示
	@GetMapping("/stafflist")
	public String staffAll() {
		return "";
	}

	//職員詳細画面の表示
	@GetMapping("/stafflist/{id}/detail")
	public String adminUpdateDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//職員アカウント削除画面の表示
	@GetMapping("/stafflist/{id}/delete")
	public String staffDelete(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//職員アカウント追加画面の表示
	@GetMapping("/stafflist/{id}/add")
	public String adminAdd(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//職員アカウント更新画面の表示
	@GetMapping("/stafflist/update")
	public String staffUpdeteInput() {
		return "";
	}

	//職員アカウント更新確認画面の表示
	@GetMapping("/stafflist/{id}/update")
	public String adminUpdateConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//職員アカウント情報を更新し、アカウント一覧画面に戻る
	@PostMapping("/stafflist/{id}/update")
	public String adminUpdateExecution(
			@PathVariable("id") Integer id,
			@RequestParam("staff_name") String name,
			@RequestParam("staff_email") String email,
			@RequestParam("staff_pass") String pass,
			@RequestParam("staff_number") String number,
			Model model) {
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("pass", pass);
		model.addAttribute("number", number);
		return "";
	}
}
