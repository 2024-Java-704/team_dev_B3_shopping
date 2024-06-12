package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class StaffPutUpController {

	//出品申請一覧画面の表示
	@GetMapping("/itemrequest")
	public String putUpAccess() {
		return "staffPutUp";
	}

	//出品申請詳細画面の表示
	@GetMapping("/putup/detail")
	public String putUpDetail(@RequestParam("id") Integer id) {
		return "staffPutUpDetail";
	}

	//出品申請許可確認画面の表示
	@GetMapping("/itemrequest/approval")
	public String putUpConfirm(@RequestParam("id") Integer id, Model model) {
		return "staffPutUpConfirm";
	}

	//出品申請許可処理
	@PostMapping("/itemrequest/approval")
	public String putUp(
			@RequestParam("id") Integer id,
			@RequestParam("operation") String operation) {
		return "staffPutUpComplete";
	}

	//出品申請却下確認画面の表示
	@GetMapping("/itemrequest/reject")
	public String putUpRejectConfirm(@RequestParam("id") Integer id, Model model) {
		return "staffPutUpConfirm";
	}

	//出品却下処理
	@PostMapping("/itemrequest/reject")
	public String putUpReject(@RequestParam("id") Integer id) {
		return "staffPutUpReject";
	}
}
