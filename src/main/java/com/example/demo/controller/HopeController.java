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

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.Bookmark;
import com.example.demo.entity.Hope;
import com.example.demo.entity.Images;
import com.example.demo.entity.SaleList;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.BookmarkRepository;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.HopeRepository;
import com.example.demo.repository.ImagesRepository;
import com.example.demo.repository.SaleListRepository;

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
	
	@Autowired
	ImagesRepository imagesRepository;
	
	@Autowired
	SaleListRepository saleListRepository;
	
	@Autowired
	BookmarkRepository bookmarkRepository;

	//リクエスト一覧画面を表示する
	@GetMapping("/hope")
	public String hopeList(Model model) {
		List<Hope> hope = hopeRepository.findByStatus(2);
		//		for (Hope hopeList : hope) {
		//			Bookinfo bookinfo = bookinfoRepository.findById(hopeList.getBookinfoId()).get();
		//			hopeList.setTitle(bookinfo.getTitle());
		//		}
		if (hope.size() == 0) {
			model.addAttribute("errorMessage", "リクエストはありません");
		} else {
			model.addAttribute("hope", hope);
		}
		model.addAttribute("methodNum", 1);
		return "hopeList";
	}

	//自身のリクエスト一覧を表示する
	@GetMapping("/hope/mylist")
	public String myHopeList(Model model) {
		List<Hope> hope = hopeRepository.findByStatusAndStudentId(2, accountAndCart.getId());
		if (hope.size() == 0) {
			model.addAttribute("errorMessage", "自身のリクエストはありません");
		} else {
			model.addAttribute("hope", hope);
		}
		model.addAttribute("methodNum", 2);
		return "hopeList";
	}

	//リクエスト詳細画面を表示する
	@GetMapping("/hope/{id}/detail")
	public String hopeDetail(@PathVariable("id") Integer id, Model model) {
		Hope hopeList = hopeRepository.findById(id).get();
		List<Bookinfo> bookinfo = bookinfoRepository.findByTitle(hopeList.getTitle());
		if (bookinfo.size() > 0) {
			for (Bookinfo book : bookinfo) {
				//画像をセットする
				String imageString = book.getImageId() + "";
				Long imageLong = Long.parseLong(imageString);
				Images image = imagesRepository.findById(imageLong).get();
				book.setImageName(image.getName());
				SaleList sale = saleListRepository.findByBookInfoId(book.getId()).get(0);
				//ブックマークされているかを見る
				List<Bookmark> bookmark = bookmarkRepository.findBySalelistIdAndStudentId(sale.getId(),
						accountAndCart.getId());
				book.setBookmark(!bookmark.isEmpty());

			}
			model.addAttribute("books", bookinfo);
			return "/indexStyleCard";
		}
		//		hopeList.setTitle(bookinfo.getTitle());
		//		hopeList.setAuthor(bookinfo.getAuthor());
		//		hopeList.setPublisher(bookinfo.getPublisher());

		model.addAttribute("hopeList", hopeList);

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
			return "hopePutup";
		}

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
		Hope hope = new Hope(accountAndCart.getId(), title, author, publisher, 1);
		if (hopeRepository.existsByStudentIdAndTitle(accountAndCart.getId(), title) == false) {
			hopeRepository.save(hope);
			return "hopePutUpComplete";
		}

		return "hopeList";
	}

	//リクエスト削除確認画面を表示する
	@GetMapping("/hope/{id}/delete/confirm")
	public String hopeDeleteConfirm(@PathVariable("id") Integer id, Model model) {
		Hope hope = hopeRepository.findById(id).get();
		model.addAttribute("hope", hope);
		return "hopeDeleteConfirm";
	}

	//リクエスト削除処理を行い、完了画面を表示する
	@PostMapping("/hope/delete/complete")
	public String hopeDeleteComplete(@RequestParam("id") Integer id) {
		Hope hope = hopeRepository.findById(id).get();
		hopeRepository.delete(hope);
		return "hopeDeleteComplete";
	}

}
