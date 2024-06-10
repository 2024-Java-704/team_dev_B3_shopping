package com.example.demo.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class StaffPutUpController {

	//出品申請一覧画面を表示する。
	@GetMapping("/itemrequest")
	public String putUpAccess() {
		return "";
	}

	//出品申請詳細画面を表示する。
	@GetMapping("/itemrequest/{id}/detail")

	public String putUpDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請確認画面を表示する。
	@PostMapping("/itemrequest/{id}/approval")

	public String putUpConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請処理を行い、完了画面を表示する。
	@PostMapping("/itemrequest/{id}/approval")
	public String putUp(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請却下確認画面を表示する。
	@PostMapping("/itemrequest/{id}/rejected")
	public String putUpRejectConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請却下処理を行い、完了画面を表示する。
	@PostMapping("/itemrequest/{id}/rejected")
	public String putUpReject(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}
}
