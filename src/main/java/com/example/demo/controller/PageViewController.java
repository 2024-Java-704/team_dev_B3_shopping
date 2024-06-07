package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageViewController {
	
	//商品一覧画面表示
	@GetMapping("/items")
	public String index() {
		return "index";
	}
	
	//商品詳細画面表示
	@GetMapping("/items/{id}")
	public String detail(@PathVariable("id") String id) {
		return "detail";
	}
	
	//ブックマーク画面表示
	@GetMapping("/bookmark")
	public String bookMark() {
		return "bookmark";
	}
	
	//ブックマーク追加処理
	@PostMapping("/bookmark/{id}")
	public String bookMarkAdd(@PathVariable("id") String id) {
		return "bookmark";
	}
	
	//マイページ画面表示
	@GetMapping("/mypage/{id}")
	public String mypage(@PathVariable("id") String id) {
		return "mypage";
	}

}
