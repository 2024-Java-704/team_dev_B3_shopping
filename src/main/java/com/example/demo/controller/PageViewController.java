package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.Bookmark;
import com.example.demo.entity.Student;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.SaleListRepository;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageViewController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	AccountAndCart accountAndCart;
	
	@Autowired
	BookinfoRepository bookinfoRepository;
	
	@Autowired
	SaleListRepository saleListRepository;
	
	@Autowired
	BookmarkRepository bookmarkRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	//商品一覧画面表示
	@GetMapping("/items")
	public String index(Model model) {
		List<Bookinfo> books = bookinfoRepository.findAll();
		model.addAttribute("books", books);
		
		if(accountAndCart.getId() != null) {
		Student student = studentRepository.findById(accountAndCart.getId()).get();
		
			if(student.getStatus() == 5) {
				model.addAttribute("deniedMessage", "申請が却下されました");
			}
		}
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
	public String bookMark(Model model) {
		List<Bookmark> books = bookmarkRepository.findAll();
		model.addAttribute("books", books);
		return "bookmark";
	}
	
	//ブックマーク追加処理
	@PostMapping("/bookmark/add")
	public String bookMarkAdd(@RequestParam("id") Integer id) {
		
		return "bookmark";
	}
	
	//マイページ画面表示
	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}

}
