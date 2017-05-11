package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class Join {
    private String passwd;
    private String repasswd;
    private String user_id;
    private String user_name;
    private String user_sex;
    private String user_birth;

    public Join(){}
    public Join(String user_id, String user_sex, String user_name, String passwd, String repasswd, String user_birth){
        this.passwd = passwd;
        this.repasswd = repasswd;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_sex = user_sex;
        this.user_birth = user_birth;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRepasswd() {
        return repasswd;
    }

    public void setRepasswd(String repasswd) {
        this.repasswd = repasswd;
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

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }
}
