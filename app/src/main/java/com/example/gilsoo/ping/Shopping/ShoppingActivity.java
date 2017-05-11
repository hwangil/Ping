package com.example.gilsoo.ping.Shopping;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gilsoo.ping.Item.CommonData;
import com.example.gilsoo.ping.MainActivity;
import com.example.gilsoo.ping.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class ShoppingActivity extends AppCompatActivity {
    WebView webView;
    EditText editText;
    boolean trans = true;
    File saveFile;
    private String URL;
    WebBackForwardList urlHistoryList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_main);

        init();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);                                     //자바스크립트를 사용가능하게 함
        settings.setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {                             //이부분이 있어야 웹뷰에서 링크를 타고 다른 페이지로 넘어 갈 수 있음.
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                editText.setText(url);
                return true;
            }
        });
        webView.loadUrl(URL);
    }

    // Todo. 캡쳐 크기조정??
    public void onBtnCaptureClicked(View v) {
        int width = 0, height = 0;
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "접근을 허용해야만 캡쳐가 가능합니다.", Toast.LENGTH_LONG).show();
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    99999);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
            webView.buildDrawingCache();
            int bookmark = webView.getScrollY();
            Bitmap captureView = webView.getDrawingCache();
            try {
                saveFile = new File(Environment.getExternalStorageDirectory().toString() + "/capture3.jpeg");   //
                FileOutputStream fos = new FileOutputStream(saveFile);
                captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                width = captureView.getWidth();
                height = captureView.getHeight();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("gilsoo_Capture", "url : "+webView.getUrl());
            Log.d("gilsoo_Capture", "width : "+width);
            Log.d("gilsoo_Capture", "height : " + height);
//            new HttpUpload().execute(CommonData.user.getToken(), saveFile, webView.getUrl(), "memo", String.valueOf(bookmark), width, height);                // id, file, url, memo, bookmark, width, height -> 이미지 업로드
            new HttpUpload().execute(CommonData.user.getToken(), saveFile, webView.getUrl(), "memo", String.valueOf(bookmark));                // id, file, url, memo, bookmark, width, height -> 이미지 업로드
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            urlHistoryList = webView.copyBackForwardList();
            Log.d("gilsoo_Others", "" + urlHistoryList.getCurrentIndex());
            int prev = urlHistoryList.getCurrentIndex()-1;
            if(prev >0 && urlHistoryList.getItemAtIndex(prev).getUrl()
                    == urlHistoryList.getItemAtIndex(prev-1).getUrl()){     // 이전 url과 그이전 url이 같으면
                webView.goBack();               // goBack() 한번 더
                prev --;                        // set Text 하기위한 위치 포지션도 하나 깎기
            }
            editText.setText(urlHistoryList.getItemAtIndex(prev).getUrl());
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onGoClicked(View v) {
        URL = editText.getText().toString();         //  http:// 붙여주어야할듯, 경우의 수 따져서 조건문!
        webView.loadUrl(URL);
        Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_LONG).show();                  //getActivity();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);                              //웹뷰 찾기.
        editText = (EditText) findViewById(R.id.editText);
        URL = getIntent().getStringExtra("url");
        editText.setText(URL);

    }

    private class HttpUpload extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            String responseMsg = "SUCCESS";
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://52.78.178.155:8082/card/upload");
                post.addHeader("token", (String) params[0]);

                MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipart.addPart("userPhoto", new FileBody((File) params[1]));
                multipart.addPart("internet_url", new StringBody((String) params[2]));
                multipart.addPart("memo", new StringBody((String) params[3]));        //***
                multipart.addPart("bookmark", new StringBody((String) params[4]));

//                multipart.addPart("width", new StringBody((String) params[5]));
//                multipart.addPart("height", new StringBody((String) params[6]));

                post.setEntity(multipart);
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
//                entity.getContent();                   // json parsing으로 return code 해석해서 예외처리
            } catch (Exception e) {
                e.printStackTrace();
                responseMsg = e.getMessage();
            }
            return responseMsg;
        }

        @Override
        protected void onPostExecute(String responseMsg) {
            Log.d("gilsoo_Capture", "HttpUpload (image upload) : " + responseMsg);
            //Todo. 캡쳐완료 예외처리
            if (responseMsg.equals("SUCCESS")) {
                Toast.makeText(getApplicationContext(), "캡쳐가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                MainActivity.downloadCardList(CommonData.user.getToken());
                MainActivity.downloadGroupList(CommonData.user.getToken());
                // 다이얼로그 or Toast
            } else {
                Toast.makeText(getApplicationContext(), "캡쳐에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

