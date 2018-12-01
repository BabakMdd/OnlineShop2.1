package com.example.pishgam.onlineshop2.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.pishgam.onlineshop2.R;
import com.example.pishgam.onlineshop2.Utilities.Setting;

public class Splash extends Activity {

    Setting setting=null;
    Thread timer=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(Splash.this);
        setting.setLocale("fa");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        //----------------------------------------Set FullScreen Splash-----------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //----------------------------------------Set Timer--------------------------------------

        timer=new Thread(){
            public void run(){
                try {
                    sleep(3000);
                    setting.openActivity(Main.class,new View(Splash.this));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
