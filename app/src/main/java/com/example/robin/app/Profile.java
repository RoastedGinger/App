package com.example.robin.app;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.TagLostException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class Profile extends Fragment{
    WebView mWebView;
    TextView textView,username;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notfication,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // textView  = getActivity().findViewById(R.id.logo);
        username = getActivity().findViewById(R.id.username);
        Typeface face1 = Typeface.createFromAsset(getActivity().getAssets(),"font/Bilbo-Regular.ttf");
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"font/Bilbo Swash Caps.ttf");
       // textView.setTypeface(face);
        username.setTypeface(face1);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab3);
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
