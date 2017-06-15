package com.beta.tacademy.hellomoneycustomer.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.beta.tacademy.hellomoneycustomer.R;
import com.beta.tacademy.hellomoneycustomer.module.webhook.WebHook;
import com.beta.tacademy.hellomoneycustomer.recyclerViews.counselorDetailRecyclerView.CounselorDetailRecyclerViewAdapter;

import java.net.URISyntaxException;

public class KakaoWebViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_web_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (WebView)findViewById(R.id.webView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        setSupportActionBar(toolbar); //Toolbar를 현재 Activity의 Actionbar로 설정.

        //Toolbar 설정
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        toolbar.setTitle(getResources().getString(R.string.hello_money_kakao_contact));
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getApplicationContext().getResources(), R.color.normalTypo, null));



        webView.loadUrl("http://plus.kakao.com/home/@hellomoney"); //webView에 url설정
        webView.getSettings().setJavaScriptEnabled(true); //webView에 javaScirpt 활성화
        webView.setVerticalScrollBarEnabled(true); //webView에 수직 스크롤바 활성화
        webView.setHorizontalScrollBarEnabled(true); //webView에 수평 스크롤바 활성화
        webView.getSettings().setBuiltInZoomControls(false); //webView에 줌 기능 활성화
        webView.getSettings().setSupportZoom(false); //webView에 줌 기능 활성화
        webView.getSettings().setLoadWithOverviewMode(true); //웹뷰가 html 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        webView.getSettings().setUseWideViewPort(true); //webView가 html의 viewport 메타 태그를 지원(스크린 크기에 맞게 조정)

        //시작 전에 ProgressBar를 보여주어 사용자와 interact
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {
                String url = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    url = String.valueOf(webResourceRequest.getUrl());
                    if (url != null && url.startsWith("intent://")) {
                        try {
                            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                            if (existPackage != null) {
                                startActivity(intent);
                            } else {
                                Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                                marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                                startActivity(marketIntent);
                            }
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (url != null && url.startsWith("market://")) {
                        try {
                            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            if (intent != null) {
                                startActivity(intent);
                            }
                            return true;
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    view.loadUrl(url);
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
