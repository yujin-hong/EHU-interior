//package com.yujinhong.myapplication2.Kakao;
//
//import android.content.Intent;
//
//import com.kakao.auth.ISessionCallback;
//import com.kakao.network.ErrorResult;
//import com.kakao.usermgmt.UserManagement;
//import com.kakao.usermgmt.callback.MeV2ResponseCallback;
//import com.kakao.usermgmt.response.MeV2Response;
//import com.kakao.util.exception.KakaoException;
//import com.kakao.util.helper.log.Logger;
//import com.yujinhong.myapplication2.Main3Activity;
//import com.yujinhong.myapplication2.MainActivity;
//import com.yujinhong.myapplication2.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SessionCallback implements ISessionCallback {
//
//    @Override
//    public void onSessionOpened() {
//        Logger.e("hihihihihi open successss==========================");
//        requestMe();
//    }
//
//    @Override
//    public void onSessionOpenFailed(KakaoException exception) {
//        Logger.e("hihihihihi open fail==========================");
//
//    }
//
//    public void requestMe() {
//        // 사용자정보 요청 결과에 대한 Callback
//        List<String> keys = new ArrayList<>();
//        keys.add("properties.nickname");
//        keys.add("properties.profile_image");
//        keys.add("kakao_account.email");
//
//        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
//            @Override
//            public void onFailure(ErrorResult errorResult) {
//                String message = "failed to get user info. msg=" + errorResult;
//                Logger.e(message);
//            }
//
//            @Override
//            public void onSessionClosed(ErrorResult errorResult) {
//                Logger.e(errorResult.getErrorMessage());
//            }
//
//            @Override
//            public void onSuccess(MeV2Response response) {
//                Logger.e("Success!!!!!!!!!!!!!!!!!!!");
//                Logger.e("user id : " + response.getId());
////                Logger.e("user id : " + response.getKakaoAccount().getProfile().getNickname());
////                Logger.e("user id : " + response.getKakaoAccount().getBirthday());
//
////                Logger.e("email: " + response.getKakaoAccount().getEmail());
////                Logger.e("profile image: " + response.getKakaoAccount().getProfile().getProfileImageUrl());
//            }
//
//        });
//    }
//
//
//}