package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.Bookmark;
import com.example.demo.entity.SaleList;
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
	public String index(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {

		if (accountAndCart.getId() == null) {
			return "redirect:/login";
		}

		List<SaleList> saleList = saleListRepository.findByItemStatus(1);
		List<Bookinfo> books = new ArrayList<>();
    
		if (keyword.equals("")) { //キーワードが入力されなかった場合
			for (SaleList sale : saleList) {
				Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
				books.add(bookinfo);
			}
		} else { //キーワードが入力された場合
			for (SaleList sale : saleList) {
				List<Bookinfo> bookinfos = bookinfoRepository.findByIdAndTitleContaining(sale.getBookInfoId(), keyword);
				if (!bookinfos.isEmpty()) {
					books.add(bookinfos.get(0));
				}
			}

		}

		model.addAttribute("books", books);
		for(Bookinfo book : books) {
			System.out.println("タイトルは" + book.getTitle());
		}


		//仮登録チェック
		if (accountAndCart.getId() != null) {
			Student student = studentRepository.findById(accountAndCart.getId()).get();

			if (student.getStatus() == 5) {
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
		List<Bookmark> bookmark = bookmarkRepository.findByStudentId(accountAndCart.getId());


		List<Bookinfo> books = new ArrayList<>();

		for (Bookmark book : bookmark) {
			SaleList sale = saleListRepository.findById(book.getSalelistId()).get();
			Bookinfo info = bookinfoRepository.findById(sale.getBookInfoId()).get();
			info.setItemStatus(sale.getItemStatus());
			books.add(info);
		}

		model.addAttribute("books", books);
		return "bookmark";
	}

	//ブックマーク追加処理
	@PostMapping("/bookmark/add")
	public String bookMarkAdd(@RequestParam("id") Integer id) {
		List<SaleList> item = saleListRepository.findByBookInfoId(id);
		Integer itemId = item.get(0).getId();
		System.out.println("itemIdは" + itemId);

		List<Bookmark> bookmark = bookmarkRepository.findByStudentId(accountAndCart.getId());
//		List<Bookmark> bookmark = bookmarkRepository.findAll(); 上手くいかないコード
		for (Bookmark book : bookmark) {
			
			System.out.println("セールリストIDは" + book.getSalelistId());
			if (itemId == book.getSalelistId()) {
				return "redirect:/bookmark";
			}
		}

		Integer accountId = accountAndCart.getId();
		Bookmark book = new Bookmark(accountId, item.get(0).getId());
		bookmarkRepository.save(book);
		return "redirect:/bookmark";
	}
	
	//ブックマーク削除処理
//	@PostMapping("/bookmark/delete")
//	public String bookMarkDelete(@RequestParam("id") Integer id) {
//		Bookmark bookmark = bookmarkRepository.findById(id).get();
//		bookmarkRepository.deleteById(id);
//		return "redirect:/bookmark";
//	}

	//マイページ画面表示
	@GetMapping("/mypage")
	public String mypage(Model model) {
		Integer accountId = accountAndCart.getId();
		Student student = studentRepository.findById(accountId).get();
		model.addAttribute("student", student);
		return "mypage";
	}

}
