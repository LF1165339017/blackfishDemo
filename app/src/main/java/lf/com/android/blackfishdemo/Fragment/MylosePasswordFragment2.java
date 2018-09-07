package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.InputCompleterListener;
import lf.com.android.blackfishdemo.listener.OnVerifyCodeResultListener;
import lf.com.android.blackfishdemo.util.CountDownUtil;
import lf.com.android.blackfishdemo.util.LogUtil;
import lf.com.android.blackfishdemo.util.SpannableStringUtil;
import lf.com.android.blackfishdemo.view.ViewCodeView;

public class MylosePasswordFragment2 extends BaseFragment {
    private String PhoneNumber;
    private TextView mTvMessage;
    private TextView mTvMessage1;
    private TextView mTvMessage2;
    private String getPhoneNumber;
    private String message1, message2, message3, message4;
    private TextView mTextVerifyTimer;
    private ImageView mIvback;
    private Context mContext;
    private ViewCodeView mViewCodeView;
    private Bundle bundle;
    private OnVerifyCodeResultListener listener = new OnVerifyCodeResultListener() {
        @Override
        public void sendSuccess() {
            mTvMessage2.setVisibility(View.INVISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mTextVerifyTimer.setClickable(false);
                    new CountDownUtil(60000, 1000, mTextVerifyTimer).start();
                    mTextVerifyTimer.setClickable(true);
                }
            }).start();
        }

        @Override
        public void sendCodeFailure() {
            /*message4 = getString(R.string.losePasswordMessageError2);
            mTvMessage2.setVisibility(View.VISIBLE);
            SpannableStringUtil.setText(mTvMessage2, 0,
                    message4.length(), Color.parseColor("#EF1F1F"), message4);*/
        }

        @Override
        public void getCodeSuccess() {
            //mTvMessage2.setVisibility(View.INVISIBLE);

        }

        @Override
        public void getCodeFailure() {
           /* message4 = getString(R.string.losePasswordMessageError2);
            mTvMessage2.setVisibility(View.VISIBLE);
            SpannableStringUtil.setText(mTvMessage2, 0,
                    message4.length(), Color.parseColor("#EF1F1F"), message4);*/
        }


        @Override
        public void submitCodeSuccesss(String phoneNumber, String data) {
            //mTvMessage2.setVisibility(View.INVISIBLE);

        }

        @Override
        public void submitCodeFailure() {
           /* message3 = getString(R.string.losePasswordMessageError1);
            mTvMessage2.setVisibility(View.VISIBLE);
            SpannableStringUtil.setText(mTvMessage2, 0, message3.length(),
                    Color.parseColor("#EF1F1F"), message3);*/

        }
    };

    @Override
    public void initView() {
        //5.0及以上系统才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getActivity().getWindow().getDecorView();
            //两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //将状态栏设置成透明色
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //实例化控件
        mContext = getActivity();
        mIvback = findView(R.id.iv_back);
        mTvMessage = findView(R.id.tv_lossPassword_Message);
        mTvMessage1 = findView(R.id.tv_lossPassword_Message1);
        mViewCodeView = findView(R.id.view_code_view);
        mTextVerifyTimer = findView(R.id.tv_showTime);
        mTvMessage2 = findView(R.id.tv_submitCode_Error);
        //获取手机号码参数
        bundle = getArguments();
        PhoneNumber = bundle.getString("MyLosePasswordFragment1UserPhoneNumber", null);
        if (PhoneNumber != null) {
            getPhoneNumber = PhoneNumber.replace(" ", "");
        } else {
            return;
        }
        LogUtil.d("LF123", "123:" + getPhoneNumber);

        //设置监听
        mIvback.setOnClickListener(this);
        mTextVerifyTimer.setOnClickListener(this);

        //
        message1 = "我们已向" + PhoneNumber + "发送验证信息";
        message2 = getString(R.string.losePassword_Message1);
        SpannableStringUtil.setText(mTvMessage, 4, 17,
                Color.parseColor("#4169E1"), message1);
        SpannableStringUtil.setText(mTvMessage1, 9, message2.toString().length(),
                Color.parseColor("#EF1F1F"), message2);

        SendCode("86", getPhoneNumber);

        checkSumbitcode();
    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_showTime:
                SendCode("86", getPhoneNumber);
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_losepassword_2;
    }

    //请求短信验证码
    private void SendCode(String country, String number) {
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    listener.sendSuccess();
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //获取验证码成功
                        listener.getCodeSuccess();
                    } else {
                        listener.getCodeFailure();
                    }
                } else {
                    listener.sendCodeFailure();
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
        SMSSDK.getVerificationCode(country, number);
    }

    //提交验证码
    private void SubmitCode(String country, final String number, String code) {
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                if (i1 == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                        listener.submitCodeSuccesss(number, df.format(new Date()));
                    }
                } else {
                    listener.submitCodeFailure();
                }
            }
        };
        SMSSDK.submitVerificationCode(country, number, code);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private void checkSumbitcode() {

        mViewCodeView.setListener(new InputCompleterListener() {
            @Override
            public void inputComplete() {
                SubmitCode("86", getPhoneNumber, mViewCodeView.getInputContext());
            }

            @Override
            public void invalidContent() {

            }
        });

    }
}
