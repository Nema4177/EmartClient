package com.emart.seller.implementation;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emart.data.Item;
import com.emart.networt.RestUtils;
import com.emart.seller.SellerApiInterface;
import com.emart.utils.Constants;

@Component
public class SellerApiImplementation implements SellerApiInterface{

	@Autowired
	RestUtils restUtils;
		
    private SellerApiImplementation() {
    }

    public enum ACTIONS {
        addItemToDatabase,
        list,
        changeSalePrice,
        removeItemFromDB
    }

    /**
     * API to create account
     *
     * @param username of the account
     * @param password of the account
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
	public JSONObject createAccount(String username, String password) throws ParseException {
    	JSONObject request = new JSONObject();
    	request.put(Constants.request_username_key, username);
    	request.put(Constants.request_password_key, password);
    	String url = Constants.baseUrl+Constants.createAccountPath;
    	return restUtils.postRequest(request, url);
    
    }
    
    /* API to create account
    *
    * @param username of the account
    * @param password of the account
    * @throws ParseException 
    */
   @SuppressWarnings("unchecked")
	public JSONObject login(String username, String password) throws ParseException {
   	JSONObject request = new JSONObject();
   	request.put("username", username);
   	request.put("password", password);
   	String url = Constants.baseUrl+Constants.loginPath;
   	return restUtils.postRequest(request, url);
   
   }
    /**
     * API to add item for sale
     *
     * @param item to be added for sale
     */
    public JSONObject addItemForSale(Item item,int sellerId) throws Exception{
    	JSONObject request = new JSONObject();
    	request.put(Constants.request_item_id_key, item.getId());
    	request.put(Constants.request_item_price_key, item.getPrice());
    	request.put(Constants.request_item_keywords_key, item.getKeyWords());
    	request.put(Constants.request_item_category_key, item.getCategory());
    	request.put(Constants.request_item_quantity_key, item.getQuantity());
    	request.put(Constants.request_item_name_key, item.getName());
    	request.put(Constants.request_item_condition_key, item.getCondition());

    	String url = Constants.baseUrl+Constants.putItemPath+"?"+Constants.response_sellerId_key+"="+sellerId;
    	return restUtils.postRequest(request, url);
    }

    /**
     * API for price change of item
     *
     * @param itemID   of the item whose price needs to changed
     * @param newPrice of the item
     */
    @SuppressWarnings("unchecked")
	public JSONObject changeItemPrice(int itemId, double newPrice, int sellerId) throws Exception{
    	JSONObject request = new JSONObject();
    	request.put(Constants.request_item_id_key, itemId);
    	request.put(Constants.request_item_price_key, newPrice);
    	String url = Constants.baseUrl+Constants.changeSalePricePath+"?"+Constants.response_sellerId_key+"="+sellerId;
    	return restUtils.postRequest(request, url);
    }

    /**
     * API for removing item from sale or reducing it's quantity
     *
     * @param itemID   of the item which need to be removed/ reduced
     * @param quantity of the item to be reduced/ removed
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
	public JSONObject removeItemFromSale(int itemId, int quantity, int sellerId) throws ParseException {
    	JSONObject request = new JSONObject();
    	request.put(Constants.request_item_id_key, itemId);
    	request.put(Constants.request_item_quantity_key, quantity);
    	String url = Constants.baseUrl+Constants.removeItemPath+"?"+Constants.response_sellerId_key+"="+sellerId;
    	return restUtils.postRequest(request, url);
    }

    /**
     * API for retrieving all the items added by a seller
     *
     * @param sellerId is the id of the seller
     * @return list of Item object, added by the seller
     */
    public JSONObject getAllItemsBySeller(int sellerId) throws Exception {
        String url = Constants.baseUrl+Constants.displayItemsPath+"?"+Constants.response_sellerId_key+"="+sellerId;
    	return restUtils.getRequest(url);
    }

	@Override
	public JSONObject logout(int sellerId) throws Exception {
		String url = Constants.baseUrl+Constants.logoutPath+"?"+Constants.response_sellerId_key+"="+sellerId;
    	return restUtils.getRequest(url);
	}

	@Override
	public JSONObject getSellerRating(int sellerId) throws Exception {
    	String url = Constants.baseUrl+Constants.getSellerRatingPath+"?"+Constants.response_sellerId_key+"="+sellerId;
    	return restUtils.getRequest(url);
	}
}
