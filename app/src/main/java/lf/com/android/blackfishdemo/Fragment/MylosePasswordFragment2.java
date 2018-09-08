package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import lf.com.android.blackfishdemo.util.FragmentTranscationUtil;
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
    private Bundle myLoseFragment2Bumdle = new Bundle();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mTvMessage2.setVisibility(View.INVISIBLE);
                    mTextVerifyTimer.setClickable(false);
                    new CountDownUtil(60000, 1000, mTextVerifyTimer).start();
                    mTextVerifyTimer.setClickable(true);
                    break;
                case 0x02:
                    message4 = getString(R.string.losePasswordMessageError2);
                    mTvMessage2.setVisibility(View.VISIBLE);
                    SpannableStringUtil.setText(mTvMessage2, 0,
                            message4.length(), Color.parseColor("#EF1F1F"), message4);
                    mViewCodeView.setEditContentEmpty();
                    break;
                case 0x03:
                    mTvMessage2.setVisibility(View.INVISIBLE);
                    FragmentTranscationUtil.replaceFragment(getActivity(),
                            new MylosePasswordFragment3(), myLoseFragment2Bumdle);
                    break;
                case 0x04:
                    message3 = getString(R.string.losePasswordMessageError1);
                    mTvMessage2.setVisibility(View.VISIBLE);
                    SpannableStringUtil.setText(mTvMessage2, 0, message3.length(),
                            Color.parseColor("#EF1F1F"), message3);
                    mViewCodeView.setEditContentEmpty();
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    private OnVerifyCodeResultListener listener = new OnVerifyCodeResultListener() {

        @Override
        public void getCodeSuccess() {
            LogUtil.d("LF123", "getCodeSuccess");
            LogUtil.d("LF123", "sendSuccess");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = mHandler.obtainMessage(0x01);
                    mHandler.sendMessage(msg);
                }
            }).start();
        }

        @Override
        public void getCodeFailure() {
            LogUtil.d("LF123", "getCodeFailure");
            Message msg = mHandler.obtainMessage(0x02);
            mHandler.sendMessage(msg);
        }


        @Override
        public void submitCodeSuccesss(String phoneNumber, String data) {
            LogUtil.d("LF123", "submitCodeSuccesss");
            Message msg = mHandler.obtainMessage(0x03);
            mHandler.sendMessage(msg);
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mViewCodeView.getmEditText().getWindowToken(), 0);
        }

        @Override
        public void submitCodeFailure() {
            LogUtil.d("LF123", "submitCodeFailure");
            Message msg = mHandler.obtainMessage(0x04);
            mHandler.sendMessage(msg);
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
        int code = bundle.getInt("code");

        if (code == 1) {
            PhoneNumber = bundle.getString("MyLosePasswordFragment1UserPhoneNumber", null);
            if (PhoneNumber != null) {
                getPhoneNumber = PhoneNumber.replace(" ", "");
            } else {
                return;
            }
        } else if (code == 2) {
            PhoneNumber = bundle.getString("register", null);
            if (PhoneNumber != null) {
                getPhoneNumber = PhoneNumber.replace(" ", "");
            } else {
                return;
            }
        }


        myLoseFragment2Bumdle.putString("userPhoneNumber", PhoneNumber);

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
                if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取短信验证码事件
                    //获取验证码成功
                    if (i1 == SMSSDK.RESULT_COMPLETE) {
                        listener.getCodeSuccess();
                    } else if (i1 == SMSSDK.RESULT_ERROR) {
                        listener.getCodeFailure();
                    }
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
                if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证码提交事件
                    if (i1 == SMSSDK.RESULT_COMPLETE) {
                        //回调成功
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        listener.submitCodeSuccesss(number, dateFormat.format(new Date()));
                    } else if (i1 == SMSSDK.RESULT_ERROR) {//提交验证码失败
                        listener.submitCodeFailure();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
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
            public void inputComplete(EditText view) {
                SubmitCode("86", getPhoneNumber, mViewCodeView.getInputContext());
                LogUtil.d("LF123", "" + mViewCodeView.getInputContext());
            }

            @Override
            public void invalidContent() {

            }
        });
    }
}
