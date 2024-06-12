package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AccountAndCart {
	private Integer id; //主キー

	private String name; //名前

	public List<CartItems> cartItems = new ArrayList<CartItems>();//カートリスト

	//デフォルトコンストラクタ
	public AccountAndCart() {
	}

	public AccountAndCart(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public AccountAndCart(List<CartItems> cartItems) {

	}

	//カートに追加する
	public void add(CartItems cartitems) {
		for (CartItems cartitem : cartItems) {
			if (cartitems.getId() == cartitem.getId()) {//リストにデータがある場合
				return;
			}
		}
		//リストに該当するIDが存在しない場合追加
		cartItems.add(cartitems);

	}

	//カートから削除する
	public void del(CartItems cartitems) {
		for (CartItems cartitem : cartItems) {
			if (cartitems.getId() == cartitem.getId()) {//リストにデータがある場合
				//リストに該当するIDが存在する場合に削除
				cartItems.remove(cartitems);
				return;
			}
		}
	}

	//カートの履歴を削除
	public void clear(CartItems cartitems) {

		cartItems.removeAll(cartItems);

	}

	public void del(Integer id) {
		for (CartItems cartitem : cartItems) {
			if (id == cartitem.getId()) {//リストにデータがある場合
				//リストに該当するIDが存在する場合に削除
				cartItems.remove(cartitem);
				return;
			}
		}
	}

	//ゲッター　セッター
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	//	public void setCartItems(List<CartItems> cartItems) {
	//		this.cartItems = cartItems;
	//	}
}
