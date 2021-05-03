package com.emart.seller;


import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.emart.data.Item;


@Component
public interface SellerApiInterface {

    public enum ACTIONS {
    	createAccount,
    	login,
    	logout,
    	getSellerRating,
        addItemToDatabase,
        list,
        changeSalePrice,
        removeItemFromDB
    }
    
    public JSONObject createAccount(int sellerId,String username, String name,String password) throws Exception;
    
    public JSONObject login(String username, String password) throws Exception;
    
    public JSONObject logout(int sellerId) throws Exception;
    
    public JSONObject getSellerRating(int sellerId) throws Exception;
    /**
     * API to add item for sale
     *
     * @param item to be added for sale
     */
    public JSONObject addItemForSale(Item item,int sellerId) throws Exception;

    /**
     * API for price change of item
     *
     * @param itemID   of the item whose price needs to changed
     * @param newPrice of the item
     */
    public JSONObject changeItemPrice(int itemID, double newPrice,int sellerId) throws Exception;

    /**
     * API for removing item from sale or reducing it's quantity
     *
     * @param itemID   of the item which need to be removed/ reduced
     * @param quantity of the item to be reduced/ removed
     */
    public JSONObject removeItemFromSale(int itemID, int quantity,int sellerId) throws Exception;

    /**
     * API for retrieving all the items added by a seller
     *
     * @param sellerId is the id of the seller
     * @return list of Item object, added by the seller
     */
    public JSONObject getAllItemsBySeller(int sellerId) throws Exception;

}
