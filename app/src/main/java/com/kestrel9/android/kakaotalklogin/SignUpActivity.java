package com.kestrel9.android.kakaotalklogin;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.helper.log.Logger;
import com.kestrel9.android.kakaotalklogin.common.BaseActivity;
import com.kestrel9.android.kakaotalklogin.databinding.ActivitySignUpBinding;

import java.util.Map;

public class SignUpActivity extends BaseActivity {

    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void showSignUp() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding.setViewModel(signUpViewModel);
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSignUp(binding.getViewModel().getProperties());
            }
        });
    }

    private void requestSignUp(final Map<String, String> properties) {
        UserManagement.getInstance().requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                final String message = "UsermgmtResponseCallback : failure : " + errorResult;
                Logger.w(message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //세션이 닫혀 실패한 경우로 에러 결과를 받습니다.
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                showSignUp();
            }

            @Override
            public void onSuccess(Long result) {
                Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_SHORT).show();
                requestMe();
            }
        }, properties);
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                int result = errorResult.getErrorCode();
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Logger.e("onSessionClosed");
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                if (result.hasSignedUp() == OptionalBoolean.FALSE) {
                    showSignUp();
                } else {
                    redirectMainActivity();
                }
            }
        });
    }
}
