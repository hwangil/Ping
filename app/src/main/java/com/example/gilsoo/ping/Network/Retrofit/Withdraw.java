package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class Withdraw {
    private String passwd;
    private String repasswd;

    public Withdraw(){}

    public Withdraw(String passwd, String repasswd){
        this.passwd = passwd;
        this.repasswd = repasswd;
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
}
