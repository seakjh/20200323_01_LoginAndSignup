package com.example.a20200323_01_loginandsignup;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.a20200323_01_loginandsignup.databinding.ActivityLoginBinding;
import com.example.a20200323_01_loginandsignup.datas.User;
import com.example.a20200323_01_loginandsignup.utils.ContextUtil;
import com.example.a20200323_01_loginandsignup.utils.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignUpActivity.class);
                startActivity(intent);
            }
        });

//        체크박스에 체크가 될때 마다 (변화가 있을때 마다)
//        체크 여부를 저장

        binding.autoLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("체크여부", isChecked+"");

//                ContextUtil을 이용해서, 체크 여부를 저장.
                ContextUtil.setAutoLoginCheck(mContext, isChecked);

            }
        });

//        로그인 버튼을 누르면 => 아이디 저장이 체크되어 있다면
//        ->입력 되어있는 이메일 저장
//        그렇지 않다면 -> 이메일을 빈칸 ""으로 저장
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

////                체크 박스에 체크가 되어있나?
//                if (binding.autoLoginCheckBox.isChecked()){
//
////                    체크가 되어있는 상황
//                    String inputEmail = binding.emailEdt.getText().toString();
//
//                    ContextUtil.setEmail(mContext, inputEmail);
//                }
//                else {
////                    체크가 안된 상황
//                    ContextUtil.setEmail(mContext, "");
//                }

                String inputEmail = binding.emailEdt.getText().toString();
                String inputPw = binding.pwEdt.getText().toString();
                ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

//                        응답 실행 코드는 => 비동기 처리가 반드시 필요함.
//                        비동기 : 다른 할 일들을 하다가, 완료되면 별도로 실행해주자.
//                        OkHttp : 비동기 처리를 자동으로 지원 => 별도 쓰레드가 알아서 처리중
//                        => onResponse는 다른 쓰레드가 돌리고 있다.
//                        UI동작은 별도의 메인쓰레드가 전용으로 처리함
//                        -> 다른쓰레드가 UI를 건드리면 앱이 터짐.

                        Log.d("JSON내용-메인에서", json.toString());

                        try {
                            final String message = json.getString("message");
                            Log.d("서버가 주는 메세지", message);

                            int code = json.getInt("code");
                            Log.d("서버가 주는 코드값", code + "");

                            if (code == 200) {
//                                해당 기능이 성공적으로 동작
//                                로그인 성공!
                                JSONObject data = json.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");
                                final String token = data.getString("token");

//                                로그인한 사람의 이름/폰번 토스트로 띄워봄
//                                final String name = user.getString("name");
//                                final String phone = user.getString("phone");

                                final User loginUser = User.getUserFromJson(user);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, String.format("%s / %s", loginUser.getName(), loginUser.getPhone()), Toast.LENGTH_SHORT).show();
//                                        따온 토큰을 SharedPreferences에 저장.
//                                        => 로그인에 성공했다 + 내가 누군지 기록
                                        ContextUtil.setUserToken(mContext, token);

//                                        메인화면으로 진입 => 내 프로필 정보를 출력 => 저장된 토큰을 이용할 예정.
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                            else{
//                                뭔가 문제가 있었다.

//                                Toast를 띄우는데 앱이 죽는다! -> UI쓰레드가 아닌데, 토스트를 띄우니까.
//                                조치 : UI쓰레드 안에서 토스트를 띄우도록

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void setValues() {

//        이 화면을 켜면, 저장된 이메일 값을 emailEdt에 입력
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));

        binding.autoLoginCheckBox.setChecked(ContextUtil.isAutoLoginCheck(mContext));
    }
}
