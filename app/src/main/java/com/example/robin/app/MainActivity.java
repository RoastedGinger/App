package com.example.robin.app;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity{
    MenuItem item1;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Home home = new Home();
            Profile profile = new Profile();
            Dashboard dashboard = new Dashboard();
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    fragmentTransaction.replace(R.id.container,home);
                   //fragmentTransaction.addToBackStack("home");
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.container,dashboard);
                   //fragmentTransaction.addToBackStack("dashboard");
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:

                    fragmentTransaction.replace(R.id.container,profile);
                    //fragmentTransaction.addToBackStack("navigation");
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Home home = new Home();
        fragmentTransaction.replace(R.id.container,home);
        fragmentTransaction.addToBackStack("home");
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {

            super.onBackPressed();
            moveTaskToBack(true);
    }
}
