package com.example.robin.app;

import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Credintials extends AppCompatActivity{

    TextView textView;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credintials);
        textView = findViewById(R.id.icon);
        Login login = new Login();
        Typeface face = Typeface.createFromAsset(getAssets(),"font/Bilbo Swash Caps.ttf");
        textView.setTypeface(face);
        SharedPreferences prefs = getSharedPreferences("user",MODE_PRIVATE);
        String restoredText = prefs.getString("status", null);
        if (restoredText != null) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       // fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.enter_from_left);
        fragmentTransaction.add(R.id.cre,login,"login666");
        fragmentTransaction.commit();

        }



    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
            //moveTaskToBack(true);
        }
    }

}
