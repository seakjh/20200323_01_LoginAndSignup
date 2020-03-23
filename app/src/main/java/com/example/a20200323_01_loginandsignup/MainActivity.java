package com.example.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.example.a20200323_01_loginandsignup.databinding.ActivityMainBinding;
import com.example.a20200323_01_loginandsignup.datas.User;
import com.example.a20200323_01_loginandsignup.utils.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    }

    @Override
    public void setValues() {

        ServerUtil.getRequestUserList(mContext, "ALL", new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                Log.d("사용자목록", json.toString());
            }
        });

//        저장된 토큰을 확인해서
//        => 그 토큰으로 내 정보를 서버에서 다시 불러올것 - 조회 (GET).

        ServerUtil.getRequestMyInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("내 정보", json.toString());

                try {
                    int code = json.getInt("code");

                    if(code == 200){
                        JSONObject data = json.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");

                        final User myInfo = User.getUserFromJson(user);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nameTxt.setText(myInfo.getName());
                                binding.memoTxt.setText(myInfo.getName());
                                binding.phoneTxt.setText(myInfo.getName());
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
