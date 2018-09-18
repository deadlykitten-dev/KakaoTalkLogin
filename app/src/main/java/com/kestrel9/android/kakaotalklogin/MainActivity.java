package com.kestrel9.android.kakaotalklogin;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kestrel9.android.kakaotalklogin.common.BaseActivity;

public class MainActivity extends BaseActivity {

    LinearLayout layout;

    private ISessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.ll_loading_container);

        callback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                Log.d("login", "success");
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                redirectLoginActivity();
            }
        };

        Session.getCurrentSession().addCallback(callback);

        Button btnProgress = findViewById(R.id.btn_progress);
        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Session.getCurrentSession().checkAndImplicitOpen()){
                    redirectLoginActivity();
                }
            }
        });
    }

    private void startProgress() {
        layout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setVisibility(View.GONE);
            }
        }, 3500);
    }
}
