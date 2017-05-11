package com.example.gilsoo.ping;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Item.GroupItem;
import com.example.gilsoo.ping.Kakao.CustomKakaoCallback;
import com.example.gilsoo.ping.Kakao.KakaoFunc;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.Card;
import com.example.gilsoo.ping.Network.Retrofit.Group;
import com.example.gilsoo.ping.Network.Retrofit.KakaoLogin;
import com.example.gilsoo.ping.Shopping.ShowSponsorActivity;
import com.example.gilsoo.ping.Show_All.ShowAllFragment;
import com.example.gilsoo.ping.Show_Group.ShowGroupFragment;
import com.example.gilsoo.ping.Show_More.ShowMoreFragment;
import com.kakao.auth.Session;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        , ShowAllFragment.ChangeBar {

    Fragment currentFragment;
    NavigationView navigationView;

    View headerLayout;
    Toolbar search_toolbar;
    Toolbar main_toolbar;
    EditText cardSearch_editText;
    Button cardSearch_button;

    ArrayList<CardItem> searchedCardList = null;

    static int flagNum =0;      // **** 이거 고쳐야됨, 쓰레드, 동기화에 대한 지식이 아직 부족해서 일단 이렇게 했음 ㅠㅠ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d("gilsoo_Retro", "token : " + CommonData.user.getToken());
        if (Session.getCurrentSession().isOpened()) {
            Log.d("gilsoo_Kakao", "Session is Opened()");
            KakaoFunc.requestMe(new CustomKakaoCallback() {
                @Override
                public void changeNavHeader() {
                    headerLayout = navigationView.getHeaderView(0);     // navigation 바 헤더 유저정보바꾸기
                    TextView nav_userid = (TextView) headerLayout.findViewById(R.id.nav_userid);
                    nav_userid.setText(CommonData.user.getUser_name());
                    CircularImageView nav_userimage = (CircularImageView) headerLayout.findViewById(R.id.nav_userimage);
                    nav_userimage.setBorderWidth(10);
                    Log.d("gilsoo_Kakao", CommonData.user.getUser_id());
//                    Log.d("gilsoo_Kakao", CommonData.user.getUser_name());
                    Log.d("gilsoo_Kakao", CommonData.user.getProfile_url());
                    Glide.with(getApplicationContext()).load(CommonData.user.getProfile_url()).fitCenter().into(nav_userimage);
                    Toast.makeText(getApplicationContext(), "환영합니다! " + CommonData.user.user_name + " 님", Toast.LENGTH_SHORT).show();
                    // Todo. 서버와 연결 - 카카오로그인
                    kakaoLoginToPing(CommonData.user.getUser_id(), CommonData.user.getToken());         // 카카오 로그인이 되면 핑서버에서 카드리스트 받기

                }
            });
        } else {
            headerLayout = navigationView.getHeaderView(0);     // navigation 바 헤더 유저정보바꾸기
            TextView nav_userid = (TextView) headerLayout.findViewById(R.id.nav_userid);
            nav_userid.setText(CommonData.user.getUser_name());
            CircularImageView nav_userimage = (CircularImageView) headerLayout.findViewById(R.id.nav_userimage);
            nav_userimage.setBorderWidth(20);

            Glide.with(getApplicationContext()).load(CommonData.user.getProfile_url()).into(nav_userimage);
        }
        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        search_toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        cardSearch_editText = (EditText) findViewById(R.id.cardSearch_editText);
        cardSearch_button = (Button) findViewById(R.id.cardSearch_button);
        searchedCardList = new ArrayList<CardItem>();
        setCardSearchEvent();

        setSupportActionBar(main_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.b1);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // '드로어 열기' 에 해당하는 문자열 리소스 (접근성 지원용), '드로어 닫기'에 해당하는 문자열 리소스 (접근성 지원용)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.myFragment);      //content_main의 FrameLayout

        if (fragment == null) {
            ShowAllFragment showAllFragment = new ShowAllFragment();
            showAllFragment.setChangeBar(this);
            fragmentTransaction.add(R.id.myFragment, showAllFragment, "show_all");
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.showAll);
        }

        if(!Session.getCurrentSession().isOpened()) {
            downloadCardList(CommonData.user.getToken()); // 서버로부터 카드리스트 가져오기
            downloadGroupList(CommonData.user.getToken()); // 서버로부터 그룹리스트 가져오기
        }


        // User정보 받아오는 통신
    }

    long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        Fragment main;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager ft = getFragmentManager();
        currentFragment = ft.findFragmentById(R.id.myFragment);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(main_toolbar.getVisibility() == View.GONE){
                main_toolbar.setVisibility(View.VISIBLE);
                search_toolbar.setVisibility(View.GONE);
                searchedCardList.clear();
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                ft.popBackStack();
                return;
            }

            if (currentFragment.getTag().equals("show_partial")) {
                ft.popBackStack();
                return;
            } else if (!currentFragment.getTag().equals("show_all")) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ShowAllFragment showAllFragment = new ShowAllFragment();
                showAllFragment.setChangeBar(this);
                fragmentTransaction.replace(R.id.myFragment, showAllFragment, "show_all");
                fragmentTransaction.commit();
                navigationView.setCheckedItem(R.id.showAll);
                return;

            } else if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();

                Toast.makeText(getApplicationContext(), "한번더 누르면 종료합니다", Toast.LENGTH_SHORT).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                Toast.makeText(getApplicationContext(), "한번더 누르면 종료합니다", Toast.LENGTH_SHORT).cancel();
                moveTaskToBack(true);
                ActivityCompat.finishAffinity(this);        // 안드로이드 프로세스 종료시키는 법
                System.runFinalizersOnExit(true);           // 방법에 대해 말이 많음, 일단 이렇게 종료시켰음. 연구 필요!
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("gilsoo_Others", "onCreateOptionsMenu()");
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menu_search = menu.findItem(R.id.menu_search);
        MenuItem menu_share = menu.findItem(R.id.menu_share);

        if (ShowAllFragment.longClickFlag) {               // ㅣlong click 할때마다 메뉴 보이는거 달리해주려고
            menu_search.setVisible(false);
            menu_share.setVisible(true);
            Log.d("gilsoo_Others", "menu_search is false");
        } else {
            Log.d("gilsoo_Others", "menu_search is true");
            menu_search.setVisible(true);
            menu_share.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_share:           // 공유하기 버튼
                if(Session.getCurrentSession().isOpened())
                    if(ShowAllFragment.multiClickFlag > 0)
                // 한번에 뭉쳐서 보내는 방법을 모르겠음 ㅠㅠㅠ 지금은 그냥 카드 하나하나 보낼 상대를 지정해주어야함
                        KakaoFunc.kakaoLinkTest(this);
                    else
                        Toast.makeText(this, "카드를 선택하세요 ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "해당 서비스는 카카오톡 로그인 회원만 가능합니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_search:              // 카드 찾기 버튼
//                startActivity(new Intent(this, CardSearchingActivity.class));
                main_toolbar.setVisibility(View.GONE);
                search_toolbar.setVisibility(View.VISIBLE);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);       //DrawerLayout 잠그기

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ShowAllFragment searchFragment = ShowAllFragment.newInstance(null, searchedCardList);
                searchFragment.setChangeBar(this);
                fragmentTransaction.replace(R.id.myFragment, searchFragment, "show_search");
                fragmentTransaction.addToBackStack(null);               //백스택에 저장하려면!

                fragmentTransaction.commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.myFragment);      // content_main의 FrameLayout

        if (id == R.id.shopping) {      // 쇼핑하기
            navigationView.setCheckedItem(R.id.showAll);
            startActivity(new Intent(this, ShowSponsorActivity.class));                                 //여기 바꿔주기 ShoppingActivity.class

        } else if (id == R.id.showAll) {     // 전체보기
            if (!fragment.getTag().equals("show_all")) {           // 현재 fragment가 전체보기가 아니면 replace
                ShowAllFragment showAllFragment = new ShowAllFragment();
                showAllFragment.setChangeBar(this);
                fragmentTransaction.replace(R.id.myFragment, showAllFragment, "show_all");
                fragmentTransaction.commit();
            }
        } else if (id == R.id.showGroup) {      // 그룹보기
            if (!fragment.getTag().equals("show_group")) {
                ShowGroupFragment showGroupFragment = new ShowGroupFragment();
                showGroupFragment.setChangeBar(this);
                fragmentTransaction.replace(R.id.myFragment, showGroupFragment, "show_group");
                fragmentTransaction.commit();
            }
        } else if (id == R.id.showMore) {          // 더보기
            if (!fragment.getTag().equals("show_more")) {
                ShowMoreFragment showMoreFragment = new ShowMoreFragment();
                fragmentTransaction.replace(R.id.myFragment, showMoreFragment, "show_more");
                fragmentTransaction.commit();
            }
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void chagneBarMenu() {
        invalidateOptionsMenu();            // onCreateOptionMenu 함수 실행
        Log.d("gilsoo_Others", "changetBarMenu()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("gilsoo_LifeCycle", "MainActivity - onDestroy()");
    }
    public void setCardSearchEvent(){         // 카드 검색 이벤트 추가
        cardSearch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            // Todo. 카드 검색 Retrofit 추가
            public void onClick(View v) {
                downloadSearchedCard();
            }
        });
    }



    public static void downloadCardList(String token) {                                     // 서버로부터 카드리스트 다운로드
        CommonData.cardList.clear();
//        CommonData.cardList.add(new CardItem(String.valueOf(R.drawable.one), "one", "http://m.naver.com", "group1"));
//        CommonData.cardList.add(new CardItem(String.valueOf(R.drawable.two), "two", "http://m.naver.com", "group3"));
//        CommonData.cardList.add(new CardItem(String.valueOf(R.drawable.three), "three", "http://m.naver.com", "group3"));
        Log.d("gilsoo_Retro", "downloadCardLIst - token : " + token);
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<List<Card>> callCard = networkService.newCard(token);           // arg. token
        callCard.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if (response.isSuccessful()) {
                    Log.d("gilsoo_Retro", "downloadCardList() response.isSuccesful()");
                    List<Card> tempCardList = response.body();
                    for (int i = 0; i < tempCardList.size(); i++) {
                        Card tempCard = tempCardList.get(i);
                        CardItem tempCardItem = new CardItem(tempCard.getPhoto_url(), tempCard.getMemo(), tempCard.getInternet_url(),
                                tempCard.getGroupname(), tempCard.getCard_id(), tempCard.getBookmark());

                        CommonData.cardList.add(tempCardItem);      // Ping 내 ArrayList 자료구조에 저장
                        Log.d("gilsoo_Retro", "cardInfo ; " + tempCard.getPhoto_url() + " " + tempCard.getMemo() + " " + tempCard.getInternet_url() + " " +
                                tempCard.getGroupname() + " " + tempCard.getCard_id() + " " + tempCard.getBookmark());
                    }
                    tempFlag();
                    ShowAllFragment.myNotifyDataSetChanged();           // 다른 방법있으면 다른방법으로
                } else {
                    Log.d("gilsoo_Retro", "downloadCardList() : onResponse() : " + response.message());
                    Log.d("gilsoo_Retro", "downloadCardList() : onResponse() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Log.d("gilsoo_Retro", "downloadCardList() : onFailure() : " + t.getMessage());
            }
        });
    }

    public static void downloadGroupList(String token) {
        CommonData.groupList.clear();
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<List<Group>> callGroup = networkService.newGroup(token);
        callGroup.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    Log.d("gilsoo_Retro", "downloadGroupList response.isSuccessful()");
                    List<Group> tempGroupList = response.body();
                    response.body();
                    for (int i = 0; i < tempGroupList.size(); i++) {
                        Group tempGroup = tempGroupList.get(i);
//                        Log.d("gilsoo_Retro", " groupInfo : Group.groupImage - " +tempGroup.getPhoto_url() + " Group.groupName - " +  tempGroup.getGroupname());
                        GroupItem tempGroupItem = new GroupItem(tempGroup.getGroupname(), tempGroup.getPhoto_url());

                        CommonData.groupList.add(tempGroupItem);    // Ping 내 ArrayList 자료구조에 저장
                        Log.d("gilsoo_Retro", " groupInfo : groupImage - " + tempGroupItem.getGroupImage() + " groupName - " + tempGroupItem.getGroupName());
                    }
                    tempFlag();
                    ShowGroupFragment.myNotifyDataSetChanged();
                } else {
                    Log.d("gilsoo_Retro", "downloadGroupList() : onResponse() " + response.message());
                    Log.d("gilsoo_Retro", "downloadGroupList() : onResponse() " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.d("gilsoo_Retro", "downloadGroupList() : onFailure() : " + t.getMessage());
            }
        });
    }

    public static void cardSorted() {
        ArrayList<CardItem> tempCardList = new ArrayList<CardItem>();
        Log.d("gilsoo_Cards", "cardSorted()");
        for (int i = 0; i < CommonData.groupList.size(); i++) {
            Log.d("gilsoo_Cards", "" + CommonData.groupList.get(i).getGroupName());
            tempCardList.clear();
            for (int j = 0; j < CommonData.cardList.size(); j++) {
                if (CommonData.cardList.get(j).getGroupInfo().equals(CommonData.groupList.get(i).getGroupName())) {
                    tempCardList.add(CommonData.cardList.get(j));
                }
            }

            CommonData.groupList.get(i).setCardItems(tempCardList);
            for (int k = 0; k < tempCardList.size(); k++) {
                Log.d("gilsoo_Cards", "" + tempCardList.get(k).getCard_id());
            }
        }
    }
    synchronized public static void tempFlag(){
        flagNum ++;
        if(flagNum == 2) {         // downloadCardList와 downloadGroupList 둘다 완료가 되면 cardSorted시작 !
            cardSorted();
            flagNum = 0;
        }

    }

    public void kakaoLoginToPing(String id, final String token){            ///카카오톡 로그인 -> 핑서버에서 해당 정보 받기
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();

        Call<Void> callKakaoLogin = networkService.newKakaoLogin(new KakaoLogin(id, token));
        callKakaoLogin.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("gilsoo_Kakao", "KakaoLoginToPing() : onResponse() : " + response.code());
//                    downloadCardList(token);            // 카카오톡 로그인이 되면 카드 다운로드
//                    downloadGroupList(token);
                } else {
                    Log.d("gilsoo_Kakao", "KakaoLoginToPing() : onResponse() : " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Kakao", "KakaoLoginToPing() : onFailure() : " + t.getMessage());
                Toast.makeText(getApplicationContext(), "네트워크 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadSearchedCard(){

    }


}
