package lf.com.android.blackfishdemo.listener;

public interface OnVerifyCodeResultListener {
    void sendSuccess();//发送成功

    void sendCodeFailure();//发送失败

    void getCodeSuccess();//获取验证码成功

    void getCodeFailure();//获取验证码失败


    void submitCodeSuccesss(String phoneNumber, String data);//验证成功

    void submitCodeFailure();//验证失败

}
