package com.example.gilsoo.ping.Item;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-07-01.
 */
public class GroupItem {
    private String groupName;
    private String groupImage;
    private ArrayList<CardItem> cardItems;                  //  여기에 ArrayList를 만들어줘야될까
    public GroupItem(String groupName, String groupImage, ArrayList<CardItem> cardItems){
        this.groupName = groupName;
        this.groupImage = groupImage;
        this.cardItems = cardItems;
    }

    public GroupItem(String groupName, String groupImage){
        this.groupName = groupName;
        this.groupImage = groupImage;
        cardItems = null;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<CardItem> getCardItems() {
        return cardItems;
    }

    public void setCardItems(ArrayList<CardItem> cardItems) {
        this.cardItems = cardItems;
    }


    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }
}
