package com.example.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

//        토큰이 저장되어 있고 & 자동로그인이 체크되어 있다면
//         => 메인액티비티로 이동.
        Handler handler = new Handler();

//        그렇지 않다면 => 로그인액티비티로 이동.

    }
}
