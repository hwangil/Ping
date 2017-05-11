package com.example.gilsoo.ping.Show_Group;

/**
 * Created by gilsoo on 2016-06-28.
 */
public class GroupGrid {
    private String groupImage;
    private String groupName;

    public GroupGrid(String groupImage, String groupName){
        this.groupImage = groupImage;
        this.groupName = groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
