package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class StaffPutUpController {

	//出品申請一覧画面を表示する。
	@GetMapping("/itemrequest")
	public String putUpAccess() {
		return "";
	}

	//出品申請詳細画面を表示する。
	@GetMapping("/itemrequest/{id}/detail")
	public String putUpDetail() {
		return "";
	}

	//出品申請確認画面を表示する。
	@PostMapping("/itemrequest/{id}/approval")
	public String putUpConfirm() {
		return "";
	}

	//出品申請処理を行い、完了画面を表示する。
	@PostMapping("/itemrequest/{id}/approval")
	public String putUp() {
		return "";
	}

	//出品申請却下確認画面を表示する。
	@PostMapping("/itemrequest/{id}/rejected")
	public String putUpRejectConfirm() {
		return "";
	}

	//出品申請却下処理を行い、完了画面を表示する。
	@PostMapping("/itemrequest/{id}/rejected")
	public String putUpReject() {
		return "";
	}
}
