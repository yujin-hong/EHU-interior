package com.yujinhong.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        TextView typedId = (TextView) findViewById(R.id.typedId);
        TextView typedPw = (TextView) findViewById(R.id.typedPw);

        Intent intent = getIntent();

        String id = intent.getStringExtra("typed_id");
        String pw = intent.getStringExtra("typed_pw");

        typedId.setText(id);
        typedPw.setText(pw);

    }

    public void goBack(View V) {
        finish();
    }


}
