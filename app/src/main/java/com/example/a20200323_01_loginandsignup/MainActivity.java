package com.example.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.a20200323_01_loginandsignup.databinding.ActivityMainBinding;
import com.example.a20200323_01_loginandsignup.utils.ContextUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = binding.emailEdt.getText().toString();

                ContextUtil.getEmail(mContext);
            }
        });

    }

    @Override
    public void setValues() {

//        이 화면을 켜면, 저장된 이메일 값을 emailEdt에 입력
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));

    }
}
