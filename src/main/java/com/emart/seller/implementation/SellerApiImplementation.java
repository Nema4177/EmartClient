package com.emart.seller.implementation;

import com.emart.data.Item;
import com.emart.networt.RestUtils;
import com.emart.seller.SellerApiInterface;
import com.emart.utils.SellerConstants;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.Random;

@Component
public class SellerApiImplementation implements SellerApiInterface {

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
    public JSONObject createAccount(String username, String name, String password) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(SellerConstants.request_username_key, username);
        request.put(SellerConstants.request_name_key, name);
        request.put(SellerConstants.request_password_key, password);
        return makeRESTAPICall(request, SellerConstants.createAccountPath, SellerConstants.POST);
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
        return makeRESTAPICall(request, SellerConstants.loginPath, SellerConstants.POST);
    }

    /**
     * API to add item for sale
     *
     * @param item to be added for sale
     */
    public JSONObject addItemForSale(Item item, int sellerId) throws Exception {
        JSONObject request = new JSONObject();
        request.put(SellerConstants.request_item_id_key, item.getId());
        request.put(SellerConstants.request_item_price_key, item.getPrice());
        request.put(SellerConstants.request_item_keywords_key, item.getKeyWords());
        request.put(SellerConstants.request_item_category_key, item.getCategory());
        request.put(SellerConstants.request_item_quantity_key, item.getQuantity());
        request.put(SellerConstants.request_item_name_key, item.getName());
        request.put(SellerConstants.request_item_condition_key, item.getCondition());

		return makeRESTAPICall(request, SellerConstants.putItemPath + "?" +
				SellerConstants.response_sellerId_key + "=" + sellerId, SellerConstants.POST);
    }

    /**
     * API for price change of item
     *
     * @param itemId   of the item whose price needs to changed
     * @param newPrice of the item
     */
    @SuppressWarnings("unchecked")
    public JSONObject changeItemPrice(int itemId, double newPrice, int sellerId) throws Exception {
        JSONObject request = new JSONObject();
        request.put(SellerConstants.request_item_id_key, itemId);
        request.put(SellerConstants.request_change_price_key, newPrice);
		return makeRESTAPICall(request, SellerConstants.changeSalePricePath + "?" +
				SellerConstants.response_sellerId_key + "=" + sellerId, SellerConstants.POST);
    }

    /**
     * API for removing item from sale or reducing it's quantity
     *
     * @param itemId   of the item which need to be removed/ reduced
     * @param quantity of the item to be reduced/ removed
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public JSONObject removeItemFromSale(int itemId, int quantity, int sellerId) throws ParseException {
        JSONObject request = new JSONObject();
        request.put(SellerConstants.request_item_id_key, itemId);
        request.put("itemQuantity", quantity);
		return makeRESTAPICall(request, SellerConstants.removeItemPath + "?" +
				SellerConstants.response_sellerId_key + "=" + sellerId, SellerConstants.POST);
    }

    /**
     * API for retrieving all the items added by a seller
     *
     * @param sellerId is the id of the seller
     * @return list of Item object, added by the seller
     */
    public JSONObject getAllItemsBySeller(int sellerId) throws Exception {
        return makeRESTAPICall(null, SellerConstants.displayItemsPath + "?" +
                SellerConstants.response_sellerId_key + "=" + sellerId, SellerConstants.GET);
    }

    @Override
    public JSONObject logout(int sellerId) throws Exception {
        JSONObject request = new JSONObject();
        return makeRESTAPICall(request, SellerConstants.logoutPath + "?" +
                SellerConstants.response_sellerId_key + "=" + sellerId + "&username=", SellerConstants.POST);
    }

    @Override
    public JSONObject getSellerRating(int sellerId) throws Exception {
        return makeRESTAPICall(null, SellerConstants.getSellerRatingPath + "?" +
                SellerConstants.response_sellerId_key + "=" + sellerId, SellerConstants.GET);
    }

    private String getBaseURL() {
        int random = new Random().nextInt(SellerConstants.baseURLs.size());
        return SellerConstants.baseURLs.get(random);
    }

    private void removeURL(String url) {
        SellerConstants.baseURLs.remove(url);
    }

    private JSONObject makeRESTAPICall(JSONObject request, String endpoint, int restMethod) throws ParseException {
        JSONObject response = new JSONObject();
        String baseUrl = getBaseURL();
        String url = baseUrl + endpoint;
        System.out.println("Request url is " + url);
        System.out.println("Request payload is " + request);
        try {
            if (restMethod == SellerConstants.GET) {
                response = restUtils.getRequest(url);
            } else {
                response = restUtils.postRequest(request, url);
            }
        } catch (ResourceAccessException ex) {
            ex.printStackTrace();
            removeURL(baseUrl);
            if (!SellerConstants.baseURLs.isEmpty()) {
                response = makeRESTAPICall(request, endpoint, restMethod);
            }
        }
        System.out.println("Response payload is " + response);
        return response;
    }
}
