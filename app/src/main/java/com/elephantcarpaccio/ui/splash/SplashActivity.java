package com.elephantcarpaccio.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.ui.cart.CartActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // Splash screen display for 3 seconds.
            Intent i = new Intent(SplashActivity.this, CartActivity.class);
            startActivity(i);
            finish();
        }, 3000);
    }
}
