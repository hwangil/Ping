//package com.example.gilsoo.ping.Shopping;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.gilsoo.ping.R;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
///**
// * Created by chanung on 2016-07-03.
// */
//public class MainWebView extends Activity {
//    WebView webView;
//    EditText editText;
//    boolean trans=true;
//    //GridFragment gridFragment;
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.webview_main);
//
//        init();
//        Log.d("MainWebView","로드됨.");
//
////        mainFragment2=(MainFragment2)getSupportFragmentManager().findFragmentById(R.id.container);
////        menuFragment2=new MenuFragment2();
//
//
//        WebSettings settings=webView.getSettings();
//        settings.setJavaScriptEnabled(true);                                     //자바스크립트를 사용가능하게 함
//        webView.setWebViewClient(new WebClient());                              //이부분이 있어야 웹뷰에서 링크를 타고 다른 페이지로 넘어 갈 수 있음.
//        webView.loadUrl("http://m.naver.com");
//    }
//
//
//    public void onCaptureClicked(View v){
//
//
//        webView.buildDrawingCache();
//        Bitmap captureView = webView.getDrawingCache();
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/capture3.jpeg");
//            captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(getApplicationContext(), webView.getUrl(), Toast.LENGTH_LONG).show();
//
//
//
//
//
//      //  ApplicationController application = ApplicationController.getInstance();
//        Log.d("Service:", "들어가기 전");
//       // application.buildNetworkService("52.78.64.107", 8082);
//
////        Upload upload=new Upload();
////        upload.filename="1";
////        upload.internet_url="1";
////        upload.memo="1";
////        upload.photo_url="1";
////        upload.user_id="hyunsu";
//
////        Call<Upload> uploadCall= networkService.newUpload(upload);
////        uploadCall.enqueue(new Callback<Upload>() {
////            @Override
////            public void onResponse(Call<Upload> call, Response<Upload> response) {
////                if(response.isSuccessful()){
////                    Log.i("Service","response.isSuccessful()");
////                }else{
////                    Log.i("Service","response.false()");
////                }
////            }
////
////            @Override
////            public void onFailure(Call<Upload> call, Throwable t) {
////
////            }
////        });
//
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public void onGoClicked(View v){
//        String URL=editText.getText().toString();
//        webView.loadUrl(URL);
//        Toast.makeText(getApplicationContext(), "이동!", Toast.LENGTH_LONG).show();                  //getActivity();
//    }
//
//
////    public void onFragmentChanged(int index){
////        if(index==1)
////            getSupportFragmentManager().beginTransaction().replace(R.id.container,gridFragment).commit();
////        else if (index==0)
////            getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();
////    }
//
//
//    private void init(){
//        webView=(WebView)findViewById(R.id.webView);                              //웹뷰 찾기.
//        editText=(EditText)findViewById(R.id.editText);
//    }
//
//    class WebClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }
//}
