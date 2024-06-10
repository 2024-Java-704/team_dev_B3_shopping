package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.SaleList;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.SaleListRepository;

@Controller
public class ProceedsController {
	
	@Autowired
	SaleListRepository saleListRepository;
	
	@Autowired
	AccountAndCart AccountAndCart;

	//売上一覧確認
	@GetMapping("/sold")
	public String proceedsAccess(Model model) {
		
		List<SaleList> sales = saleListRepository.findByItemStatusIs(2);
		sales.removeAll(saleListRepository.findByStudentIdIs(AccountAndCart.getId()));
		model.addAttribute("sales", sales);
		return "indexSold";
	}
	
	//売上詳細
	@GetMapping("/sold/{saleListId}/view")
	public String proceedsDetails(
			@PathVariable("saleListId") Integer id,
			Model model) {
		
		return "";
	}
}
