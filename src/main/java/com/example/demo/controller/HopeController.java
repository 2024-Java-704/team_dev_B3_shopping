package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.Hope;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.HopeRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HopeController {
	@Autowired
	AccountAndCart accountAndCart;

	@Autowired
	HttpSession session;
	@Autowired
	HopeRepository hopeRepository;

	@Autowired
	BookinfoRepository bookinfoRepository;

	@Autowired
	CategoriesRepository categoriesRepository;
	
	//リクエスト一覧画面を表示する
	@GetMapping("/hope")
	public String hopeList(Model model) {
		List<Hope> hope = hopeRepository.findByStatus(2);
//		for (Hope hopeList : hope) {
//			Bookinfo bookinfo = bookinfoRepository.findById(hopeList.getBookinfoId()).get();
//			hopeList.setTitle(bookinfo.getTitle());
//		}
		model.addAttribute("hope", hope);
		return "hopeList";
	}

	//リクエスト詳細画面を表示する
	@GetMapping("/hope/{id}/detail")
	public String hopeDetail(@PathVariable("id") Integer id,
			RedirectAttributes redirect,Model model) 
	{
		Hope hopeList = hopeRepository.findById(id).get();
		List<Bookinfo> bookinfo = bookinfoRepository.findByTitle(hopeList.getTitle());
		if(bookinfo.size()>0) {
			for(Bookinfo book:bookinfo) {
				redirect.addFlashAttribute("keyword",book.getTitle());
			}
			return"redirect:/items";
		}
//		hopeList.setTitle(bookinfo.getTitle());
//		hopeList.setAuthor(bookinfo.getAuthor());
//		hopeList.setPublisher(bookinfo.getPublisher());
		
		model.addAttribute("hopeList" , hopeList);
		
		return "hopeDetail";
	}

	//リクエスト申請画面を表示する
	@GetMapping("/hope/putup")
	public String hoepPutUpAccess(Model model) {
//		List<Categories> categories = categoriesRepository.findAll();
//		List<String> categoryName = new ArrayList<>();
//		for (Categories category : categories) {
//			categoryName.add(category.getCategoryName());
//		}
//		model.addAttribute("categoryName", categoryName);
		return "hopePutUp";
	}

	//リクエスト申請確認画面を表示する
	@GetMapping("/hope/putup/confirm")
	public String hopePutUpConfirm(
			@RequestParam("title") String title,
			@RequestParam("author") String author,
			@RequestParam("publisher") String publisher,
			Model model) {
		
		List<String> errorList = new ArrayList<>();
		
		//エラーチェック
		if (title.length() == 0) {
			errorList.add("タイトルを入力してください");
		}
		
		if (author.length() == 0) {
			errorList.add("作者名を入力してください");
		}
		
		if (publisher.length() == 0) {
			errorList.add("出版社を入力してください");
		}
		
		//エラー発生時はリクエスト申請に戻す
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
		return "hopePutup";}
		
		
		
//		Categories categories = categoriesRepository.findById(category_id).get();
//		String category = categories.getCategoryName();
		
		model.addAttribute("title", title);
		model.addAttribute("author", author);
		model.addAttribute("publisher", publisher);
		
		return "hopePutUpConfirm";
	}

	//リクエスト申請処理を行い、完了画面を表示する
	@PostMapping("/hope/putup/complete")
	public String hopePutUpComplete(@RequestParam("title") String title,
			@RequestParam("author") String author,
			@RequestParam("publisher") String publisher,
			Model model) {
		Hope hope=new Hope(accountAndCart.getId(), title , author , publisher , 1);
		if(hopeRepository.existsByStudentIdAndTitle(accountAndCart.getId(), title)== false) {
		hopeRepository.save(hope);
		return "hopePutUpComplete";}
		
		return "hopeList";
	}

	//リクエスト削除確認画面を表示する
	@GetMapping("/hope/delete/confirm")
	public String hopeDeleteConfirm() {
		return "hopeDeleteConfirm";
	}

	//リクエスト削除処理を行い、完了画面を表示する
	@PostMapping("/hope/delete/complete")
	public String hopeDeleteComplete() {
		return "hopeDeleteComplete";
	}

}
