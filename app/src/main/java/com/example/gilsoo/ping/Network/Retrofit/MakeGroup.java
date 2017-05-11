package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class MakeGroup {
    private String groupname;               // 여기에 카드 정보 담을 변수도 나중에 추가해야될 것같애서 클래스로 만듬 (@Body로)

    public MakeGroup(){}

    public MakeGroup(String groupname){
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
