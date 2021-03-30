package com.emart.driver.implementation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emart.utils.SellerConstants;
import com.emart.utils.Utils;
import com.google.gson.Gson;
import com.emart.data.Item;
import com.emart.data.ItemCondition;
import com.emart.data.ItemList;
import com.emart.driver.Driver;
import com.emart.networt.RestUtils;
import com.emart.seller.SellerApiInterface;
import com.emart.seller.implementation.SellerApiImplementation;

@Component
public class SellerDriver implements Driver {

	@Autowired
	private SellerApiInterface sellerApi;

	@Autowired
	private Utils utils;

	@Autowired
	RestUtils restUtils;

	private int sellerId;

	private Gson gson = new Gson();

	// TODO add changing sale price
	@Override
	public void start() {
		try {
			int message = 400;
			int input = 0;
			while (message != SellerConstants.success) {
				System.out.println("Select an option\n 1.Create an account\n " + "2.Login into account\n");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> message = createAccount();
				case 2 -> message = login();
				}
				if (message != SellerConstants.success) {
					System.out.println("Operation falied, please try again");
				} else if (message == SellerConstants.success) {
					System.out.println("Operation success");
				}
			}
			while (input != 6) {
				System.out.println("Select an option\n" + "1.Get Seller Rating\n" + "2.Put an item for sale\n"
						+ "3.Remove an item for sale\n" + "4.Display Items on sale\n" + "5.changeSalePrice\n"
						+ "6.Logout");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> message = getSellerRating();
				case 2 -> message = putItemForSale();
				case 3 -> message = removeItemFromSale();
				case 4 -> message = displayItems();
				case 5 -> message = changeSalePrice();
				case 6 -> message = logout();
				}
				System.out.println(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int logout() throws Exception {
		int status = 0;
		try {
			Instant start = Instant.now();
			JSONObject response = sellerApi.logout(sellerId);
			Instant end = Instant.now();
			Long st = (long) response.get(SellerConstants.response_status_key);
			status = st.intValue();
			if (status == SellerConstants.success) {
				System.out.println("Logout successful");
			} else {
				System.out.println("Remove operation failed");
			}
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int displayItems() throws Exception {
		int status = 0;
		try {
			Instant start = Instant.now();
			JSONObject response = sellerApi.getAllItemsBySeller(sellerId);
			Instant end = Instant.now();
			Long st = (long) response.get(SellerConstants.response_status_key);
			status = st.intValue();
			if (!(status == SellerConstants.success)) {
				System.out.println("Fetching items unsuccessful");
				return status;
			}
			ItemList itemList = gson.fromJson((String) response.get(SellerConstants.response_itemsList_key),
					ItemList.class);
			for (Item item : itemList.getItem()) {
				String itemName = item.getName();
				int itemId = item.getId();
				Double salePrice = item.getPrice();
				int quantity = item.getQuantity();
				System.out.println("ItemName: " + itemName + " itemId: " + itemId + " salePrice: " + salePrice
						+ " quantity: " + quantity);
			}
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int removeItemFromSale() throws Exception {
		int status = 0;
		try {
			System.out.println("Enter itemId");
			int itemId = utils.readInputInteger();
			System.out.println("Enter quantity");
			int quantity = utils.readInputInteger();
			Instant start = Instant.now();
			JSONObject response = sellerApi.removeItemFromSale(itemId, quantity, sellerId);
			Long st = (long) response.get(SellerConstants.response_status_key);
			status = st.intValue();
			if (status == SellerConstants.success) {
				System.out.println("ItemId " + itemId + " is removed");
			} else {
				System.out.println("Remove operation failed");
			}
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int changeSalePrice() throws Exception {
		int status =0;
		try {
		System.out.println("Enter itemId");
		int itemId = utils.readInputInteger();
		System.out.println("Enter new seller price");
		int quantity = utils.readInputInteger();
		Instant start = Instant.now();
		JSONObject response = sellerApi.changeItemPrice(itemId, quantity, sellerId);
		Instant end = Instant.now();
		long st = (long) response.get(SellerConstants.response_status_key);
		status = (int) st;
		if (status == SellerConstants.success) {
			System.out.println("Item price is changed");
		} else {
			System.out.println("Change is not successful");
		}
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int putItemForSale() throws Exception {
		int status = 0;
		try {
		System.out.println(
				"Enter following details of the item: name,category(integer),keywords(separated by '-'),itemCondition(NEW/USED),price,quantity");
		String input = utils.readInputString();
		String[] inputItems = input.split(",");
		String itemName = inputItems[0];
		int category = Integer.parseInt(inputItems[1].trim());
		String keywords = inputItems[2];
		String itemCondition = inputItems[3];
		ItemCondition condition = ItemCondition.NEW;
		if (itemCondition.equalsIgnoreCase("USED")) {
			condition = ItemCondition.USED;
		}
		double price = Double.parseDouble(inputItems[4].trim());
		int quantity = Integer.parseInt(inputItems[5].trim());
		Item item = new Item(itemName, category, keywords, condition, price, sellerId, quantity);
		Instant start = Instant.now();
		JSONObject response = sellerApi.addItemForSale(item, sellerId);
		Instant end = Instant.now();
		Long st = (long) response.get(SellerConstants.response_status_key);
		Long long_id;
		status = st.intValue();
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int getSellerRating() throws Exception {
		int status =0;
		try {
		Instant start = Instant.now();
		JSONObject response = sellerApi.getSellerRating(sellerId);
		Instant end = Instant.now();
		long st = (long) response.get(SellerConstants.response_status_key);
	    status = (int) st;
		if (status == SellerConstants.success) {
			System.out.println("Seller upvotes are " + response.get(SellerConstants.response_seller_upvotes_key)
					+ " Downvotes are " + response.get(SellerConstants.response_seller_downvotes_key));
		} else {
			System.out.println("Get Seller Rating failed");
		}
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int createAccount() throws Exception {
		int status = 0;
		try {
		String username;
		String password;
		String name;
		System.out.println("Enter username");
		username = utils.readInputString();
		System.out.println("Enter name");
		name = utils.readInputString();
		System.out.println("Enter password");
		password = utils.readInputString();
		Instant start = Instant.now();
		JSONObject response = sellerApi.createAccount(username, name, password);
		Instant end = Instant.now();
		Long st = (long) response.get(SellerConstants.response_status_key);
		Long long_id;
		status = st.intValue();
		if (status == SellerConstants.success) {
			long_id = (long) response.get(SellerConstants.response_sellerId_key);
			this.sellerId = long_id.intValue();
		}
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	private int login() throws Exception {
		int status = 0;
		try {
		String username;
		String password;
		System.out.println("Enter username");
		username = utils.readInputString();
		System.out.println("Enter password");
		password = utils.readInputString();
		Instant start = Instant.now();
		JSONObject response = sellerApi.login(username, password);
		Instant end = Instant.now();
		long st = (long) response.get(SellerConstants.response_status_key);
		status = (int) st;
		Long long_id;
		if (status == SellerConstants.success) {
			long_id = (long) response.get(SellerConstants.response_sellerId_key);
			this.sellerId = long_id.intValue();
		}
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
