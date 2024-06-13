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
import com.example.demo.entity.SaleList;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.SaleListRepository;

@Controller
public class ProceedsController {
	
	@Autowired
	SaleListRepository saleListRepository;
	
	@Autowired
	BookinfoRepository bookinfoRepository;
	
	@Autowired
	AccountAndCart AccountAndCart;

	//売上一覧確認
	@GetMapping("/sold")
	public String proceedsAccess(Model model) {
		
//		if(AccountAndCart.getId() == null) {
//			System.out.println("ログインしてないよ");
//			return "needLogin";
//		}
		
		List<SaleList> sales = saleListRepository.findByItemStatusIs(2);
			sales.removeAll(saleListRepository.findByStudentIdIsNot(AccountAndCart.getId()));
			
		for(SaleList sale : sales) {
			Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get(); 
				sale.setTitle(bookinfo.getTitle());
				sale.setPrice(bookinfo.getPrice());
		}
		
		model.addAttribute("sales", sales);
		return "indexSold";
	}
	
	//売上詳細
	@GetMapping("/sold/{saleListId}/details")
	public String proceedsDetails(
			@PathVariable("saleListId") Integer id,
			Model model) {
		Bookinfo book = bookinfoRepository.findById(id).get();
		model.addAttribute("book", book);
		return "detail";
	}
	
	//売上受け取り申請
	@GetMapping("/request")
	public String paymentOrder(Model model) {
		if(AccountAndCart.getId() == null) {
			System.out.println("ログインしてないよ");
			return "needLogin";
		}
		
		List<SaleList> sales = saleListRepository.findByItemStatusIs(2);
			sales.removeAll(saleListRepository.findByStudentIdIsNot(AccountAndCart.getId()));
			
		for(SaleList sale : sales) {
			Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get(); 
				sale.setTitle(bookinfo.getTitle());
				sale.setPrice(bookinfo.getPrice());
		}
		model.addAttribute("sales", sales);
		return "paymentOrder";
	}
	
	//申請確認
	@PostMapping("/request")
	public String paymentOrderConfirm(
			@RequestParam(name = "wantsId", defaultValue = "0") Integer wantsId,
			@RequestParam(name = "wantsPrice", defaultValue = "0") Integer wantsPrice,
			Model model
			) {
		
		if(wantsId.equals(0) || wantsPrice.equals(0)) {
			model.addAttribute("nullMessage", "！選択してください");
			return "paymentOrder";
		}
		
		model.addAttribute("wantsId", wantsId);
		model.addAttribute("wantsPrice", wantsPrice);
		return "paymentOrderConfirm";
	}
	
	//申請完了&テーブル操作
	@PostMapping("/request/complete")
	public String paymentOrderComplete(
			@RequestParam(name = "wantsId", defaultValue = "") Integer id,
			@RequestParam(name = "wantsPrice", defaultValue = "") Integer wantsPrice,
			Model model
			) {
		//アイテムのステータスを3に
		SaleList sales = saleListRepository.findById(id).get();
		sales.setItemStatus(3);
		saleListRepository.save(sales);
		
		model.addAttribute("orderPrice", wantsPrice);
		return "paymentOrderComplete";
	}
}
