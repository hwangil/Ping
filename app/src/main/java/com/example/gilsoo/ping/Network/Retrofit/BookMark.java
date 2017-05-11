package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-20.
 */
public class BookMark {
    private String card_id;
    private String bookmark;

    public BookMark(){}

    public BookMark(String card_id, String bookmark){
        this.card_id = card_id;
        this.bookmark = bookmark;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }
}
