package com.example.gilsoo.ping.Shopping;

/**
 * Created by chanung on 2016-07-04.
 */
public class SponsorGrid {
    private int image;
    private String name;
    private String url;

    public SponsorGrid (int image, String name, String url){
        this.image=image;
        this.name = name;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}