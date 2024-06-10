package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.repository.BookinfoRepository;

@Controller
public class PageViewController {
	
	@Autowired
	BookinfoRepository bookinfoRepository;
	
	//商品一覧画面表示
	@GetMapping("/items")
	public String index(Model model) {
		List<Bookinfo> books = bookinfoRepository.findAll();
		model.addAttribute("books", books);
		return "index";
	}
	
	//商品詳細画面表示
	@GetMapping("/items/detail")
	public String detail(@RequestParam("id") Integer id, Model model) {
		Bookinfo book = bookinfoRepository.findById(id).get();
		model.addAttribute("book", book);
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
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}

}
