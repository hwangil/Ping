package com.example.gilsoo.ping.Network;

import android.app.Activity;
import android.app.Application;

import com.example.gilsoo.ping.Kakao.KakaoSDKAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.auth.KakaoSDK;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gilsoo on 2016-08-17.
 */
public class ApplicationController extends Application {
    private static ApplicationController instance;
    private NetworkService networkService;
    private static volatile Activity currentActivity = null; // kakao

    public static ApplicationController getInstance() { return instance; }
    public NetworkService getNetworkService() { return networkService; }

    public static Activity getCurrentActivity()     {               // kakao
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {   // kakao
        ApplicationController.currentActivity = currentActivity;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        ApplicationController.instance = this;
        instance.buildNetworkService("52.78.178.155", 8082);

        KakaoSDK.init(new KakaoSDKAdapter());   // kakao
    }


    public void buildNetworkService(String ip, int port){
        synchronized (ApplicationController.class){
            String baseUrl = String.format("http://%s:%d/", ip, port);
            if(networkService == null){

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")                    //질문
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();


                networkService = retrofit.create(NetworkService.class);
//                if(networkService==null){
//                    Log.d("gilsoo_Retro", "네트워크서비스 할당안됨");
//                }
//                else{
//                    Log.d("gilsoo_Retro","네트워크서비스 할당됨");
//                }
            }
        }
    };

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

}
