package com.emart.seller.person;

public class SellerPerson {

    private static final String UP_VOTES = " Up Votes";
    private static final String DOWN_VOTES = " Down Votes";
    private static final String SEPARATOR = ", ";

    private int id;
    private String name;
    private String feedback;
    private int upVotes;
    private int downVotes;
    private int itemSoldCount;

    public SellerPerson() {
    }

    public SellerPerson(int id, String name, int upVotes, int downVotes, int itemSoldCount) {
        this.id = id;
        this.name = name;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.itemSoldCount = itemSoldCount;
        this.feedback = upVotes + UP_VOTES + SEPARATOR + downVotes + DOWN_VOTES;
    }

    public static String getUpVotes() {
        return UP_VOTES;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
        this.feedback = upVotes + UP_VOTES + SEPARATOR + downVotes + DOWN_VOTES;
    }

    public static String getDownVotes() {
        return DOWN_VOTES;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
        this.feedback = upVotes + UP_VOTES + SEPARATOR + downVotes + DOWN_VOTES;
    }

    public int getItemSoldCount() {
        return itemSoldCount;
    }

    public void setItemSoldCount(int itemSoldCount) {
        this.itemSoldCount = itemSoldCount;
    }

    public static String getSEPARATOR() {
        return SEPARATOR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }
}
