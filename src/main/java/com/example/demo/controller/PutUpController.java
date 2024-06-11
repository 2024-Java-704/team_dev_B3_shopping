package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PutUpController {
	@Autowired
	BookinfoRepository bookinfoRepository;

	@Autowired
	AccountAndCart accountAndCart;

	@Autowired
	HttpSession session;

	//出品申請画面の表示
	@GetMapping("/order")
	public String putUpAccess() {
		return "order";
	}

	//ユーザーが入力した情報で出品申請するかの確認画面を表示する。
	@PostMapping("/order")
	public String putUpConfirm(
			@RequestParam("title") String title,
			@RequestParam("publisher") String publisher,
			@RequestParam("isbn") String isbn,
			@RequestParam("condition") String condition,
			@RequestParam("grade") String grade,
			@RequestParam("lecture") String lecture,
			@RequestParam("author") String author,
			@RequestParam("category_id") Integer category_id,
			Model model) {
		model.addAttribute("title", title);
		model.addAttribute("publisher", publisher);
		model.addAttribute("isbn", isbn);
		model.addAttribute("grade", grade);
		model.addAttribute("lecture", lecture);
		model.addAttribute("condition", condition);
		model.addAttribute("category_id", category_id);
		model.addAttribute("author", author);
		return "orderConfirm";
	}

	//申請処理を行い完了画面を表示する。
	@PostMapping("/order/success")
	public String putUpConfirmSuccess(
			@RequestParam("title") String title,
			@RequestParam("publisher") String publisher,
			@RequestParam("isbn") String isbn,
			@RequestParam("condition") String condition,
			@RequestParam("grade") Integer grade,
			@RequestParam("lecture") String lecture,
			@RequestParam("author") String author,
			@RequestParam("category_id") Integer category_id,
			Model model) {
		Bookinfo bookInfo = new Bookinfo(title, publisher, isbn, condition, grade, lecture, author, category_id);
		bookinfoRepository.save(bookInfo);
		return "orderSuccess";
	}

	//出品履歴画面を表示する。
	@GetMapping("/order/history")
	public String putUpHistory() {
		Integer accountId = accountAndCart.getId();
		salelistRepository.
		return "orderHistory";
	}
}
