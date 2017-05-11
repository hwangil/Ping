package com.example.gilsoo.ping.Network.Retrofit;

/**
 * Created by gilsoo on 2016-09-26.
 */
public class KakaoLogin {
    private String kakao_id;
    private String kakao_token;

    public KakaoLogin(String kakao_id, String kakao_token){
        this.kakao_id = kakao_id;
        this.kakao_token = kakao_token;
    }
    public String getKakao_id() {
        return kakao_id;
    }

    public void setKakao_id(String kakao_id) {
        this.kakao_id = kakao_id;
    }

    public String getKakao_token() {
        return kakao_token;
    }

    public void setKakao_token(String kakao_token) {
        this.kakao_token = kakao_token;
    }
}
