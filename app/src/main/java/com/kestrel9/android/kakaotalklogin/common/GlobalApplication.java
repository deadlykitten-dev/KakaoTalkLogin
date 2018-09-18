package com.kestrel9.android.kakaotalklogin.common;

import android.app.Application;

import com.kakao.auth.KakaoSDK;


/**
 * KakaoTalkLogin
 * Class: GlobalApplication
 * Created by kestr on 2018-09-17.
 * <p>
 * Description:
 */
public class GlobalApplication extends Application {

    private static volatile GlobalApplication instance = null;


    /**
     * singleton 애플리케이션 객체를 얻는다.
     *
     * @return singleton 애플리케이션 객체
     */

    public static GlobalApplication getGlobalApplicationContext() {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kestrel9.android.kakaotalklogin.GlobalApplication");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
