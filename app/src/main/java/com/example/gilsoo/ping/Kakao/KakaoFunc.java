package com.example.gilsoo.ping.Kakao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Item.UserInfo;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.KakaoParameterException;
import com.kakao.util.helper.log.Logger;

/**
 * Created by gilsoo on 2016-09-24.
 */
public class KakaoFunc {

    // kakao
    public static void requestMe(final CustomKakaoCallback customKakaoCallback) {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

//                redirectLoginActivity();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
//                redirectLoginActivity();
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.d("gilsoo_Kakao: ", "id : " + userProfile.getId());
                Log.d("gilsoo_Kakao: ", "nickname : " + userProfile.getNickname());
                Log.d("gilsoo_Kakao: ", "profile image : " + userProfile.getProfileImagePath());
                CommonData.user = new UserInfo(String.valueOf(userProfile.getId()), userProfile.getNickname()
                        , Session.getCurrentSession().getAccessToken(), userProfile.getProfileImagePath());
                customKakaoCallback.changeNavHeader();

            }

            @Override
            public void onNotSignedUp() {
//                showSignup();
            }
        });
    }


    //** Token에 따라 사용자의 정보를 얻는함수.
    public static void requestAccessTokenInfo() {
        AuthService.requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("gilsoo_Kakao", "onSessionClosed()");
//            redirectLoginActivity(self);
            }

            @Override
            public void onNotSignedUp() {
                Log.d("gilsoo_Kakao", "onNotSignedUp()");
                // not happened
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.d("gilsoo_Kakao", "onFailure()");
                Logger.e("failed to get access token info. msg=" + errorResult);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse accessTokenInfoResponse) {
                long userId = accessTokenInfoResponse.getUserId();
                Logger.d("this access token is for userId=" + userId);

                long expiresInMilis = accessTokenInfoResponse.getExpiresInMillis();
                Logger.d("this access token expires after " + expiresInMilis + " milliseconds.");

                Log.d("gilsoo_Kakao", " user Id? : " + userId);

            }
        });
    }
    public static void kakaoLinkTest(Context _context) {
        int count = 0;
        Context context = _context;
        KakaoLink kakaoLink = null;
        KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = null;
        try {
            kakaoLink = KakaoLink.getKakaoLink(context);
        }catch (KakaoParameterException e){}

        for(int i=0; i<CommonData.cardList.size(); i++) {
            CardItem cardItem = CommonData.cardList.get(i);
            if(cardItem.isSelected()) {
                count ++;
                try {
                    String url = "밑에 링크를 눌러주세요 \n" + cardItem.getUrl();
                    String imageUrl =  cardItem.getImg();
                    int imageWidth = 100;
                    int imageHeight = 100;
//                    String buttonText = "해당 사이트로 이동하기";


                    kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

                    kakaoTalkLinkMessageBuilder
                            .addText(url)
                            .addImage(imageUrl, imageWidth, imageHeight)
                            .build();
                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, context);

                } catch (KakaoParameterException e) {
                }
            }
        }
        if(count>0) {
            Toast.makeText(context, count + "개의 카드 각각 보낼 상대를 지정해주어야 해요 ㅠㅠ 죄송해여 엉엉", Toast.LENGTH_LONG).show();
            count = 0;
        }



    }

}
