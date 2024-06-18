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
import com.example.demo.entity.BoughtHistory;
import com.example.demo.entity.Images;
import com.example.demo.entity.SaleList;
import com.example.demo.entity.Student;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.BoughtHistoryRepository;
import com.example.demo.repository.ImagesRepository;
import com.example.demo.repository.SaleListRepository;
import com.example.demo.repository.StudentRepository;

@Controller
public class StaffFunctionController {

	@Autowired
	BoughtHistoryRepository boughtHistoryRepository;

	@Autowired
	SaleListRepository saleListRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	BookinfoRepository bookinfoRepository;
	
	@Autowired
	ImagesRepository imagesRepository;

	//職員マイページの表示。
	@GetMapping("/staffpage")
	public String staffMypage() {
		return "staffMypage";
	}

	//注文書籍一覧画面の表示。
	@GetMapping("/purchased")
	public String order(Model model) {
		List<SaleList> saleList = saleListRepository.findByItemStatus(2);

		for (SaleList sale : saleList) {
			Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
			sale.setTitle(bookinfo.getTitle());

			BoughtHistory boughtHisory = boughtHistoryRepository.findBySaleListId(sale.getId());
			sale.setAccept(boughtHisory.getAccept());
			sale.setDelivery(boughtHisory.getDelivery());
		}

		if (saleList.size() == 0) {
			model.addAttribute("errorMessage", "注文がありません");
		}

		model.addAttribute("saleList", saleList);
		return "staffOrder";
	}

	//注文書籍詳細画面の表示。
	@GetMapping("/purchased/detail")
	public String orderDetail(@RequestParam("id") Integer id, Model model) {
		SaleList sale = saleListRepository.findById(id).get();
		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		
		String imageString = bookinfo.getImageId() + "";
		Long imageLong = Long.parseLong(imageString);
		Images image = imagesRepository.findById(imageLong).get();
		bookinfo.setImageName(image.getName());
		
		model.addAttribute("bookinfo", bookinfo);

		List<BoughtHistory> boughtHistory = boughtHistoryRepository.findBySalelistId(sale.getId());
		BoughtHistory bought = boughtHistory.get(0);
		model.addAttribute("bought", bought);
		return "staffOrderDetail";
	}

	//注文ステータス変更処理、注文書籍一覧画面の表示。
	@PostMapping("/purchased/change")
	public String orderStatus(
			@RequestParam("id") Integer id,
			@RequestParam("status") String status) {

		//受渡準備完了
		if (status == "prepareHand") {
			BoughtHistory boughtHistory = boughtHistoryRepository.findById(id).get();
			boughtHistory.setDelivery(1);
			boughtHistoryRepository.save(boughtHistory);
		}

		//受渡完了
		if (status == "completeHand") {
			BoughtHistory boughtHistory = boughtHistoryRepository.findById(id).get();
			boughtHistory.setDelivery(2);
			boughtHistoryRepository.save(boughtHistory);
		}

		//発送完了
		if (status == "completeDelivery") {
			BoughtHistory boughtHistory = boughtHistoryRepository.findById(id).get();
			boughtHistory.setDelivery(3);
			boughtHistoryRepository.save(boughtHistory);
		}

		return "redirect:/purchased";
	}

	//売上受取申請一覧画面の表示。
	@GetMapping("/salesreceipt")
	public String proceeds(@RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {
		List<SaleList> saleList = new ArrayList<>();

		if (keyword.equals("")) {
			saleList = saleListRepository.findByItemStatus(3);
		} else {
			List<Student> studentList = studentRepository.findByNameContaining(keyword);
			for(Student student: studentList) {
				saleList = saleListRepository.findByItemStatusAndStudentId(3, student.getId());
			}
			model.addAttribute("search", keyword);
		}
		
		for (SaleList sale : saleList) {
			Student student = studentRepository.findById(sale.getStudentId()).get();
			String name = student.getName();
			sale.setName(name);

			Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
			Integer price = bookinfo.getPrice();
			sale.setPrice(price);
		}

		model.addAttribute("saleList", saleList);
		return "staffProceeds";
	}

	//売上受取申請詳細画面の表示。
	@GetMapping("/salesreceipt/detail")
	public String proceedsDetail(@RequestParam("id") Integer id, Model model) {
		SaleList sale = saleListRepository.findById(id).get();

		Student student = studentRepository.findById(sale.getStudentId()).get();
		String name = student.getName();
		sale.setName(name);

		Bookinfo bookinfo = bookinfoRepository.findById(sale.getBookInfoId()).get();
		Integer price = bookinfo.getPrice();
		sale.setPrice(price);

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
