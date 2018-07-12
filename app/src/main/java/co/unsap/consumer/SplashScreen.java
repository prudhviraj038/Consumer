package co.unsap.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mac on 7/3/18.
 */

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();



        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if(SessionManager.getDeviceId(SplashScreen.this).equals("0")){

                    Intent i = new Intent(SplashScreen.this, TutorialActivity.class);
                    startActivity(i);

                }
                else if(SessionManager.getSessionId(SplashScreen.this).equals("0")) {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                }else{

                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);

                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
