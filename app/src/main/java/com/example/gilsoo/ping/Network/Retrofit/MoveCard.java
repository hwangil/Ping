package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-08-20.
 */
public class MoveCard {
    private String card_id;
    private String card_group_move;

    public MoveCard(){}

    public MoveCard(String card_id, String card_group_move){
        this.card_id = card_id;
        this.card_group_move = card_group_move;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_group_move() {
        return card_group_move;
    }

    public void setCard_group_move(String card_group_move) {
        this.card_group_move = card_group_move;
    }
}
