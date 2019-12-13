package com.yujinhong.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import android.content.Context;
//
//import android.content.pm.PackageInfo;
//
//import android.content.pm.PackageManager;
//
//import android.content.pm.Signature;
//
////import android.support.Nullable;
//
////import android.support.v7.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import android.util.Base64;
//
//import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.yujinhong.myapplication2.ui.login.LoginActivity;
import com.yujinhong.myapplication2.ui.login.SignupActivity;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.view.ViewGroup.LayoutParams;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    SharedPreferences loginInformation;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);

        super.onCreate(savedInstanceState);
        loginInformation = this.getSharedPreferences("setting",0);
//        Set<String> prevEmailSet = loginInformation.getStringSet("email", null);
//        Set<String> prevPwSet = loginInformation.getStringSet("password", null);
//        String[] emailStrings = prevEmailSet.toArray(new String[]{});
//        String[] pwStrings = prevPwSet.toArray(new String[]{});
        List<String> emailStrings = sharedPreferenceArrayList.getJsonArrayList(loginInformation, "email");
        List<String> pwStrings = sharedPreferenceArrayList.getJsonArrayList(loginInformation, "password");
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        getHashKey(mContext);
        int numId = emailStrings.size();
        int pwId = pwStrings.size();

        LayoutParams userParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        LayoutParams imageParams = new LinearLayout.LayoutParams(300, 300);
        LayoutParams textParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        LinearLayout idList = findViewById(R.id.idList);

        for(int i=0;i<numId;i++) {
            LinearLayout a = new LinearLayout(this);
            a.setOrientation(LinearLayout.VERTICAL);
            a.setLayoutParams(userParams);

            final String thisEmail = emailStrings.get(i);
            final String thisPw = pwStrings.get(i);

            ImageButton userImage = new ImageButton(this);
            userImage.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     signIn(thisEmail, thisPw);
                 }
             }
            );
            userImage.setLayoutParams(imageParams);
            userImage.setImageResource(R.mipmap.ic_launcher);
            TextView userId = new TextView(this);
            userId.setGravity(Gravity.CENTER);
            userId.setText(thisEmail); // it will be programmed differently by data
            userId.setLayoutParams(textParams);
            a.addView(userImage);
            a.addView(userId);
            idList.addView(a);
        }
        Logger.e("start");
        Logger.e(String.valueOf(numId));
        Logger.e(String.valueOf(pwId));

        Logger.e("end");

    }

    public void signIn(String email, String password) {
        Logger.e(email);
        Logger.e(password);

//        if(firebaseAuth.getCurrentUser()!=null) {
//            Logger.e("someone login already..");
//            firebaseAuth.signOut();
////            Logger.e(firebaseAuth.getCurrentUser().toString());
//        }
        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), Main5Activity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "시스템 에러입니다. 관리자에게 문의하세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
//        Logger.e("after");
//        Logger.e(firebaseAuth.getCurrentUser().getEmail());
    }

    public void onClick02(View V) {
        Toast.makeText(getApplicationContext(), "button pressed", Toast.LENGTH_LONG).show();
    }

    public void onClick03(View V) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-9725-2508"));
        startActivity(intent);
    }

    public void goLogin (View V) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void goSignup (View V) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }

        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }

    }
}

