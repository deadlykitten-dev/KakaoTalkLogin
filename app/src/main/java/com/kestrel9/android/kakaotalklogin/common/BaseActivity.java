package com.kestrel9.android.kakaotalklogin.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.kestrel9.android.kakaotalklogin.LoginActivity;
import com.kestrel9.android.kakaotalklogin.MainActivity;
import com.kestrel9.android.kakaotalklogin.SignUpActivity;

/**
 * KakaoTalkLogin
 * Class: BaseActivity
 * Created by kestr on 2018-09-17.
 * <p>
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void redirectSignUpActivity() {
        final Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void redirectMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
