package com.kestrel9.android.kakaotalklogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
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

        Button btnUnlink = findViewById(R.id.btn_unlink);

        btnUnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUnlink();
            }
        });

        final Button btnProgress = findViewById(R.id.btn_progress);
        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                    btnProgress.setText("로그인");
                    redirectLoginActivity();
                } else {
                    btnProgress.setText("로그아웃");
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {

                        }
                    });
                }
            }
        });
    }

    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                        redirectSignUpActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long result) {
                                        redirectLoginActivity();
                                    }
                                });
                            }
                        }
                ).setNegativeButton((getString(R.string.com_kakao_cancel_button)),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        ).show();
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
