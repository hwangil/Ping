package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-13.
 */
public class ModifyMemo {
    private String card_id;
    private String memo;

    public ModifyMemo(){}

    public ModifyMemo(String card_id, String memo){
        this.card_id = card_id;
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
}
