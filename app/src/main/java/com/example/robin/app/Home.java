package com.example.robin.app;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.Context.MODE_PRIVATE;

public  class Home extends Fragment {
    WebView mWebView;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        mWebView = view.findViewById(R.id.webview);
       mWebView.setWebViewClient(new WebViewClient());
        SharedPreferences prefs = getActivity().getSharedPreferences("user",MODE_PRIVATE);
        String restoredText = prefs.getString("status", null);
        if (restoredText != null) {
            mWebView.loadUrl("https://photogenic0001.000webhostapp.com/photogenic/photogenic1.0/setsession.php?q="+restoredText);
        }
       //// mWebView.loadUrl("https://photogenic0001.000webhostapp.com/photogenic/photogenic1.0/home.php");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && mWebView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });
        return view;
    }

    private void webViewGoBack(){
        mWebView.goBack();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M){
                    Intent intent = new Intent(getActivity(),Status_Upload.class);
                    startActivity(intent);
                }
                else {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        Intent intent = new Intent(getContext(),Status_Upload.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}