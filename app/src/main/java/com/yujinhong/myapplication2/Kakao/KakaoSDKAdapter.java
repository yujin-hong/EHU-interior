package com.yujinhong.myapplication2.Kakao;

import android.app.Activity;
import android.content.Context;
import com.kakao.auth.*;
import com.yujinhong.myapplication2.GlobalApplication.GlobalApplication;

public class KakaoSDKAdapter extends KakaoAdapter {
    /**
     * Session Config에 대해서는 default값들이 존재한다. * 필요한 상황에서만 override해서 사용하면 됨. * @return Session의 설정값.
     */
    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }
}