package com.example.feroz.randomnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by feroz on 16-Jan-16.
 */
public class WelcomeScreen extends Activity {

    Animation animFadeIn, animFadeOut;
    TextView txtMessage;
        // Splash screen timer
        private static int SPLASH_TIME_OUT = 2000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.welcome);

            txtMessage = (TextView) findViewById(R.id.randomnews);
            RotateAnimation rotate= (RotateAnimation)AnimationUtils.loadAnimation(this,R.anim.rotate);
            animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
            txtMessage.setAnimation(animFadeIn);
            txtMessage.setAnimation(rotate);

            CheckInternet ch = new CheckInternet(this);
            if(ch.isOK()) {

                new Handler().postDelayed(new Runnable() {

         /*
          * Showing splash screen with a timer. This will be useful when you
          * want to show case your app logo / company
          */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        Intent i = new Intent(WelcomeScreen.this, MainActivity.class);
                        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                        overridePendingTransition(R.anim.slideout, R.anim.slidein);
                        // close this activity
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }else{
                Toast.makeText(getApplicationContext(), "No Internet :(", Toast.LENGTH_LONG).show();
            }
        }

}
