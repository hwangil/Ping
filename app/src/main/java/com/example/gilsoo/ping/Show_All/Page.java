package com.example.gilsoo.ping.Show_All;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CardItem;
import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.Network.ApplicationController;
import com.example.gilsoo.ping.Network.NetworkService;
import com.example.gilsoo.ping.Network.Retrofit.BookMark;
import com.example.gilsoo.ping.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by songmho on 2015-01-02.
 */
public class Page extends android.support.v4.app.Fragment {
    WebView webView;
    int currentScroll;
    View head;
    //    Button bookmark;
    Button save;
    //    Button load;
    Toolbar toolbar;
    RelativeLayout relativeLayout;

    FloatingActionButton bookmark;
    FloatingActionButton load;

    String url = " ";
    int cardPosition = -1;
    ArrayList<CardItem> selectedCardList = null;

    public static Page newInstance(String url, int cardPosition, ArrayList<CardItem> selectedCardList){
        Page page = new Page();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("cardPosition", cardPosition);
        args.putParcelableArrayList("selectedCardList", selectedCardList);
        page.setArguments(args);
        return page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        relativeLayout = (RelativeLayout)inflater.inflate(R.layout.page, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            this.url = bundle.getString("url");
            this.cardPosition = bundle.getInt("cardPosition");
            this.selectedCardList = bundle.getParcelableArrayList("selectedCardList");
        }
        Log.d("gilsoo_Cards", " Page.cass : onCreateView() : " + url);
        init();
        webView.loadUrl(url);
        if(! selectedCardList.get(cardPosition).getBookmark().equals("null"))
            webView.scrollTo(0, Integer.parseInt(selectedCardList.get(cardPosition).getBookmark()));

        return relativeLayout;
    }

    public void init(){
        // toolbar=(Toolbar)relativeLayout.findViewById(R.id.toolbar_webView_card);
        webView = (WebView)relativeLayout.findViewById(R.id.cardWebView);
        webView.setWebViewClient(new WebViewClient());      //WebViewClient
        bookmark=(FloatingActionButton) relativeLayout.findViewById(R.id.bookmark);
        load=(FloatingActionButton)relativeLayout.findViewById(R.id.load);
        WebSettings set = webView.getSettings();            //setting 정보
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setDefaultTextEncodingName("utf-8");

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.scrollTo(0, Integer.parseInt(selectedCardList.get(cardPosition).getBookmark()));
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCardList.get(cardPosition).setBookmark(String.valueOf(webView.getScrollY()));
                cardbookmarking(CommonData.user.getToken()
                        , String.valueOf(selectedCardList.get(cardPosition).getCard_id()), selectedCardList.get(cardPosition).getBookmark());
                Toast.makeText(getActivity(),"북마크 저장",Toast.LENGTH_SHORT).show();
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
    }

    public void cardbookmarking(String token, String card_id, String bookmark){
        NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        Call<Void> callBookmark = networkService.newBookMark(token, new BookMark(card_id, bookmark));
        callBookmark.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("gilsoo_Retro", "cardBookmarking() : onResponse() : isSuccessful() : " + response.message());

                }else{
                    Log.d("gilsoo_Retro", "cardBookmarking() : onResponse() : isNotSuccessful() : " + response.message());
                    Log.d("gilsoo_Retro", "cardBookmarking() : onResponse() : isNotSuccessful() : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("gilsoo_Retro", "cardBookmarking() : onFailure() : " + t.getMessage());

            }
        });
    }

}