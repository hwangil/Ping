package com.example.gilsoo.ping.Item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sansung on 2016-06-30.
 */
public class CardItem implements Parcelable {
    private String img;
    private String memo;
    private String url;
    private String groupInfo;
    private int card_id;
    private String bookmark;
    private boolean selected;
    private int width;
    private int height;

    public CardItem(){
        img = null;
        memo = null;
        url = null;
        groupInfo = null;
        card_id = -1;
        bookmark = null;
        selected = false;
    }
    public CardItem(String img, String memo, String url, String groupInfo) {
        this.img = img;
        this.memo = memo;
        this.url = url;
        this.groupInfo = groupInfo;
        card_id = -1;
        bookmark = null;
        selected = false;
        width = -1;
        height = -1;

    }
    public CardItem(String img, String memo, String url, String groupInfo, int card_id, String bookmark){
        this.img = img;
        this.memo = memo;
        this.url = url;
        this.groupInfo = groupInfo;
        this.card_id = card_id;
        this.bookmark = bookmark;
        selected = false;
        width = -1;
        height = -1;
    }
    public CardItem(String img, String memo, String url, String groupInfo, int card_id, String bookmark, int width, int height){    // width, height 정보있는 카드
        this.img = img;
        this.memo = memo;
        this.url = url;
        this.groupInfo = groupInfo;
        this.card_id = card_id;
        this.bookmark = bookmark;
        selected = false;
        this.width = width;
        this.height = height;
    }

    // Todo. width, height 카드 정보 들어있게 하면 이부분 수정 (Parcelable)
    protected CardItem(Parcel in) {
        img = in.readString();
        memo = in.readString();
        url = in.readString();
        groupInfo = in.readString();
        card_id = in.readInt();
        bookmark = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<CardItem> CREATOR = new Creator<CardItem>() {
        @Override
        public CardItem createFromParcel(Parcel in) {
            return new CardItem(in);
        }

        @Override
        public CardItem[] newArray(int size) {
            return new CardItem[size];
        }
    };

    public String getGroupInfo(){return groupInfo;}
    public String getUrl(){return url;}
    public void setGroupInfo(String groupInfo)
    {
        this.groupInfo=groupInfo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(memo);
        dest.writeString(url);
        dest.writeString(groupInfo);
        dest.writeInt(card_id);
        dest.writeString(bookmark);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
