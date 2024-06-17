package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HopeController {
	
	//リクエスト一覧画面を表示する
	@GetMapping("/hope")
	public String hopeList() {
		return "hopeList";
	}
	
	//リクエスト詳細画面を表示する
	@GetMapping("/hope/{id}/detail")
	public String hopeDetail() {
		return "hopeDetail";
	}
	
	//リクエスト申請画面を表示する
	@GetMapping("/hope/putup")
	public String hoepPutUpAccess() {
		return "hopePutUp";
	}
	
	//リクエスト申請確認画面を表示する
	@GetMapping("/hope/putup/confirm")
	public String hopePutUpConfirm() {
		return "hopePutUpConfirm";
	}
	
	//リクエスト申請処理を行い、完了画面を表示する
	@PostMapping("/hope/putup/complete")
	public String hopePutUpComplete() {
		return "hopePutUpComplete";
	}
	
	//リクエスト削除確認画面を表示する
	@GetMapping("/hope/delete/confirm")
	public String hopeDeleteConfirm() {
		return "hopeDeleteConfirm";
	}
	
	//リクエスト削除処理を行い、完了画面を表示する
	@PostMapping("/hope/delete/complete")
	public String hopeDeleteComplete(){
		return "hopeDeleteComplete";
	}

}
