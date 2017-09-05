package com.james.animalshome;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ZoomButtonsController;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class WebViewActivity extends Activity {
    private static final String TAG = WebViewActivity.class.getSimpleName();
    WebView webView;
    String webViewUrl, webName, webAcceptnum, webType;
    WebSettings webSettings;
    String mailUrl = "tcapoa8@mail.taipei.gov.tw";
    String vDirectionUrl;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Bundle extras = getIntent().getExtras();
        webViewUrl = extras.getString("URL");
        webName = extras.getString("name");
        webAcceptnum = extras.getString("acceptnum");
        webType = extras.getString("type");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("F618803C89E1614E3394A55D5E7A756B").build(); //Nexus 5
        mAdView.loadAd(adRequest);
        Button goabout = (Button) findViewById(R.id.btnRight);
        goabout.setText("寵物所在地");
        Button gologout = (Button) findViewById(R.id.btnLeft);
        gologout.setText("我要認養");
        gologout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:" + mailUrl));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "我想要認養(" + webName + " " + webAcceptnum+")");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "您的真實姓名:\n連絡電話:\n常用Email:\n我想認養:\n認養原因:\n居住城市:\n我的家庭成員:\n家中是否有其他寵物:\n請簡單自我介紹:\n");
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(emailIntent);
            }
        });
        goabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webType.equals("1")){
                    vDirectionUrl = "https://maps.google.com/maps?q=台北市動物之家";
                }else if(webType.equals("2")){
                    vDirectionUrl = "https://maps.google.com/maps?q=台北市動物之家";
                }else if(webType.equals("3")){
                    vDirectionUrl = "https://maps.google.com/maps?q=愛兔協會";
                }
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(vDirectionUrl) );
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        webView = (WebView) findViewById(R.id.webview);
        webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        setZoomControlGone(webView);
        webView.setInitialScale(180);
        webView.loadUrl(webViewUrl);
        webView.setBackgroundColor(0x00000000);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        }
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Hashtable<String, String> map = new Hashtable<String, String>();
                view.loadUrl(url, map);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right_1, R.anim.slide_in_right_2);
    }

    public void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
