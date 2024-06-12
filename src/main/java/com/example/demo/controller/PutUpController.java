package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.SaleList;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.SaleListRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PutUpController {
	@Autowired
	BookinfoRepository bookinfoRepository;

	@Autowired
	Bookinfo bookinfo;

	@Autowired
	SaleListRepository saleListRepository;

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

			@RequestParam("id") Integer id,
			@RequestParam("student_id") Integer student_id,
			@RequestParam("bookinfo_id") Integer bookinfo_id,
			@RequestParam("sale_day") LocalDate sale_day,
			@RequestParam("item_status") Integer item_status,
			@RequestParam("sale_method") Integer sale_method,
			Model model) {
		Bookinfo bookInfo = new Bookinfo(title, publisher, isbn, condition, grade, lecture, author, category_id);
		bookinfoRepository.save(bookInfo);

		SaleList saleList = new SaleList(id, student_id, bookinfo_id, sale_day, item_status, sale_method);
		saleListRepository.save(saleList);
		return "orderSuccess";
	}

	//出品履歴画面を表示する。
	@GetMapping("/order/history")
	public String putUpHistory(
			Model model) {
		Integer accountId = accountAndCart.getId();
		List<SaleList> salelist = saleListRepository.findByStudentId(accountId);//idと一致するものを取得
		for (SaleList list : salelist) {
			bookinfo = bookinfoRepository.findById(list.getBookInfo()).get();
			list.setTitle(bookinfo.getTitle());//title取得
		}

		//item_status - 1:出品中、2:売買済み、3:売上受取申請済み、4:売上受取済、5:出品申請中
		model.addAttribute("salelist", salelist);
		return "orderHistory";
	}
}
