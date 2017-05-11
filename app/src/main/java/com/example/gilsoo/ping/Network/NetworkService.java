package com.example.gilsoo.ping.Network;

import com.example.gilsoo.ping.Item.UserInfo;
import com.example.gilsoo.ping.Network.Retrofit.BookMark;
import com.example.gilsoo.ping.Network.Retrofit.Card;
import com.example.gilsoo.ping.Network.Retrofit.ChangeGroupName;
import com.example.gilsoo.ping.Network.Retrofit.ChangePassword;
import com.example.gilsoo.ping.Network.Retrofit.DeleteGroupName;
import com.example.gilsoo.ping.Network.Retrofit.Group;
import com.example.gilsoo.ping.Network.Retrofit.Join;
import com.example.gilsoo.ping.Network.Retrofit.KakaoLogin;
import com.example.gilsoo.ping.Network.Retrofit.Login;
import com.example.gilsoo.ping.Network.Retrofit.MakeGroup;
import com.example.gilsoo.ping.Network.Retrofit.ModifyMemo;
import com.example.gilsoo.ping.Network.Retrofit.MoveCard;
import com.example.gilsoo.ping.Network.Retrofit.Withdraw;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gilsoo on 2016-08-13.
 */
public interface NetworkService {

// ******************        fin. 2016.08.19  by gil            ********************* //

    @POST("auth/login/done")                                                 // 로그인
    Call<UserInfo> newLogin(@Body Login login);

    @POST("auth/login/kakao")                                                     // 카카오 로그인
    Call<Void> newKakaoLogin(@Body KakaoLogin kakaoLogin);

    @POST("auth/join/insert")                                               //회원가입
    Call<UserInfo> newJoin(@Body Join insert);

    @GET("card/list")                                                       // 카드리스트 받아오기
    Call<List<Card>> newCard(@Header("token") String token);

    @POST("group/grouplist")                                               // 그룹리스트 받아오기
    Call<List<Group>> newGroup(@Header("token") String token);

// ******************                   ********************* //

    @GET("card/delete/{card_id}")                                                   //카드 삭제
    Call<Void> newDeleteCard(@Header("token") String token , @Path("card_id") int card_id);

    @POST("card/update")                                                             //메모 수정
    Call<Void> newModifyMemo(@Header("token") String token, @Body ModifyMemo modifyMemo);

    @POST("card/card_group_move")                                                   // 카드이동
    Call<Void> newMoveCard(@Header("token") String token, @Body MoveCard moveCard);

    @POST("group/makegroup")                                                        //그룹 만들기      사실 그룹 만들때 그룹에 집어넣을 카드도 같이 선택하도록
    Call<Void> newMakeGroup(@Header("token") String token, @Body MakeGroup makeGroup);

    @POST("group/update_groupname")                                                 //그룹이름 변경
    Call<Void> newChangeGroupName(@Header("token") String token, @Body ChangeGroupName changeGroupName);

    @POST("group/delete_groupname")                                                  //그룹 삭제      @Body로 할필요가 있나 싶음
    Call<Void> newDeleteGroupName(@Header("token") String token, @Body DeleteGroupName deleteGroupName);

    @POST("auth/join/update")                                                       //비밀번호 변경
    Call<Void> newChangePassword(@Header("token") String token, @Body ChangePassword changePassword);

    @POST("auth/join/delete")                                                        //회원 탈퇴       아이디는 필요없나
    Call<Void> newWithdraw(@Header("token") String token, @Body Withdraw withdraw);

    @POST("card/bookmarking")                                                       //북마킹
    Call<Void> newBookMark(@Header("token") String token, @Body BookMark bookMark);

    // ******************        2016.09.26           ********************* //
    @GET("card/list/{text}")                                                       // 카드리스트 받아오기
    Call<List<Card>> newSearchedCard(@Header("token") String token, @Path("text") String text);
}
