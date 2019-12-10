package com.yujinhong.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
//import com.yujinhong.myapplication2.Kakao.SessionCallback;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.e("Here?");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
//        Session.getCurrentSession().checkAndImplicitOpen();
    }

    public void onClickLogin (View V) {

        EditText text_id = (EditText) findViewById(R.id.idText);
        EditText text_pw = (EditText) findViewById(R.id.pwText);

        String id = text_id.getText().toString();
        String pw = text_pw.getText().toString();

        System.out.println("this is id" + id);


        Intent intent = new Intent(getApplicationContext(), Main4Activity.class);

        intent.putExtra("typed_id", id);
        intent.putExtra("typed_pw", pw);

        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


    public class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Logger.e("hihihihihi open successss==========================");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Logger.e("hihihihihi open fail==========================");

        }

        public void requestMe() {
            // 사용자정보 요청 결과에 대한 Callback
            List<String> keys = new ArrayList<>();
            keys.add("properties.nickname");
            keys.add("properties.profile_image");
            keys.add("kakao_account.email");

            UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.e(message);
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Logger.e(errorResult.getErrorMessage());
                }

                @Override
                public void onSuccess(MeV2Response response) {
                    Logger.e("Success!!!!!!!!!!!!!!!!!!!");
                    Logger.e("user id : " + response.getId());
                    Logger.e("email: " + response.getKakaoAccount().getEmail());



                    redirectSignupActivity();
//                Logger.e("user id : " + response.getKakaoAccount().getProfile().getNickname());
//                Logger.e("user id : " + response.getKakaoAccount().getBirthday());

//                Logger.e("profile image: " + response.getKakaoAccount().getProfile().getProfileImageUrl());
                }

            });
        }


    }
    public void redirectSignupActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
