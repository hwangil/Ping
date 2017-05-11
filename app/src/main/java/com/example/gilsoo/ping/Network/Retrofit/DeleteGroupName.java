package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class DeleteGroupName {
    private String groupname;

    public DeleteGroupName(){}

    public DeleteGroupName(String groupname){
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
