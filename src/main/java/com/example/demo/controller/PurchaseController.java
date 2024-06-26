package com.example.demo.controller;

import java.time.LocalDateTime;
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
import com.example.demo.entity.BoughtCertificate;
import com.example.demo.entity.BoughtHistory;
import com.example.demo.entity.Images;
import com.example.demo.entity.SaleList;
import com.example.demo.entity.Student;
import com.example.demo.model.AccountAndCart;
import com.example.demo.model.CartItems;
import com.example.demo.repository.BookinfoRepository;
import com.example.demo.repository.BoughtCertificateRepository;
import com.example.demo.repository.BoughtHistoryRepository;
import com.example.demo.repository.ImagesRepository;
import com.example.demo.repository.SaleListRepository;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PurchaseController {

	@Autowired
	HttpSession session;

	@Autowired
	Student student;

	@Autowired
	SaleList saleList;

	@Autowired
	BoughtHistory boughtHistory;

	@Autowired
	CartItems cartItems;

	@Autowired
	AccountAndCart accountAndCart;

	@Autowired
	BoughtCertificate boughtCertificate;

	@Autowired
	BookinfoRepository bookinfoRepository;

	@Autowired
	BoughtHistoryRepository boughtHistoryRepository;

	@Autowired
	BoughtCertificateRepository boughtCertificateRepository;

	@Autowired
	SaleListRepository saleListRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	ImagesRepository imagesRepository;

	//カートを表示する
	@GetMapping("/cart")
	public String cartAccess(Model model) {
		Student student = studentRepository.findById(accountAndCart.getId()).get();
		model.addAttribute("student", student);

		Integer sum = 0;
		for (CartItems cartItem : accountAndCart.getCartItems()) {
			sum = sum + cartItem.getPrice();
		}
		model.addAttribute("sum", sum);

		return "cart";
	}

	//指定した商品をカートに追加する(RequestParam)
	@PostMapping("/cart/add")
	public String cartAdd(@RequestParam("bookinfoId") Integer bookinfoId, Model model) {

		Bookinfo bookinfo = bookinfoRepository.findById(bookinfoId).get();
		//		String name = bookinfo.getTitle();

		CartItems cartItems = new CartItems(bookinfo.getId(), bookinfo.getTitle(), bookinfo.getPrice());
		accountAndCart.add(cartItems);

		Integer sum = 0;
		for (CartItems cartItem : accountAndCart.getCartItems()) {
			sum = sum + cartItem.getPrice();
		}
		model.addAttribute("sum", sum);

		return "redirect:/cart";
	}

	//指定した商品をカートに追加する(PathVariable)
	@GetMapping("/cart/{id}/add")
	public String cartAddPathVariable(
			@PathVariable("id") Integer bookinfoId,
			Model model) {

		Bookinfo bookinfo = bookinfoRepository.findById(bookinfoId).get();
		//		String name = bookinfo.getTitle();

		CartItems cartItems = new CartItems(bookinfo.getId(), bookinfo.getTitle(), bookinfo.getPrice());
		accountAndCart.add(cartItems);

		Integer sum = 0;
		for (CartItems cartItem : accountAndCart.getCartItems()) {
			sum = sum + cartItem.getPrice();
		}
		model.addAttribute("sum", sum);

		return "redirect:/cart";
	}

	//　指定した商品をカートから削除

	@GetMapping("/purchase/{id}/delete")
	public String cartDelete(
			@PathVariable("id") Integer id) {
		accountAndCart.del(id);
		return "redirect:/cart";
	}

	@GetMapping("/purchase/order")
	public String purchaseAccess(Model model) {
		if (accountAndCart.getCartItems().size() == 0) {
			return "redirect:/cart";
		}
		return "purchaseAccess";
	}
	//	↑購入画面の表示

	@GetMapping("/book")
	public String purchaseConfirm(@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "recieve", defaultValue = "") Integer recieve,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "telephone", defaultValue = "") String telephone,
			@RequestParam(name = "payment", defaultValue = "") Integer payment,
			@RequestParam(name = "cardName", defaultValue = "") String cardName,
			@RequestParam(name = "cardNumber", defaultValue = "") String cardNumber,
			@RequestParam(name = "cardDate", defaultValue = "") String cardDate,
			@RequestParam(name = "cardCode", defaultValue = "") String cardCode,
			Model model) {

		if (payment == 1) {
			List<String> errorMessage = new ArrayList<>();

			if (cardName.equals("")) {
				errorMessage.add("名義人を入力してください");
				model.addAttribute("cardName", cardName);
			}
			if (cardNumber.equals("")) {
				errorMessage.add("カード番号を入力してください");
				model.addAttribute("cardNumber", cardNumber);
			}
			if (cardDate.equals("")) {
				errorMessage.add("有効期限を入力してください");
				model.addAttribute("cardDate", cardDate);
			}
			if (cardCode.equals("")) {
				errorMessage.add("セキュリティコードを入力してください");
				model.addAttribute("cardCode", cardCode);
			}
			
			if(errorMessage.size() > 0) {
				model.addAttribute("errorMessage", errorMessage);
				return "purchaseAccess";
			}
		}

		String receiveString;
		if (recieve == 1) {
			receiveString = "郵便";
		} else {
			receiveString = "窓口受け取り";
		}

		String paymentString;
		if (payment == 1) {
			paymentString = "クレジットカード";
		} else {
			paymentString = "窓口支払い";
		}

		boughtHistory = new BoughtHistory(payment, recieve);

		model.addAttribute("name", name);
		model.addAttribute("recieve", receiveString);
		model.addAttribute("address", address);
		model.addAttribute("telephone", telephone);
		model.addAttribute("payment", paymentString);

		return "purchase";

	}
	//	↑購入確認画面の表示

	@PostMapping("/book/complete")
	private String purchaseComplete(Model model) {

		for (CartItems cartItems : accountAndCart.getCartItems()) {
			//boughtHistory.setStudentId(accountAndCart.getId());
			//boughtHistory.setSalelistId(cartItems.getId());

			boughtHistory = new BoughtHistory(accountAndCart.getId(), cartItems.getId(), boughtHistory.getPayment(),
					boughtHistory.getAccept());
			boughtHistory.setDelivery(4);

			boughtHistoryRepository.save(boughtHistory);

			SaleList updateSaleList = saleListRepository.findById(cartItems.getId()).get();
			updateSaleList.setItemStatus(2);
			saleListRepository.save(updateSaleList);

			boughtCertificate = new BoughtCertificate(boughtHistory.getSalelistId(), LocalDateTime.now());
			boughtCertificateRepository.save(boughtCertificate);

		}

		accountAndCart.clear(cartItems);

		BoughtHistory boughtHistory2 = boughtHistoryRepository.findByStudentIdOrderByIdDesc(accountAndCart.getId())
				.get(0);
		String number = accountAndCart.getId().toString() + "000" + boughtHistory2.getId();
		model.addAttribute("number", number);

		return "purchaseComplete";

	}

	// ↑購入完了画面の表示

	//	↓購入履歴画面の表示
	@GetMapping("/buy")
	private String purchaseHistory(Model model) {
		List<BoughtHistory> boughtHistories = boughtHistoryRepository.findByStudentId(accountAndCart.getId());
		for (BoughtHistory boughtHistory : boughtHistories) {
			Integer saleListId = boughtHistory.getSalelistId();
			BoughtCertificate boughtCertificate = boughtCertificateRepository.findBySaleListId(saleListId);
			if (boughtCertificate != null) {
				boughtHistory.setBoughtDay(boughtCertificate.getBoughtDay());
			}
		}
		if (boughtHistories.size() == 0) {
			model.addAttribute("errorMessage", "購入履歴はありません");
		}
		model.addAttribute("boughtHistories", boughtHistories);
		return "purchaseHistory";
	}

	//購入商品詳細画面表示
	@GetMapping("/purchase/items/detail")
	public String detail(@RequestParam("id") Integer id, Model model) {
		Bookinfo book = bookinfoRepository.findById(id).get();

		//画像
		String imageString = book.getImageId() + "";
		Long imageLong = Long.parseLong(imageString);
		Images image = imagesRepository.findById(imageLong).get();
		book.setImageName(image.getName());
		model.addAttribute("book", book);

		return "purchaseHistoryDetail";
	}
}
