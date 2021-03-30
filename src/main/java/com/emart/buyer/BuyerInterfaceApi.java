package com.emart.buyer;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.emart.data.Item;
import com.emart.data.ItemCart;

public interface BuyerInterfaceApi {

    public enum ACTIONS {
    	createAccount,
    	login,
    	logout,
    	purchase,
    	pushFeedback,
    	getSellerRating,
    	getBuyerHistory,
        searchItemWithCategory,
        addItemToCart,
        removeItemFromCart,
        clearCart,
        displayCart
    }
    
    public JSONObject createAccount(String username, String password);
    
    public JSONObject login(String username, String password);
    
    public void logout();
    
    public void pushFeedback();
    
    public void getSellerRating();
    
    public void getBuyerHistory();
    
    public JSONObject purchase();
    
    
    /**
     * API for searching items
     *
     * @param category of the item needed
     * @param keywords search keyword
     * @return list of Items that matched the search
     */
    public ArrayList<Item> searchItems(int category, ArrayList<String> keywords);

    /**
     * API for adding item to the cart
     *
     * @param buyerId of the buyer
     * @param itemId  of the item to be added to the cart
     * @param quantity of the items to be added
     */
    public void addItemToCart(int buyerId, int itemId, int quantity);

    /**
     * API for removing item from the cart
     *
     * @param buyerId  of the buyer
     * @param itemId   of the item to be removed from the cart
     * @param quantity of the item to be removed from the cart
     */
    public void removeItemFromCart(int buyerId, int itemId, int quantity);

    /**
     * API for clearing the cart
     *
     * @param buyerId of the buyer
     */
    public void clearCart(int buyerId);

    /**
     * API for for retrieving the cart items
     *
     * @param buyerId of the buyer
     * @return list of Items in the cart
     */
    public ArrayList<ItemCart> getCart(int buyerId);
}
