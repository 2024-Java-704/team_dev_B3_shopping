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

import com.example.demo.entity.Staff;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.StaffRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminFunctionController {//returnはhtml完成次第追記
	
	@Autowired
	HttpSession session;
	
	@Autowired
	AccountAndCart accountAndCart;
	
	@Autowired
	StaffRepository staffRepository;

	//管理者用のマイページの表示
	@GetMapping("/adminpage")
	public String adminPage() {
//		if(accountAndCart.getName().equals("admin")) {
		return "adminMyPage";
//		} else {
//			return "needLogin";
//		}
	}

	//職員一覧画面の表示
	@GetMapping("/staffList")
	public String staffAll(Model model) {
		List<Staff> staffList = staffRepository.findAll();
		model.addAttribute("staffList", staffList);
		return "adminStaffList";
	}

	//職員詳細画面の表示
	@GetMapping("/staffList/{id}/detail")
	public String adminUpdateDetail(
			@PathVariable("id") Integer id,
			Model model) {
		Staff staff = staffRepository.findById(id).get();
		model.addAttribute("staff", staff);
		return "adminStaffDetail";
	}
	
	//職員アカウント更新画面の表示
	@GetMapping("/staffList/{id}/update")
	public String staffUpdeteInput(
			@PathVariable("id") Integer id,
			Model model) {
		Staff staff = staffRepository.findById(id).get();
		model.addAttribute("staff", staff);
		return "adminStaffEdit";
	}

	//職員アカウント更新確認画面の表示
	@GetMapping("/staffList/{id}/update/confirm")
	public String adminUpdateConfirm(
			@PathVariable("id") Integer id,
			@RequestParam(name = "number", defaultValue = "")String number,
			@RequestParam(name = "name", defaultValue = "")String name,
			@RequestParam(name = "email", defaultValue = "")String email,
			@RequestParam(name = "pass", defaultValue = "")String pass,
			Model model) {
		//NULLチェック処理を後で記述
		
		//更新前の情報
		Staff lastInfo = staffRepository.findById(id).get();
		model.addAttribute("lastInfo", lastInfo);
		
		//更新後の情報
		model.addAttribute("id", id);
		model.addAttribute("number", number);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("pass", pass);
		
		return "adminStaffEditConfirm";
	}

	//職員アカウント情報を更新し、アカウント一覧画面に戻る
	@PostMapping("/staffList/{id}/update/complete")
	public String adminUpdateComplete(
			@PathVariable("id") Integer id,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("pass") String pass,
			@RequestParam("number") String number,
			Model model) {
		
		//Nullチェックをしないとエラーが出るので後ほど記述
		
		Staff uniqueCheck = staffRepository.findById(id).get();
		Staff unique = staffRepository.findById(id).get();
		
		//メールアドレスと職員番号どちらも変わっていない場合
		if(uniqueCheck.getStaffEmail().equals(email) && uniqueCheck.getStaffNumber().equals(number)) {
			
			Staff staff = new Staff(id, name, unique.getStaffEmail(), pass, unique.getStaffNumber());
			staffRepository.save(staff);
		}
		//メールアドレスが変わっていない場合
		if(uniqueCheck.getStaffEmail().equals(email)) {
			Staff staff = new Staff(id, name, unique.getStaffEmail(), pass, number);
			staffRepository.save(staff);
			
//			これは動かない
//			staff.setStaffEmail(email);
//			Staff staff = new Staff(id, name, pass, number);
			
		}
		//職員番号が変わっていない場合
		if(uniqueCheck.getStaffNumber().equals(number)) {
			Staff staff = new Staff(id, name, email, pass, unique.getStaffNumber());
			
			staffRepository.save(staff);
		}
		
		Staff staff = new Staff(id, name, email, pass, number);
		staffRepository.save(staff);
		return "redirect:/staffList";
	}
	
	
	//職員アカウント削除確認画面の表示
	@GetMapping("/staffList/{id}/delete")
	public String staffDeleteConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		Staff staff = staffRepository.findById(id).get();
		model.addAttribute("staff", staff);
		return "adminStaffDeleteConfirm";
	}

	//職員アカウント削除処理
	@PostMapping("/staffList/{id}/delete")
	public String staffDelete(
			@PathVariable("id") Integer id
			) {
		staffRepository.deleteById(id);
		return "redirect:/staffList";
	}

	
	//職員アカウント追加画面の表示
	@GetMapping("/staffList/add")
	public String adminAdd() {
		return "adminStaffAdd";
	}
	
	//入力された情報を新規登録確認画面に表示する
		@GetMapping("staffList/add/confirm")
		public String staffAddConfirm(
				@RequestParam("name") String name,
				@RequestParam("number") String number,
				@RequestParam("password") String password,
				@RequestParam("email") String email,
				Model model) {
			List<String> errorList = new ArrayList<>();
			List<Staff> userList = staffRepository.findByStaffNumber(number);
			model.addAttribute("name", name);
			model.addAttribute("number", number);
			model.addAttribute("password", password);
			model.addAttribute("email", email);
			//エラーチェック
			if (userList != null && userList.size() > 0) {
				errorList.add("	既に登録されている学籍番号です");
			}
			if (name.length() == 0) {
				errorList.add("名前を入力してください");
			}

			if (number.length() == 0) {
				errorList.add("学籍番号を入力してください");
			}

			if (email.length() == 0) {
				errorList.add("メールアドレスを入力してください");
			}

			if (password.length() == 0) {
				errorList.add("パスワードを入力してください");
			}

			// エラー発生時は新規登録に戻す
			if (errorList.size() > 0) {
				model.addAttribute("errorList", errorList);

				return "adminStaffAddConfirm";
			}

			return "adminStaffAddConfirm";
		}
		
	//アカウント追加処理
	@PostMapping("/staffList/add/complete")
	public String adminStaffAdd(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			Model model) {

			Staff staff = new Staff(name, number, password, email);
			staffRepository.save(staff);

			return "adminStaffAddComplete";
		}

}
