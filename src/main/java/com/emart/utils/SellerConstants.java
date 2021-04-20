package com.emart.utils;

import java.util.ArrayList;
import java.util.List;

public class SellerConstants {
	public static final String baseUrl = "http://10.166.127.135:8080/";
	public static final String createAccountPath = "createAccount";
	public static final String loginPath = "login";
	public static final String logoutPath = "logout";
	public static final String getSellerRatingPath = "getSellerRating";
	public static final String putItemPath = "addItem";
	public static final String changeSalePricePath = "changeSalePrice";
	public static final String removeItemPath = "removeItem";
	public static final String displayItemsPath = "getAllItems";

	public static final List<String> baseURLs = new ArrayList<>(List.of("http://34.106.13.241:8081/", "http://34.125.75.24:8081/",
			"http://34.83.184.179:8081/", "http://34.203.152.55:8081/"));

	//REST METHOD
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int DELETE = 3;
	
	public static final int success = 200;
	public static final String failure = "failure";
	
	public static final String request_type_key = "requestType";
	
	public static final String request_item_id_key = "itemId";
	public static final String request_item_price_key = "price";
	public static final String request_change_price_key = "itemPrice";
	public static final String request_item_quantity_key = "quantity";
	public static final String request_item_name_key = "itemName";
	public static final String request_item_category_key = "category";
	public static final String request_item_keywords_key = "keywords";
	public static final String request_item_condition_key = "condition";
	
	public static final String request_username_key = "username";
	public static final String request_name_key = "name";
	public static final String request_password_key = "password";

	
	public static final String response_status_key = "status";
	public static final String response_sellerId_key = "sellerId";
	public static final String response_itemsList_key = "itemsList";
	public static final String response_seller_upvotes_key="upvotes";
	public static final String response_seller_downvotes_key="downvotes";

	
}
