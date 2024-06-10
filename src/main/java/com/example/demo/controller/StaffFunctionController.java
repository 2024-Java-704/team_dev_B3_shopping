package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class StaffFunctionController {

	//職員マイページを表示する。
	@GetMapping("/staffpage")
	public String staffMypage() {
		return "";
	}

	//注文書籍一覧画面を表示する。
	@GetMapping("/purchased")
	public String order() {
		return "";
	}

	//注文書籍詳細画面を表示する。
	@GetMapping("/purchased/{id}/detail")
	public String orderDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//注文書籍ステータス変更処理を行い、注文書籍一覧画面を表示する。
	@PostMapping("/purchased/{id}/change")
	public String orderStatus(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//売上受取申請一覧画面を表示する。
	@GetMapping("/salesreceipt")
	public String proceeds() {
		return "";
	}

	//売上受取申請詳細画面を表示する。
	@GetMapping("/salesreceipt/{id}/detail")
	public String proceedsDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//売上受取申請ステータス変更処理を行い、受取申請詳細画面を表示する。
	@PostMapping("/salesreceipt/{id}/change")
	public String proceedsStatus(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請一覧画面の表示
	@GetMapping("/itemrequest")
	public String putUpAccess() {
		return "";
	}

	//出品申請却下確認画面の表示
	@GetMapping("/itemrequest/{id}/rejected")
	public String putUpRejectConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請を却下し、出品ステータスを変更して出品申請一覧画面に戻る
	@PostMapping("/itemrequest/{id}/rejected")
	public String putUpReject(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請承認確認画面の表示
	@GetMapping("/itemrequest/{id}/approval")
	public String putUpConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請を承認し、出品ステータスを変更して出品申請一覧画面に戻る
	@PostMapping("/itemrequest/{id}/approval")
	public String putUp(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//出品申請詳細画面の表示
	@GetMapping("/itemrequest/{id}/detail")
	public String putUpDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント一覧画面の表示
	@GetMapping("/account")
	public String accountAccess() {
		return "";
	}

	//アカウント詳細画面の表示
	@GetMapping("/account/{id}/detail")
	public String accountDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント申請一覧画面の表示
	@GetMapping("/account/tempo")
	public String accountAddAccess() {
		return "";
	}

	//アカウント申請却下確認画面の表示
	@GetMapping("/account/tempo/{id}/reject")
	public String accountRejectAccess(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント承認申請を却下し、アカウントステータスを変更してアカウント一覧画面に戻る
	@PostMapping("/account/tempo/{id}/reject")
	public String accountReject(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント申請承認確認画面の表示
	@GetMapping("/account/tempo/{id}/approval")
	public String accountAddDetail(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント承認申請を承認し、アカウントステータスを変更してアカウント一覧画面に戻る
	@PostMapping("/account/tempo/{id}/approval")
	public String accountAdd(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント凍結確認画面の表示
	@GetMapping("/account/{id}/freeze")
	public String accountBanConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウントを凍結し、アカウントステータスを変更してアカウント一覧画面に戻る
	@PostMapping("/account/{id}/freeze")
	public String accountBan(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント更新画面の表示
	@GetMapping("/account/update")
	public String accountUpdateAccess() {
		return "";
	}

	//アカウント更新確認画面の表示
	@GetMapping("/account/{id}/update")
	public String accountUpdate(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}

	//アカウント情報を更新し、アカウント一覧画面に戻る
	@PostMapping("/account/{id}/update")
	public String accountUpdateConfirm(
			@PathVariable("id") Integer id,
			Model model) {
		return "";
	}
}
