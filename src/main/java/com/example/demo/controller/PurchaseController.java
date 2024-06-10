package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Bookinfo;
import com.example.demo.model.AccountAndCart;
import com.example.demo.model.CartItems;
import com.example.demo.repository.BookinfoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PurchaseController {

	@Autowired
	HttpSession session;
	
	@Autowired
	CartItems cartItems;

	@Autowired
	AccountAndCart accountAndCart;

	@Autowired
	BookinfoRepository bookinfoRepository;

	//カートを表示する
	@GetMapping("/cart")
	public String cartAccess(Model nodel) {
		return "cart";
	}

	//指定した商品をカートに追加する
	@PostMapping("/cart/add")
	public String cartAdd(@RequestParam("bookinfoId") Integer bookinfoId) {

		Bookinfo bookinfo = bookinfoRepository.findById(bookinfoId).get();
		String name = bookinfo.getTitle();
		CartItems cartItems = new CartItems(bookinfoId, name);
		accountAndCart.add(cartItems);
		return "cart";
	}

	//　指定した商品をカートから削除
	@PostMapping()
	public String cartDelete(@RequestParam("bookinfoId") Integer bookinfoId) {
//		CartItems cartItems = bookinfoRepository.findById(bookinfoId).get();
//		cartItems.delete(bookinfoId);
		return "redirect:/cart";
	}


	@GetMapping("/purchase/order")
	public String purchaseAccess(Model model) {
		
		return "perchaseCheck";
	}
//	↑購入画面の表示

}
