package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnButtonClick;
import lf.com.android.blackfishdemo.listener.OnCheckReturn;
import lf.com.android.blackfishdemo.util.LogUtil;
import lf.com.android.blackfishdemo.util.PhoneUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class MyRegisteredFragment extends BaseFragment {
    private Context mContext;
    private OnButtonClick buttonClick;
    private OnCheckReturn aReturn;
    private RelativeLayout muserpasswordRelayout;
    private LinearLayout mUserPhonelayout, mUserPasswordlayout;
    private EditText muserPNEdtext, muserPAPEdtext;
    private ImageView mIv_PhoneNumber_clean, mIv_Password_clean,
            mIv_eyes_type, mIv_user_qq, mIv_user_weChat;
    private TextView mTv_Choose_type, mTv_lose_password;
    private View mView_phonebg_line, mView_password_line;
    private Button mLogin_btn;
    private boolean isUserPhoneCheck = false;//判断输入框格式是否正确 false为默认不正确
    private boolean isViewRegisteredType = false;//true 为登录界面 false为注册界面
    private boolean isPasswordinit = false;//判断输入框是否输入密码;
    private int mEyesType = 0;//设置点击计数器，判断当前密码状态
    private int layoutChange = 0;//设置点击计数器，判断当前布局状态
    private Bundle bundle = getArguments();
    private String userPhoneNumber;
    private String PhonePassword;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01://获取输入框焦点时，让下划线背景颜色改变
                    mView_phonebg_line.setBackgroundResource(R.color.colorGoldFFC125);
                    mView_password_line.setBackgroundResource(R.color.colorGrayE8E8E8);
                    break;
                case 0x02://获取密码框焦点时，让下划线背景颜色改变
                    mView_phonebg_line.setBackgroundResource(R.color.colorGrayE8E8E8);
                    mView_password_line.setBackgroundResource(R.color.colorGoldFFC125);
                    break;
                case 0x03:
                    mEduserPhoneGetFocus();//点击手机图标也能让输入框获取到焦点
                    break;
                case 0x04:
                    mEduserPasswordGetFocus();//点击密码图标也能让输入框获取到焦点
                    break;
                case 0x05://通过点击次数判断是否显示密码，此时不显示
                    mIv_eyes_type.setImageResource(R.drawable.user_icon_eye_close_blue);
                    muserPAPEdtext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    break;
                case 0x06://通过点击次数判断是否显示密码，此时显示
                    mIv_eyes_type.setImageResource(R.drawable.user_icon_eye_open_blue);
                    muserPAPEdtext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    break;
                case 0x07://通过点击次数判断是否改变格局，此时不改变
                    muserpasswordRelayout.setVisibility(View.VISIBLE);
                    isViewRegisteredType = true;//是登录界面
                    mTv_Choose_type.setText(R.string.registered_msg2);
                    mLogin_btn.setText(R.string.btn_registered_msg2);
                    mLogin_btn.setBackground(getResources().getDrawable(R.drawable.shape_btn_light_yellow_bg));
                    break;
                case 0x08://通过点击次数判断是否改变格局，此时改变
                    muserpasswordRelayout.setVisibility(View.GONE);
                    isViewRegisteredType = false;//是注册界面
                    mTv_Choose_type.setText(R.string.registered_msg1);
                    mLogin_btn.setText(R.string.btn_registered_msg1);
                    mLogin_btn.setBackground(getResources().getDrawable(R.drawable.shape_btn_khakil_bg));
                    break;
                default:
                    break;
            }

            return false;
        }
    });

    @Override
    public void initView() {
        mContext = getActivity();
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
        mUserPhonelayout = findView(R.id.linear_userPhone_line);
        mUserPasswordlayout = findView(R.id.linear_userPassword_line);
        muserpasswordRelayout = findView(R.id.Relayout_PhonePassword);
        muserPNEdtext = findView(R.id.Ed_uerPhoneNumber);
        muserPAPEdtext = findView(R.id.Ed_uerPassword);
        mIv_PhoneNumber_clean = findView(R.id.icon_PhoneNumber_clean);
        mIv_Password_clean = findView(R.id.icon_Password_clean);
        mIv_eyes_type = findView(R.id.icon_eye_type);
        mIv_user_qq = findView(R.id.user_icon_qq);
        mIv_user_weChat = findView(R.id.user_icon_wechat_login);
        mTv_Choose_type = findView(R.id.Tv_registered_msg);
        mTv_lose_password = findView(R.id.tv_user_losePassword);
        mView_phonebg_line = findView(R.id.view_phoneNumber_line);
        mView_password_line = findView(R.id.view_phonePassword_line);
        mLogin_btn = findView(R.id.btn_registered_login);
        //设置监听
        mUserPhonelayout.setOnClickListener(this);
        mUserPasswordlayout.setOnClickListener(this);
        mIv_PhoneNumber_clean.setOnClickListener(this);
        mIv_Password_clean.setOnClickListener(this);
        mIv_eyes_type.setOnClickListener(this);
        mTv_Choose_type.setOnClickListener(this);
        mTv_lose_password.setOnClickListener(this);
        mLogin_btn.setOnClickListener(this);
        mIv_user_qq.setOnClickListener(this);
        mIv_user_weChat.setOnClickListener(this);
        //对输入框监听，设置格式化3-4-4手机号码模式
        edittextlistener();

        userPhoneNumber = bundle.getString("PhoneNumber");
        PhonePassword = bundle.getString("Password");
        muserPNEdtext.setText(userPhoneNumber);
        muserPNEdtext.setSelection(userPhoneNumber.length());
    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.linear_userPhone_line://获取点击事件，设置用户账号输入弹出软件盘
                Message message = mHandler.obtainMessage(0x03);
                mHandler.sendMessage(message);
                break;
            case R.id.linear_userPassword_line://获取点击事件，设置用户密码输入弹出软件盘
                Message msg = mHandler.obtainMessage(0x04);
                mHandler.sendMessage(msg);
                break;
            case R.id.icon_PhoneNumber_clean://获取点击事件，清除用户账号数据
                muserPNEdtext.setText("");
                break;
            case R.id.icon_Password_clean://获取点击事件，清除用户密码数据
                muserPAPEdtext.setText("");
                break;
            case R.id.icon_eye_type://获取点击事件，判断是否显示密码
                TypeChang();
                break;
            case R.id.Tv_registered_msg://获取点击事件，改变布局
                LayoutChange();
                break;
            case R.id.tv_user_losePassword://忘记密码的操作
                buttonClick.OnClick(mTv_lose_password);
                break;
            case R.id.user_icon_qq:
                break;
            case R.id.user_icon_wechat_login:
                break;
            case R.id.btn_registered_login:
                checklogin();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_registered_layout;
    }

    private void checklogin() {//登录时对手机号码的判断
        String number = muserPNEdtext.getText().toString();
        String phonePassword = muserPAPEdtext.getText().toString();
        String phonenumber = number.replace(" ", "");
        String password = muserPAPEdtext.toString();
        Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT,
                "手机号码格式不对", Toast.LENGTH_SHORT);
        Toast toast1 = ToastUtil.setMyToast(mContext, ToastUtil.WARING,
                "这不是手机号码", Toast.LENGTH_SHORT);
        Toast toast2 = ToastUtil.setMyToast(mContext, ToastUtil.ERROR,
                "请输入密码，长度在6-20之间", Toast.LENGTH_SHORT);
        Toast toast3 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT,
                "用户不存在", Toast.LENGTH_SHORT);

        if (isViewRegisteredType) {//判断是什么界面，true为登录界面，false为注册界面
            if (isUserPhoneCheck) {//判断手机号是否是13位
                if (PhoneUtil.isPhone(phonenumber)) {//判断手机号是否是手机号
                    if (isPasswordinit) {//判断密码是否输入且位数是否在6-20之间
                        if (number.equals(userPhoneNumber)) {//判断账户是否存在
                            if (phonePassword.equals(PhonePassword)) {
                                Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                            } else {
                                aReturn.onCheckResultReturn();
                            }
                        } else {
                            toast3.show();
                        }
                    } else {
                        toast2.show();
                    }
                } else {
                    toast1.show();
                }
            } else {
                toast.show();
            }

        } else {
            if (isUserPhoneCheck) {//判断手机号是否有13位
                if (PhoneUtil.isPhone(phonenumber)) {
                    if (number.equals(userPhoneNumber)) {//判断账户是否存在
                        ToastUtil.setToastNormal(mContext, "下一步", Toast.LENGTH_SHORT);
                    } else {
                        toast3.show();
                    }
                } else {
                    toast1.show();
                }
            } else {
                toast.show();
            }
        }

    }

    public void setButtonClick(OnButtonClick buttonClick) {//点击忘记密码时回调的函数
        this.buttonClick = buttonClick;
    }

    public void setCheckReturn(OnCheckReturn aReturn) {//点击Dialog忘记密码时回调的函数
        this.aReturn = aReturn;
    }

    /**
     * 获取密码框焦点
     */
    private void mEduserPasswordGetFocus() {
        muserPAPEdtext.setFocusable(true);
        muserPAPEdtext.setFocusableInTouchMode(true);
        muserPAPEdtext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(muserPAPEdtext, 0);

    }

    /**
     * 获取输入框焦点
     */
    private void mEduserPhoneGetFocus() {
        muserPNEdtext.setFocusable(true);
        muserPNEdtext.setFocusableInTouchMode(true);
        muserPNEdtext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(muserPNEdtext, 0);
    }

    private void edittextlistener() {

        //账号输入框事件监听
        muserPNEdtext.addTextChangedListener(new TextWatcher() {
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
                        muserPNEdtext.setText(stringBuffer.toString());
                        muserPNEdtext.setSelection(stringBuffer.length());
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
                    mIv_PhoneNumber_clean.setVisibility(View.INVISIBLE);
                } else {
                    mIv_PhoneNumber_clean.setVisibility(View.VISIBLE);
                }

                StringBuffer stringBuffer = new StringBuffer(s);
                if (s.length() >= 4) {
                    char[] chars = s.toString().toCharArray();
                    if (chars[3] != ' ') {
                        stringBuffer.insert(3, ' ');
                        muserPNEdtext.setText(stringBuffer.toString());
                        muserPNEdtext.setSelection(stringBuffer.length());
                    }
                }
                if (s.length() >= 9) {
                    char[] chars = s.toString().toCharArray();
                    if (chars[8] != ' ') {
                        stringBuffer.insert(8, ' ');
                        muserPNEdtext.setText(stringBuffer.toString());
                        muserPNEdtext.setSelection(stringBuffer.length());
                    }
                }

                if (s.length() == 13) {//判断手机号码是否正确
                    isUserPhoneCheck = true;
                } else {
                    isUserPhoneCheck = false;
                }

            }
        });
        /**
         * 当输入框获取焦点时，让下划线变色
         */
        muserPNEdtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Message message = mHandler.obtainMessage(0x01);
                    mHandler.sendMessage(message);
                }
            }
        });
        //密码输入框事件监听
        muserPAPEdtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    mIv_Password_clean.setVisibility(View.INVISIBLE);
                } else {
                    mIv_Password_clean.setVisibility(View.VISIBLE);
                }
                muserPAPEdtext.setSelection(s.length());

                if (s.toString().length() != 0 && s.toString().length() > 5 && s.toString().length() < 21) {
                    isPasswordinit = true;
                } else {
                    isPasswordinit = false;
                }
            }
        });
        /**
         * 当输入框获取焦点时，让下划线变色
         */
        muserPAPEdtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Message message = mHandler.obtainMessage(0x02);
                    mHandler.sendMessage(message);
                }
            }
        });

    }

    /**
     * 对密码框是否显示密码的控制
     */
    private void TypeChang() {
        if (mEyesType % 2 == 0) {
            Message message = mHandler.obtainMessage(0x05);
            mHandler.sendMessage(message);
        } else {
            Message message = mHandler.obtainMessage(0x06);
            mHandler.sendMessage(message);
        }
        mEyesType++;
    }

    /**
     * 通过点击判断布局格式
     */
    private void LayoutChange() {
        if (layoutChange % 2 == 0) {
            Message message = mHandler.obtainMessage(0x07);
            mHandler.sendMessage(message);
        } else {
            Message message = mHandler.obtainMessage(0x08);
            mHandler.sendMessage(message);
        }
        layoutChange++;
    }

    public String getEdUserPhoneNumber() {
        return muserPNEdtext.getText().toString();
    }

}
