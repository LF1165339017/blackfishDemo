package lf.com.android.blackfishdemo.listener;

import android.widget.EditText;

public interface OnVerifyCodeResultListener {
    void getCodeSuccess();//获取验证码成功

    void getCodeFailure();//获取验证码失败

    void submitCodeSuccesss(String phoneNumber, String data);//验证成功

    void submitCodeFailure();//验证失败

}
