package com.ariri.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariri.myapplication.ui.home.HomeFragment;

public class splash extends AppCompatActivity {
    //11 Agustus, 10117231, Ahmad Ripai Rizki, IF7

    Animation atg, atgtwo, atgthree;
    ImageView gambar;
    TextView covid19, devloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        gambar= findViewById(R.id.gambar);
        covid19= findViewById(R.id.covid19);
        devloper= findViewById(R.id.devloper);

        Thread thread = new Thread() {
            public void run () {
                try {
                    sleep(2800);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(splash.this, MainActivity.class));
                }
            }
        };
        thread.start();



        covid19.startAnimation(atgtwo);
        gambar.startAnimation(atgtwo);
        devloper.startAnimation(atgthree);
    }
}
