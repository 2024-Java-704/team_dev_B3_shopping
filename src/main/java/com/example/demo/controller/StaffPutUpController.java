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
import com.example.demo.entity.Hope;
import com.example.demo.entity.Images;
import com.example.demo.entity.SaleList;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.HopeRepository;
import com.example.demo.repository.ImagesRepository;
import com.example.demo.repository.SaleListRepository;

@Controller
public class StaffPutUpController {

	@Autowired
	SaleListRepository saleListRepository;
	
	@Autowired
	BookinfoRepository bookinfoRepository;
	
	@Autowired
	ImagesRepository imagesRepository;
	
	@Autowired
	HopeRepository hopeRepository;
	
	//出品申請一覧画面の表示
	@GetMapping("/itemrequest")
	public String putUpAccess(Model model) {
		List<SaleList> saleList = saleListRepository.findByItemStatus(5);
		for(SaleList sale : saleList) {
			Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
			sale.setTitle(bookinfo.getTitle());
		}
		model.addAttribute("saleList", saleList);
		return "staffPutUp";
	}

	//出品申請詳細画面の表示
	@GetMapping("/itemrequest/{id}/detail")
	public String putUpDetail(
			@PathVariable("id") Integer id,
			Model model) {
		SaleList sale = saleListRepository.findById(id).get();
		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		sale.setTitle(bookinfo.getTitle());
		sale.setAuthor(bookinfo.getAuthor());
		sale.setIsbn(bookinfo.getIsbn());
		
		//画像の差し込み
		String imageString = bookinfo.getImageId() + "";
		Long imageLong = Long.parseLong(imageString);
		Images image = imagesRepository.findById(imageLong).get();
		sale.setImageName(image.getName());
		model.addAttribute("sale", sale);
		return "staffPutUpDetail";
	}

	//出品申請許可確認画面の表示
	@GetMapping("/itemrequest/approval")
	public String putUpConfirm(
			@RequestParam("id") Integer id,
			@RequestParam(name = "condition", defaultValue = "") String condition,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			Model model) {
		List<String> errorMessage = new ArrayList<>();
		
		if(condition.equals("")) {
			errorMessage.add("状態を入力してください");
		}
		
		if(price == null) {
			errorMessage.add("金額を入力してください");
		} else if(price <= 0) {
			errorMessage.add("金額を正しく入力してください");
		}
		
		if(errorMessage.size() > 0) {
//			System.out.print("動いてるよ");
			model.addAttribute("errorMessage", errorMessage);
			
			SaleList sale = saleListRepository.findById(id).get();
			Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
			sale.setTitle(bookinfo.getTitle());
			sale.setAuthor(bookinfo.getAuthor());
			sale.setIsbn(bookinfo.getIsbn());
			model.addAttribute("condition", condition);
			model.addAttribute("price", price);
			model.addAttribute("sale", sale);
			return "staffPutUpDetail";
		}
		
		SaleList sale = saleListRepository.findById(id).get();
		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		sale.setTitle(bookinfo.getTitle());
		sale.setAuthor(bookinfo.getAuthor());
		sale.setIsbn(bookinfo.getIsbn());
		model.addAttribute("sale", sale);
		
		model.addAttribute("condition", condition);
		model.addAttribute("price", price);
		model.addAttribute("operation", 1);
		return "staffPutUpConfirm";
	}

	//出品申請許可処理
	@PostMapping("/itemrequest/approval")
	public String putUp(
			@RequestParam("id") Integer id,
			@RequestParam("condition") String condition,
			@RequestParam("price") Integer price,
			Model model) {
		
		SaleList sale = saleListRepository.findById(id).get();
		sale.setItemStatus(1);
		saleListRepository.save(sale);
		
		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		bookinfo.setCondition(condition);
		bookinfo.setPrice(price);
		bookinfoRepository.save(bookinfo);
		
		model.addAttribute("operation", 1);
		return "staffPutUpComplete";
	}

	//出品申請却下確認画面の表示
	@GetMapping("/itemrequest/reject")
	public String putUpRejectConfirm(@RequestParam("id") Integer id, Model model) {
		SaleList sale = saleListRepository.findById(id).get();
		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		sale.setTitle(bookinfo.getTitle());
		sale.setAuthor(bookinfo.getAuthor());
		sale.setIsbn(bookinfo.getIsbn());
		model.addAttribute("sale", sale);
		model.addAttribute("operation", 2);
		return "staffPutUpConfirm";
	}

	//出品却下処理
	@PostMapping("/itemrequest/reject")
	public String putUpReject(@RequestParam("id") Integer id, Model model) {
		SaleList sale = saleListRepository.findById(id).get();
//		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		sale.setItemStatus(6);
		saleListRepository.save(sale);
//		saleListRepository.delete(sale);
//		bookinfoRepository.delete(bookinfo);
		
		model.addAttribute("operation", 2);
		return "staffPutUpComplete";
	}
	
	//リクエスト申請一覧画面を表示する
	@GetMapping("/staff/hope")
	public String hopeAccess(Model model) {
		List<Hope> hopeList = hopeRepository.findByStatus(1);
		for(Hope hope: hopeList) {
			Bookinfo bookinfo = bookinfoRepository.findById(hope.getBookinfoId()).get();
			hope.setTitle(bookinfo.getTitle());
		}
		if(hopeList.size() == 0) {
			model.addAttribute("errorMessage", "リクエスト申請はありません");
		}
		model.addAttribute("hopeList", hopeList);
		return "staffHopeList";
	}
	
	//リクエスト申請詳細画面を表示する
	@GetMapping("/staff/hope/{id}/detail")
	public String hopeDetail(@PathVariable("id") Integer id, Model model) {
		Hope hope = hopeRepository.findById(id).get();
		Bookinfo bookinfo = bookinfoRepository.findById(hope.getBookinfoId()).get();
		hope.setTitle(bookinfo.getTitle());
		hope.setAuthor(bookinfo.getAuthor());
		hope.setPublisher(bookinfo.getPublisher());
		model.addAttribute("hope", hope);
		return "staffHopeDetail";
	}
	
	//リクエスト申請許可処理を行い、完了画面を表示する
	@PostMapping("/staff/hope/{id}/approval")
	public String hopeApproval(@PathVariable("id") Integer id) {
		Hope hope = hopeRepository.findById(id).get();
		hope.setStatus(2);
		hopeRepository.save(hope);
		return "staffHopeApproval";
	}
	
	//リクエスト申請却下処理を行い、完了画面を表示する
	@PostMapping("/staff/hope/{id}/reject")
	public String hopeReject(@PathVariable("id") Integer id) {
		Hope hope = hopeRepository.findById(id).get();
		hopeRepository.delete(hope);
		return "staffHopeReject";
	}

}
