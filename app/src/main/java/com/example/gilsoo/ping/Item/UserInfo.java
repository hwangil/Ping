package com.example.gilsoo.ping.Item;

/**
 * Created by gilsoo on 2016-08-15.
 */
public class UserInfo {
    public String user_id;
    public String user_name;
    public String token;
    public String profile_url;

    public UserInfo(){
        user_id = " ";
        user_name = " ";
        token = " ";
        profile_url = " ";
    }
    public UserInfo(String user_id, String user_name, String token, String profile_url){
        this.user_id = user_id;
        this.user_name = user_name;
        this.token = token;
        this.profile_url = profile_url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getProfile_url() {
        return profile_url;
    }
}
