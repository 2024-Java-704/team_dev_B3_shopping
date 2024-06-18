package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Bookinfo;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Images;
import com.example.demo.entity.SaleList;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ImagesRepository;
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

	@Autowired
	CategoriesRepository categoriesRepository;

	@Autowired
	ImagesRepository imagesRepository;

	private static final String UPLOAD_DIR = "src/main/resources/static/img"; // アップロード先のディレクトリ

	//出品申請画面の表示
	@GetMapping("/order")
	public String putUpAccess(
			@RequestParam(name = "title", defaultValue = "") String title,
			@RequestParam(name = "author", defaultValue = "") String author,
			@RequestParam(name = "publisher", defaultValue = "") String publisher,
			Model model) {
		if (title != "" && author != "" && publisher != "") {
			model.addAttribute("title", title);
			model.addAttribute("author", author);
			model.addAttribute("publisher", publisher);
		}
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

		List<String> errorList = new ArrayList<>();

		//エラーチェック
		if (title.length() == 0) {
			errorList.add("タイトルを入力してください");
		}

		if (publisher.length() == 0) {
			errorList.add("出版社を入力してください");
		}

		if (isbn.length() == 0) {
			errorList.add("ISBNを入力してください");
		}

		if (condition.length() == 0) {
			errorList.add("本の状態を入力してください");
		}

		if (grade.length() == 0) {
			errorList.add("学年を入力してください");
		}

		if (lecture.length() == 0) {
			errorList.add("使用した講義名を入力してください");
		}

		if (author.length() == 0) {
			errorList.add("作者名を入力してください");
		}

		// エラー発生時は出品申請に戻す
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "order";
		}

		Categories categories = categoriesRepository.findById(category_id).get();
		String category = categories.getCategoryName();

		model.addAttribute("title", title);
		model.addAttribute("publisher", publisher);
		model.addAttribute("isbn", isbn);
		model.addAttribute("grade", grade);
		model.addAttribute("lecture", lecture);
		model.addAttribute("condition", condition);
		model.addAttribute("category", category);
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

			//			@RequestParam("id") Integer id,
			//			@RequestParam("student_id") Integer student_id,
			//			@RequestParam("bookinfo_id") Integer bookinfo_id,
			//			@RequestParam("sale_day") LocalDate sale_day,
			//			@RequestParam("item_status") Integer item_status,
			//			@RequestParam("sale_method") Integer sale_method,
			Model model) {
		Bookinfo bookInfo = new Bookinfo(title, publisher, isbn, condition, grade, lecture, author, category_id);
		bookinfoRepository.save(bookInfo);

		bookinfo = bookinfoRepository.findById(bookInfo.getId()).get();
		SaleList saleList = new SaleList(accountAndCart.getId(), bookinfo.getId(), LocalDate.now());
		saleList.setItemStatus(5);
		saleList.setSaleMethod(1);//学生が窓口へ商品を受け渡す方法について、shema参照
		//		Integer itemStatus, Integer saleMethod

		saleListRepository.save(saleList);

		model.addAttribute("bookId", bookInfo.getId());
		return "orderSuccess";
	}

	//商品画像のアップロード画面表示
	@GetMapping("/order/{id}/imgUp")
	public String imgUp(
			@PathVariable("id") Integer id,
			Model model) {
		model.addAttribute("bookId", id);
		return "Upload-BookImg";
	}

	//画像のアップロード完了後、登録申請の完了画面表示
	@PostMapping("/order/{id}/imgUp")
	public String imgUploadSuccess(
			@PathVariable("id") Integer id,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			Model model) {

		try {
			// アップロードディレクトリが存在しない場合、作成
			File uploadDir = new File(UPLOAD_DIR);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// 画像ファイルの保存先パス
			String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();

			// 画像ファイルをディスクに保存
			Path destination = new File(filePath).toPath();
			Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

			// データベースにファイルメタデータを保存
			Images imageEntity = new Images();
			imageEntity.setName(file.getOriginalFilename());
			imageEntity.setFilePath(filePath);
			imagesRepository.save(imageEntity);

			//StudentEntityにimageIdを保存
			String imageIdString = String.valueOf(imageEntity.getId());
			Integer imageId = Integer.valueOf(imageIdString);

			Bookinfo book = bookinfoRepository.findById(id).get();
			book.setImageId(imageId);
			bookinfoRepository.save(book);

			redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
			return "redirect:/order/{id}/imgUp";
		}

		model.addAttribute("bookId", id);
		return "Upload-BookImgSuccess";
	}

	//出品履歴画面を表示する。
	@GetMapping("/order/history")
	public String putUpHistory(
			Model model) {
		Integer accountId = accountAndCart.getId();
		List<SaleList> salelist = saleListRepository.findByStudentId(accountId);//idと一致するものを取得

		if (salelist.size() == 0) {
			model.addAttribute("errorMessage", "出品履歴がありません");
			return "orderHistory";
		}

		for (SaleList list : salelist) {
			bookinfo = bookinfoRepository.findById(list.getBookInfoId()).get();
			list.setTitle(bookinfo.getTitle());//title取得
		}

		//item_status - 1:出品中、2:売買済み、3:売上受取申請済み、4:売上受取済、5:出品申請中
		model.addAttribute("salelist", salelist);
		return "orderHistory";
	}

	//出品履歴画面をソートする。
	@GetMapping("/order/history/{sortNum}")
	public String putUpHistorySort(
			@PathVariable("sortNum") int num,
			Model model) {
		Integer accountId = accountAndCart.getId();
		List<SaleList> salelist;
		switch (num) {
		case 1: {
			salelist = saleListRepository.findByStudentIdOrderBySaleDayAsc(accountId);//idと一致するものを取得(昇順)
			break;
		}

		case 2: {
			salelist = saleListRepository.findByStudentIdOrderBySaleDayDesc(accountId);//idと一致するものを取得(降順)
			break;
		}
		default: {
			salelist = saleListRepository.findByStudentId(accountId);//idと一致するものを取得
			break;
		}
		}

		if (salelist.size() == 0) {
			model.addAttribute("errorMessage", "出品履歴がありません");
			return "orderHistory";
		}

		for (SaleList list : salelist) {
			bookinfo = bookinfoRepository.findById(list.getBookInfoId()).get();
			list.setTitle(bookinfo.getTitle());//title取得
		}

		//item_status - 1:出品中、2:売買済み、3:売上受取申請済み、4:売上受取済、5:出品申請中
		model.addAttribute("salelist", salelist);
		return "orderHistory";
	}

	//出品履歴詳細画面を表示する。
	@GetMapping("/order/history/detail")
	public String orderDetail(
			@RequestParam("id") Integer Id,
			Model model) {
		bookinfo = bookinfoRepository.findById(Id).get();

		try {
			//画像
			String imageString = bookinfo.getImageId() + "";
			Long imageLong = Long.parseLong(imageString);
			Images image = imagesRepository.findById(imageLong).get();
			bookinfo.setImageName(image.getName());
			model.addAttribute("book", bookinfo);
		} catch (Exception e) {
			//画像がない場合そのまま無視する
		}

		model.addAttribute("book", bookinfo);
		SaleList saleList = saleListRepository.findByBookInfoId(bookinfo.getId()).get(0);
		model.addAttribute("saleList", saleList);

		return "orderHistoryDetail";
	}

	//出品履歴をキャンセルする。
	@PostMapping("/order/history/{id}/delete")
	public String delteHistory(
			@PathVariable("id") Integer id,
			Model model) {
		SaleList deleteSale = saleListRepository.findById(id).get();
		saleListRepository.delete(deleteSale);

		return "redirect:/order/history";
	}
}
