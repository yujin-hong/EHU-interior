package com.yujinhong.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kakao.util.helper.log.Logger;
import com.yujinhong.myapplication2.ui.login.LoginActivity;
import com.yujinhong.myapplication2.ui.login.SignupActivity;

import java.util.List;
import java.util.Set;


public class StartActivity extends AppCompatActivity {
    SharedPreferences loginInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginInformation = this.getSharedPreferences("setting",0);
        List<String> emailStrings = sharedPreferenceArrayList.getJsonArrayList(loginInformation, "email");

//        setContentView(R.layout.activity_start);

        if(emailStrings.isEmpty()) {
            setContentView(R.layout.activity_start);
        } else {
//            Toast.makeText(getApplicationContext(), emailLength, Toast.LENGTH_LONG).show();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void goSignin (View V) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void goSignup (View V) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}
