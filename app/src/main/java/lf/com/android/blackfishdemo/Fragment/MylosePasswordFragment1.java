package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnCheckReturn;
import lf.com.android.blackfishdemo.util.FragmentTranscationUtil;
import lf.com.android.blackfishdemo.util.LogUtil;
import lf.com.android.blackfishdemo.util.PhoneUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class MylosePasswordFragment1 extends BaseFragment {
    private Context mContext;
    private EditText mUserPhoneEdittext;
    private ImageView mIv_clean, mIv_icon_left;
    private Button mbtn;
    private LinearLayout mLinlayout;
    private boolean isUserPhoneCheck;
    private String userPhoneNumber, bundlePhoneNumber;
    private Bundle bundle, mLosePassword1Bundle;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mEduserPhoneGetFocus();
                    break;
                default:
                    break;
            }
            return false;
        }
    });


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
        mContext = getActivity();
        //实例化控件
        mUserPhoneEdittext = findView(R.id.Ed_uerPhoneNumber);
        mLinlayout = findView(R.id.linear_userPhone_line);
        mIv_clean = findView(R.id.icon_PhoneNumber_clean);
        mbtn = findView(R.id.btn_losepassword_next);
        mIv_icon_left = findView(R.id.icon_left);
        //设置监听
        mLinlayout.setOnClickListener(this);
        mIv_clean.setOnClickListener(this);
        mbtn.setOnClickListener(this);
        mIv_icon_left.setOnClickListener(this);
        //对EditText进行监听
        EdittextListener();
        //获取传入的参数，更新
        updateView();

    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.linear_userPhone_line:
                Message message = mHandler.obtainMessage(0x01);
                mHandler.sendMessage(message);
                break;
            case R.id.icon_PhoneNumber_clean:
                mUserPhoneEdittext.setText("");
                break;
            case R.id.btn_losepassword_next:
                btnCheck();
                break;
            case R.id.icon_left:
                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_losepassword_1;
    }

    private void EdittextListener() {

        //账号输入框事件监听
        mUserPhoneEdittext.addTextChangedListener(new TextWatcher() {
            //这个方法被调用，说明在S字符串中，从start位置开始的count个字符即将被长度为after的新文本
            //所取代，在这个方法里面改变s,会报错.
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //这个方法被调用，说明在s字符串中，从start位置开始的count个字符取代了长度为before的旧文本，
            //在这个方法里改变s,会报错。
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                StringBuffer stringBuffer = new StringBuffer(s);
                if (before > 0) {
                    if (stringBuffer.length() == 4 || stringBuffer.length() == 9) {
                        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                        mUserPhoneEdittext.setText(stringBuffer.toString());
                        mUserPhoneEdittext.setSelection(stringBuffer.length());
                    }
                }
            }

            //这个方法被调用，说明s字符串位置已经改变
            @Override
            public void afterTextChanged(Editable s) {
                /**
                 * 判断是否显示删除图标
                 */
                if (s.toString().length() == 0) {
                    mIv_clean.setVisibility(View.INVISIBLE);
                } else {
                    mIv_clean.setVisibility(View.VISIBLE);
                }

                StringBuffer stringBuffer = new StringBuffer(s);
                if (s.length() >= 4) {
                    char[] chars = s.toString().toCharArray();
                    if (chars[3] != ' ') {
                        stringBuffer.insert(3, ' ');
                        mUserPhoneEdittext.setText(stringBuffer.toString());
                        mUserPhoneEdittext.setSelection(stringBuffer.length());
                    }
                }
                if (s.length() >= 9) {
                    char[] chars = s.toString().toCharArray();
                    if (chars[8] != ' ') {
                        stringBuffer.insert(8, ' ');
                        mUserPhoneEdittext.setText(stringBuffer.toString());
                        mUserPhoneEdittext.setSelection(stringBuffer.length());
                    }
                }

                if (s.length() == 13) {//判断手机号码是否正确
                    isUserPhoneCheck = true;
                } else {
                    isUserPhoneCheck = false;
                }

            }
        });

    }

    private void mEduserPhoneGetFocus() {
        mUserPhoneEdittext.setFocusable(true);
        mUserPhoneEdittext.setFocusableInTouchMode(true);
        mUserPhoneEdittext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mUserPhoneEdittext, 0);
    }

    private void btnCheck() {
        userPhoneNumber = mUserPhoneEdittext.getText().toString();
        String number = userPhoneNumber.replace(" ", "");
        Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT,
                "手机号码格式不对", Toast.LENGTH_SHORT);
        Toast toast1 = ToastUtil.setMyToast(mContext, ToastUtil.WARING,
                "这不是手机号码", Toast.LENGTH_SHORT);
        Toast toast2 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT,
                "用户不存在", Toast.LENGTH_SHORT);
        if (isUserPhoneCheck) {//判断手机号是否有13位
            if (PhoneUtil.isPhone(number)) {//判断是否是手机号
                if (userPhoneNumber.equals(bundlePhoneNumber)) {//判断账户是否存在
                    jumpNewFragment();
                } else {
                    toast2.show();
                }
            } else {
                toast1.show();
            }
        } else {
            toast.show();
        }
    }

    private void jumpNewFragment() {
        mLosePassword1Bundle = new Bundle();
        mLosePassword1Bundle.putString("MyLosePasswordFragment1UserPhoneNumber", userPhoneNumber);
        mLosePassword1Bundle.putInt("code", 1);
        FragmentTranscationUtil.replaceFragment(getActivity(), new MylosePasswordFragment2(), mLosePassword1Bundle);
    }


    private void updateView() {
        bundle = getArguments();
        bundlePhoneNumber = bundle.getString("MyRegisteredFragmentPhoneNumber", null);
        if (bundlePhoneNumber != null) {
            mUserPhoneEdittext.setText(bundlePhoneNumber);
            mUserPhoneEdittext.setSelection(bundlePhoneNumber.length());
        }
    }

}
