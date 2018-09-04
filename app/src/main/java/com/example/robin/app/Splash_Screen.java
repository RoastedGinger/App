package com.example.robin.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {
TextView textView,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        Typeface face1 = Typeface.createFromAsset(getAssets(),"font/Bilbo-Regular.ttf");
        Typeface face = Typeface.createFromAsset(getAssets(),"font/Bilbo Swash Caps.ttf");
        textView.setTypeface(face);
        textView1.setTypeface(face1);

        Thread t =new Thread(){
            public void run(){
                try{
                    sleep(1500);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent i =new Intent(Splash_Screen.this,Credintials.class);
                    startActivity(i);
                }
            }
        };
        t.start();
    }
}
