package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class PutUpController {

	//出品申請画面の表示
	@GetMapping("/order")
	public String putUpAccess() {
		return "";
	}

	//ユーザーが入力した情報で出品申請するかの確認画面を表示する。
	@PostMapping("/order")
	public String putUpConfirm(
			@RequestParam("title") String title,
			@RequestParam("publisher") String publisher,
			@RequestParam("isbn") String isbn,
			@RequestParam("grade") String grade,
			@RequestParam("lecture") String lecture,
			Model model) {
		model.addAttribute("title", title);
		model.addAttribute("publisher", publisher);
		model.addAttribute("isbn", isbn);
		model.addAttribute("grade", grade);
		model.addAttribute("lecture", lecture);
		return "";
	}

	//申請処理を行い完了画面を表示する。
	@PostMapping("/order/success")
	public String putUpConfirmSuccess() {
		return "";
	}

	//出品履歴画面を表示する。
	@GetMapping("/order/history")
	public String putUpHistory() {
		return "";
	}
}
