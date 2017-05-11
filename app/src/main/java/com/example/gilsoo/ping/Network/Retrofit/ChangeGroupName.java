package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class ChangeGroupName {
    private String groupname;
    private String update_groupname;

    public ChangeGroupName(){}

    public ChangeGroupName(String groupname, String update_groupname){
        this.groupname = groupname;
        this.update_groupname = update_groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getUpdate_groupname() {
        return update_groupname;
    }

    public void setUpdate_groupname(String update_groupname) {
        this.update_groupname = update_groupname;
    }
}
