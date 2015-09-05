package io.moffat.kitchenpal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.parse.ParseUser;


public class SplashScreen extends Activity {

    private static int TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ProgressDialog prog = new ProgressDialog(SplashScreen.this);
                prog.setMessage("Loading Kitchen Pal");
                prog.setTitle("Loading...");
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null){
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);

                } else {
                    Intent login = new Intent(SplashScreen.this, LoginActivity2.class);
                    startActivity(login);
                }

                prog.dismiss();
                finish();
            }

        }, TIME);


    }


}
