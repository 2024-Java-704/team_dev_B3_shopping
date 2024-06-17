package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
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

import com.example.demo.entity.Admin;
import com.example.demo.entity.Images;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Student;
import com.example.demo.model.AccountAndCart;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.ImagesRepository;
import com.example.demo.repository.StaffRepository;
import com.example.demo.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	@Autowired
	HttpSession session;
	@Autowired
	AccountAndCart accountandcart;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	StaffRepository staffRepository;
	@Autowired
	AdminRepository adminRepository;

	@Autowired
	ImagesRepository imagesRepository;

	private static final String UPLOAD_DIR = "src/main/resources/static/img"; // アップロード先のディレクトリ

	// 会員登録画面の表示
	@GetMapping("/new/users")
	public String studentAdd() {
		return "adduser";
	}

	//入力された情報を新規登録確認画面に表示する
	@PostMapping("/new/users")
	public String studentConfirm(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("address") String address,
			@RequestParam("birth") Date birth,
			//			@RequestParam("phone") String phone,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			Model model) {
		List<String> errorList = new ArrayList<>();
		List<Student> userList = studentRepository.findByNumber(number);
		List<Student> emailList = studentRepository.findByEmail(email);
		model.addAttribute("name", name);
		model.addAttribute("number", number);
		model.addAttribute("address", address);
		model.addAttribute("birth", birth);
		//		model.addAttribute("phone", phone);
		model.addAttribute("password", password);
		model.addAttribute("email", email);

		//エラーチェック
		if (userList != null && userList.size() > 0) {
			errorList.add("既に登録されている学籍番号です");
		}

		if (emailList != null && emailList.size() > 0) {
			errorList.add("既に登録されているメールアドレスです");
		}

		if (email.length() == 0) {
			errorList.add("名前を入力してください");
		}

		if (number.length() == 0) {
			errorList.add("学籍番号を入力してください");
		}

		if (address.length() == 0) {
			errorList.add("住所を入力してください");
		}

		if (birth == null) {
			errorList.add("生年月日を入力してください");
		}

		//		if (phone.length() == 0) {
		//			errorList.add("電話番号を入力してください");
		//		}

		if (email.length() == 0) {
			errorList.add("メールアドレスを入力してください");
		}

		if (password.length() == 0) {
			errorList.add("パスワードを入力してください");
		}

		// エラー発生時は新規登録に戻す
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "adduser";
		}

		return "adduserconfirm";
	}

	//申請完了画面の表示
	@PostMapping("/new/users/complete")
	public String studentAddRequest(
			@RequestParam("name") String name,
			@RequestParam("number") String number,
			@RequestParam("address") String address,
			@RequestParam("birth") Date birth,
			//			@RequestParam("phone") String phone,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			Model model) {

		Student student = new Student(name, number, address, birth, /*phone,*/ password, email, 1);
		studentRepository.save(student);

		model.addAttribute("studentId", student.getId());

		return "Upload-ToUploadCard";
	}

	//学生証のアップロード画面の表示
	@GetMapping("new/users/{id}/imgUp")
	public String imgUp(
			@PathVariable("id") Integer id,
			Model model) {
		model.addAttribute("studentId", id);
		return "Upload-StudentCard";
	}

	//画像のアップロード完了後、登録申請の完了画面表示
	@PostMapping("/new/users/{id}/imgUp")
	public String imgUploadSuccess(
			@PathVariable("id") Integer id,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

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

			Student student = studentRepository.findById(id).get();
			student.setImageId(imageId);
			studentRepository.save(student);

			redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
		}

		return "adduserconfirmcomplete";
	}

	//ログイン画面の表示
	@GetMapping("/login")
	public String studentAccess() {
		session.invalidate();
		return "login";
	}

	//ログイン処理
	@PostMapping("/login")
	public String studentLogin(
			@RequestParam("number") String number,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {

		List<String> errorList = new ArrayList<>();

		//エラーチェック
		if (email.length() == 0 && number.length() == 0) {
			errorList.add("メールアドレス又は学籍番号を入力してください");
		}

		if (password.length() == 0) {
			errorList.add("パスワードを入力してください");

		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "login";
		}

		List<Student> emailStudent = studentRepository.findByEmailAndPass(email, password);
		List<Student> numberStudent = studentRepository.findByNumberAndPass(number, password);

		if (numberStudent.isEmpty() && emailStudent.isEmpty()) {
			model.addAttribute("email", email);
			model.addAttribute("number", number);
			// DBに存在しなかった場合
			model.addAttribute("message", "メールアドレス又は学籍番号とパスワードが一致しませんでした");
			return "login";
		}

		//セッションにIDと名前を登録
		if (emailStudent.size() > 0) {
			Student student = emailStudent.get(0);
			accountandcart.setId(student.getId());
			accountandcart.setName(student.getName());
			accountandcart.setAuthority(1);
		}

		if (numberStudent.size() > 0) {
			Student student = numberStudent.get(0);
			accountandcart.setId(student.getId());
			accountandcart.setName(student.getName());
			accountandcart.setAuthority(1);
		}

		return "redirect:/items";
	}

	//職員用ログイン画面の表示
	@GetMapping("/staff")
	public String staffAccess() {
		session.invalidate();
		return "stafflogin";
	}

	//ログイン処理
	@PostMapping("/staff")
	public String staffLogin(
			@RequestParam("staffNumber") String number,
			@RequestParam("staffEmail") String email,
			@RequestParam("staffPass") String pass,
			Model model) {

		List<String> errorList = new ArrayList<>();

		//エラーチェック
		if (email.length() == 0 && number.length() == 0) {
			errorList.add("メールアドレス又は学籍番号を入力してください");
		}

		if (pass.length() == 0) {
			errorList.add("パスワードを入力してください");

		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "stafflogin";
		}

		List<Staff> emailStaff = staffRepository.findByStaffEmailAndStaffPass(email, pass);
		List<Staff> numberStaff = staffRepository.findByStaffNumberAndStaffPass(number, pass);

		if (emailStaff.size() == 0 && numberStaff.size() == 0) {

			// DBに存在しなかった場合
			model.addAttribute("message", "メールアドレス又は職員番号とパスワードが一致しませんでした");
			return "stafflogin";
		}

		//セッションにIDと名前を登録
		if (emailStaff.size() > 0) {
			Staff staff = emailStaff.get(0);
			accountandcart.setId(staff.getId());
			accountandcart.setName(staff.getStaffName());
			accountandcart.setAuthority(2);
		}

		if (numberStaff.size() > 0) {
			Staff staff = numberStaff.get(0);
			accountandcart.setId(staff.getId());
			accountandcart.setName(staff.getStaffName());
			accountandcart.setAuthority(2);
		}

		return "staffMyPage";
	}

	//管理者用ログイン画面の表示
	@GetMapping("/admin")
	public String adminAccess() {
		session.invalidate();
		return "adminlogin";
	}

	//ログイン処理
	@PostMapping("/admin")
	public String adminLogin(
			@RequestParam("adminName") String name,
			@RequestParam("adminPass") String pass,
			Model model) {

		List<String> errorList = new ArrayList<>();

		//エラーチェック
		if (name == null) {
			errorList.add("名前を入力してください");
		}

		if (pass.length() == 0) {
			errorList.add("パスワードを入力してください");

		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "adminlogin";
		}

		List<Admin> idAdmin = adminRepository.findByAdminNameAndAdminPass(name, pass);

		if (idAdmin.size() == 0) {

			// DBに存在しなかった場合
			model.addAttribute("message", "名前とパスワードが一致しませんでした");
			return "adminlogin";
		}

		//セッションにIDと名前を登録
		if (idAdmin.size() > 0) {
			Admin admin = idAdmin.get(0);
			accountandcart.setId(admin.getId());
			accountandcart.setName(admin.getAdminName());
			accountandcart.setAuthority(3);
		}

		return "adminMyPage";
	}

	//退会確認画面の表示
	@GetMapping("/user/delete")
	public String studentDelete() {
		return "userdelete";
	}

	//退会処理
	@PostMapping("/user/delete")
	public String studentDeleteSuccess() {
		Integer accountId = accountandcart.getId();
		Student student = studentRepository.findById(accountId).get();
		student.setStatus(4);
		studentRepository.save(student);

		return "redirect:/login";
	}

	//ログアウト処理
	@GetMapping("/logout")
	public String userLogout() {
		return "redirect:/login";
	}

}
