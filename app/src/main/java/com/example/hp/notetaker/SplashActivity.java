package com.example.hp.notetaker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    LinearLayout l1,l2, fullL;
    Animation uptodown,downtoup, myanim;
    Typeface myfont;
    TextView tvtitle;
    ImageView ivglobe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        l1 = (LinearLayout) findViewById(R.id.logos);
        l2 = (LinearLayout) findViewById(R.id.texts);
        fullL = (LinearLayout) findViewById(R.id.fullFrame);
        ivglobe = (ImageView) findViewById(R.id.imageglobe);
        tvtitle = (TextView) findViewById(R.id.title1);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        myfont = Typeface.createFromAsset(this.getAssets(), "fonts/fontmain.ttf");
        tvtitle.setTypeface(myfont);

        //Animaton of fullL
        if (Build.VERSION.SDK_INT>20) {
            fullL.postDelayed(new Runnable() {
                @Override
                public void run() {
                    revealeffectframe();
                }
            }, 100);
        }else{
            fullL.setVisibility(View.VISIBLE);
        }
        
        l2.setAnimation(downtoup);
        l1.setAnimation(uptodown);


        final Intent main1 = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                  /*  if (Build.VERSION.SDK_INT>20) {
                        fullL.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                backeffectframe();
                            }
                        }, 100);
                    }*/



                    startActivity(main1);
                    finish();


                }
            }
        };
        timer.start();


    }


    void revealeffectframe(){
        if (Build.VERSION.SDK_INT>20){
            int cx = fullL.getMeasuredWidth()/2;
            int cy = fullL.getMeasuredHeight()/2;
            int radius = Math.max(fullL.getWidth(),fullL.getHeight());
            android.animation.Animator a = ViewAnimationUtils.createCircularReveal(fullL,cx,cy,0,radius);
            a.setDuration(1000);
            fullL.setVisibility(View.VISIBLE);
            a.start();

        }
    }
    void backeffectframe(){
        if (Build.VERSION.SDK_INT>20){
            int cx = fullL.getMeasuredWidth();
            int cy = fullL.getMeasuredHeight();
            int radius = Math.max(fullL.getWidth(),fullL.getHeight());
            android.animation.Animator a = ViewAnimationUtils.createCircularReveal(fullL,cx,cy,radius,0);
            a.setDuration(1000);
            fullL.setVisibility(View.INVISIBLE);
            a.start();


        }
    }
}
