package com.emart.buyer.implementation;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.emart.buyer.BuyerInterfaceApi;
import com.emart.data.Item;
import com.emart.data.ItemCart;

@Component
public class BuyerApiImplementation implements BuyerInterfaceApi{

	@Override
	public JSONObject createAccount(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushFeedback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getSellerRating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getBuyerHistory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject purchase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Item> searchItems(int category, ArrayList<String> keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItemToCart(int buyerId, int itemId, int quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItemFromCart(int buyerId, int itemId, int quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearCart(int buyerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ItemCart> getCart(int buyerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
