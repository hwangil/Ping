package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class ChangePassword {
    private String passwd;
    private String update_passwd;
    private String update_repasswd;

    public ChangePassword(){}

    public ChangePassword(String passwd, String update_passwd, String update_repasswd){
        this.passwd = passwd;
        this.update_passwd = update_passwd;
        this.update_repasswd = update_repasswd;
    }


    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUpdate_passwd() {
        return update_passwd;
    }

    public void setUpdate_passwd(String update_passwd) {
        this.update_passwd = update_passwd;
    }

    public String getUpdate_repasswd() {
        return update_repasswd;
    }

    public void setUpdate_repasswd(String update_repasswd) {
        this.update_repasswd = update_repasswd;
    }
}
