package com.example.gilsoo.ping.Item;

import com.example.gilsoo.ping.R;

import java.util.ArrayList;

/**
 * Created by gilsoo on 2016-07-30.
 */
public class CommonData {
    public static UserInfo user = new UserInfo();
    public static ArrayList<CardItem> cardList = new ArrayList<CardItem>() {{

        add(new CardItem(String.valueOf(R.drawable.test4), "주방용품", "http://m.11st.co.kr/html/main.html", "주방",1, "0", 200, 200));
        add(new CardItem(String.valueOf(R.drawable.test3), "여행좀가자~", "http://m.naver.com", "Trip",2, "0", 200, 400));
        add(new CardItem(String.valueOf(R.drawable.test2), "test2", "http://m.naver.com", "group3",3, "0", 200, 300));
        add(new CardItem(String.valueOf(R.drawable.test1), "test1", "http://m.naver.com", "group1",4, "0", 200, 30));
        add(new CardItem(String.valueOf(R.drawable.five), "five", "http://m.naver.com", "group3",5, "0", 200, 150));
        add(new CardItem(String.valueOf(R.drawable.six), "six", "http://m.naver.com", "group2",6, "0", 200, 200));
        add(new CardItem(String.valueOf(R.drawable.seven), "seven", "http://m.naver.com", "group1",7, "0", 200, 5000));
        add(new CardItem(String.valueOf(R.drawable.eight), "seven", "http://m.naver.com", "group1",8, "0", 200, 180));
        add(new CardItem(String.valueOf(R.drawable.nine), "seven", "http://m.naver.com", "group1",9, "0", 200, 440));
        add(new CardItem(String.valueOf(R.drawable.ten), "seven", "http://m.naver.com", "group1",10, "0", 200, 200));

    }};      // static 으로 선언해서 계속 재활용하기
    public static ArrayList<GroupItem> groupList = new ArrayList<GroupItem>() {{
//        add(new GroupItem("group1", String.valueOf(R.drawable.ten)));
//        add(new GroupItem("group2", String.valueOf(R.drawable.six)));
//        add(new GroupItem("group3", String.valueOf(R.drawable.three)));
//        add(new GroupItem("미분류", String.valueOf(R.drawable.nine)));
//        add(new GroupItem("", String.valueOf(R.drawable.group_add)));          // + 버튼 기본적으로 마지막에
    }};    // 이것도 시작과 함께 서버로 받아 저장해놓자

}
