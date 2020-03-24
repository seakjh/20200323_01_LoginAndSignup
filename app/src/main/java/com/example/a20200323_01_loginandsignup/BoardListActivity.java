package com.example.a20200323_01_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.example.a20200323_01_loginandsignup.databinding.ActivityBoardListBinding;
import com.example.a20200323_01_loginandsignup.datas.Black;
import com.example.a20200323_01_loginandsignup.utils.ServerUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoardListActivity extends BaseActivity {

    List<Black> blacks = new ArrayList<>();

    ActivityBoardListBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_list);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

        ServerUtil.getRequestBlackList(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                try {
                    int code = json.getInt("code");

                    if (code == 200) {

                        JSONObject data = json.getJSONObject("data");

                        JSONArray blackLists  = data.getJSONArray("black_lists");

                        for (int i=0; i < blackLists.length() ; i++) {
                            JSONObject bl = blackLists.getJSONObject(i);
                            Black blackPost = Black.getBlackFromJson(bl);
                            Log.d("블랙신고제목", blackPost.getTitle());

//                            파싱끝난 블랙신고글들을 배열에 담아둠.
                            blacks.add(blackPost);
                        }
//                        모두 담긴 게시글들 => 어댑터가 새로고침.

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
