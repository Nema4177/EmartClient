package com.emart.driver.implementation;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emart.utils.Constants;
import com.emart.utils.Utils;
import com.emart.data.Item;
import com.emart.data.ItemCondition;
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

	@Override
	public void start() {
		try {
			String message = "failure";
			int input = 0;
			while (message.equals(Constants.failure)) {
				System.out.println("Select an option\n 1.Create an account\n " + "2.Login into account\n");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> message = createAccount();
				case 2 -> message = login();
				}
				if (message.equals(Constants.failure)) {
					System.out.println("Operation falied, please try again");
				} else if (message.equals("success")) {
					System.out.println("Operation success");
				}
			}
			while (input != 5) {
				System.out.println("Select an option\n " + "1.Get Seller Rating\n " + "2.Put an item for sale"
						+ "3.Remove an item for sale" + "4.Display Items on sale" + "5.Logout");
				input = utils.readInputInteger();
				switch (input) {
				case 1 -> message = getSellerRating();
				case 2 -> message = putItemForSale();
				case 3 -> message = removeItemFromSale();
				case 4 -> message = displayItems();
				case 5 -> message = logout();
				}
				//System.out.println(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String logout() {
		// TODO Auto-generated method stub
		return null;
	}

	private String displayItems() throws Exception{
		JSONObject response = sellerApi.getAllItemsBySeller(sellerId);
		String status = (String) response.get(Constants.response_status_key);
		if(status.equalsIgnoreCase(Constants.failure)) {
			System.out.println("Fetching items unsuccessful");
			return status;
		}
		JSONArray itemArray = (JSONArray) response.get(Constants.response_itemsList_key);
		for (int i = 0 ; i < itemArray.size(); i++) {
	        JSONObject obj = (JSONObject) itemArray.get(i);
	        String itemName = (String) obj.get(Constants.request_item_name_key);
	        int itemId =  (int) obj.get(Constants.request_item_id_key);
	        Double salePrice = (Double) obj.get(Constants.request_item_price_key);
	        int quantity = (int) obj.get(Constants.request_item_quantity_key);
	        System.out.println("ItemName: "+itemName+" itemId: "+itemId+" salePrice: "+salePrice+" quantity: "+quantity);
	    }
		return status;
	}

	private String removeItemFromSale() throws Exception {
		System.out.println("Enter itemId");
		int itemId = utils.readInputInteger();
		System.out.println("Enter quantity");
		int quantity = utils.readInputInteger();
		JSONObject response = sellerApi.removeItemFromSale(itemId, quantity,sellerId);
		String status = (String) response.get(Constants.response_status_key);
		if(status.equalsIgnoreCase(Constants.success)) {
			System.out.println("ItemId "+itemId+" is removed");
		}else {
			System.out.println("Remove operation failed");
		}
		return status;
	}

	private String putItemForSale() throws Exception {
        System.out.println("Enter following details of the item: name,category(integer),keywords(separated by '-'),itemCondition(NEW/USED),price,quantity");
		String input = utils.readInputString();
		String[] inputItems = input.split(",");
        String itemName = inputItems[0];
        int category = Integer.parseInt(inputItems[1].trim());
        String keywords = inputItems[2];
        ArrayList<String> keyWords = new ArrayList<String>(Arrays.asList(keywords.split("-")));
        String itemCondition = inputItems[3];
        ItemCondition condition = ItemCondition.NEW;
        if(itemCondition.equalsIgnoreCase("USED")){
            condition = ItemCondition.USED;
        }
        double price = Double.parseDouble(inputItems[4].trim());
        int quantity = Integer.parseInt(inputItems[5].trim());
        Item item = new Item(itemName,category,keyWords,condition,price,sellerId,quantity);
        
        JSONObject response = sellerApi.addItemForSale(item, sellerId);
		return (String) response.get(Constants.response_status_key);
	}
	
	private String getSellerRating() {
		// TODO Auto-generated method stub
		return null;
	}

	private String createAccount() throws Exception {
		String username;
		String password;
		System.out.println("Enter username");
		username = utils.readInputString();
		System.out.println("Enter password");
		password = utils.readInputString();
		JSONObject response = sellerApi.createAccount(username, password);
		String status = (String) response.get(Constants.response_status_key);
		if (status.equalsIgnoreCase(Constants.success)) {
			this.sellerId = (int) response.get(Constants.response_sellerId_key);
		}
		return (String) response.get(Constants.response_status_key);
	}

	private String login() throws Exception {
		String username;
		String password;
		System.out.println("Enter username");
		username = utils.readInputString();
		System.out.println("Enter password");
		password = utils.readInputString();
		JSONObject response = sellerApi.login(username, password);
		String status = (String) response.get(Constants.response_status_key);
		if (status.equalsIgnoreCase("success")) {
			this.sellerId = (int) response.get(Constants.response_sellerId_key);
		}
		return (String) response.get(Constants.response_status_key);
	}

}
