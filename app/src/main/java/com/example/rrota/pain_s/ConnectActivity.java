package com.example.rrota.pain_s;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//TODO Прелоадер, чтобы пользователь понимал, что страница прогружается
public class ConnectActivity extends BaseActivity {
    private WebView webView;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        webView = (WebView) findViewById(R.id.webWiew2);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("http://ecoselect.ru/connect/");
        webView.setWebViewClient(new MyWebviewClient());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //improve webView performance

        webView.loadUrl("http://ecoselect.ru/connect/");
        //force links open in webview only
//        webView.setWebViewClient(new MyWebviewClient());

    }

    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("ecoselect.ru")) {
                //open url contents in webview
                return false;
            } else {
                //here open external links in external browser or app
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

        }
        //ProgressDialogue
        ProgressDialog pd = null;


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (count==0){
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    pd=new ProgressDialog(ConnectActivity.this,R.style.MyDialogStyle1);
                }
                else
                    pd=new ProgressDialog(ConnectActivity.this);
            pd.setTitle("Подождите");
            pd.setMessage("Идет загрузка страницы..");

            pd.show();
            Log.w("initializte ", url);
            count++;}

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.w("initializte ", "stop");
            pd.dismiss();
            count=0;
            super.onPageFinished(view, url);
        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                hideProgressDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
