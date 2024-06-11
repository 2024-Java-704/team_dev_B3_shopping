package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.BoughtHistory;
import com.example.demo.entity.SaleList;
import com.example.demo.repository.BoughtHistoryRepository;
import com.example.demo.repository.SaleListRepository;
import com.example.demo.repository.StudentRepository;

public class StaffFunctionController {
	
	@Autowired
	BoughtHistoryRepository boughtHistoryRepository;
	
	@Autowired
	SaleListRepository saleListRepository;
	
	@Autowired
	StudentRepository studentRepository;

	//職員マイページの表示。
	@GetMapping("/staffpage")
	public String staffMypage() {
		return "staffMypage";
	}
	
	//注文書籍一覧画面の表示。
	@GetMapping("/purchased")
	public String order(Model model) {
		List<BoughtHistory> boughtHistory = boughtHistoryRepository.findAll();
		model.addAttribute("boughtHistory", boughtHistory);
		return "staffOrder";
	}
	
	//注文書籍詳細画面の表示。
	@GetMapping("/purchased/{id}/detail")
	public String orderDetail(@PathVariable("id") Integer id, Model model) {
		BoughtHistory boughtDetail = boughtHistoryRepository.findById(id).get();
		model.addAttribute("boughtDetail", boughtDetail);
		
		SaleList saleList = saleListRepository.findById(boughtDetail.getSalelistId()).get();
		model.addAttribute("saleList", saleList);
		return "staffOrderDetail";
	}
	
	//注文ステータス変更処理、注文書籍一覧画面の表示。
	@PostMapping("/purchased/change")
	public String orderStatus(
			@RequestParam("id") Integer id,
			@RequestParam("status") String status) {
		
		//受渡準備完了
		if(status == "prepareHand") {
			BoughtHistory boughtHistory = boughtHistoryRepository.findById(id).get();
			boughtHistory.setDelivery(1);
			boughtHistoryRepository.save(boughtHistory);
		}
		
		//受渡完了
		if(status == "completeHand") {
			BoughtHistory boughtHistory = boughtHistoryRepository.findById(id).get();
			boughtHistory.setDelivery(2);
			boughtHistoryRepository.save(boughtHistory);
		}
		
		//発送完了
		if(status == "completeDelivery") {
			BoughtHistory boughtHistory = boughtHistoryRepository.findById(id).get();
			boughtHistory.setDelivery(3);
			boughtHistoryRepository.save(boughtHistory);
		}
		
		return "redirect:/purchased";
	}
	
	//売上受取申請一覧画面の表示。
	@GetMapping("/salesreceipt")
	public String proceeds(Model model) {
		List<SaleList> saleList = saleListRepository.findByItemStatus(3);
		model.addAttribute("saleList", saleList);
		
//		List<Student> studentList = new ArrayList<>();
//		for(SaleList sale : saleList) {
//			Integer studentId = sale.getStudentId();
//			Student student = studentRepository.findById(studentId).get();
//			studentList.add(student);
//		}
//		model.addAttribute("studentList", studentList);
		return "staffProceeds";
	}
	
	//売上受取申請詳細画面の表示。
	@GetMapping("/salesreceipt/detail")
	public String proceedsDetail(@RequestParam("id") Integer id, Model model) {
		SaleList sale = saleListRepository.findById(id).get();
		model.addAttribute("sale", sale);
		return "staffProceedsDetail";
	}
	
	//売上受取申請ステータスの変更処理、売上受取申請一覧画面の表示。
	@PostMapping("/salesreceipt/change")
	public String proceedsStatus(@RequestParam("id") Integer id) {
		SaleList sale = saleListRepository.findById(id).get();
		sale.setItemStatus(4);
		saleListRepository.save(sale);
		return "redirect:/salesreceipt";
	}
}
