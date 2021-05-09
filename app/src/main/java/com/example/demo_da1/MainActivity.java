package com.example.demo_da1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_da1.Account.Login;
import com.example.demo_da1.Account.Register;
import com.example.demo_da1.Action.HomeActivity;

public class MainActivity extends AppCompatActivity {
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        final Handler next = new Handler();
        next.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(MainActivity.this, Register.class);
                startActivity(it);
                finish();
                next.removeCallbacks(this);
            }
        }, 3000);
    }

}