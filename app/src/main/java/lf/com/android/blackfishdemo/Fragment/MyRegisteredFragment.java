package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnButtonClick;

public class MyRegisteredFragment extends BaseFragment {
    private Context mContext;
    private OnButtonClick buttonClick;
    private RelativeLayout muserpasswordRelayout;
    private LinearLayout mUserPhonelayout, mUserPasswordlayout;
    private EditText muserPNEdtext, muserPAPEdtext;
    private ImageView mIv_PhoneNumber_clean, mIv_Password_clean,
            mIv_eyes_type, mIv_user_qq, mIv_user_weChat, mIv_user_phone, mIv_user_password;
    private TextView mTv_Choose_type, mTv_lose_password;
    private View mView_phonebg_line, mView_password_line;
    private Button mLogin_btn;
    private boolean isUserInput;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mView_phonebg_line.setBackgroundResource(R.color.colorGoldFFC125);
                    mView_password_line.setBackgroundResource(R.color.colorGrayE8E8E8);
                    break;
                case 0x02:
                    mView_phonebg_line.setBackgroundResource(R.color.colorGrayE8E8E8);
                    mView_password_line.setBackgroundResource(R.color.colorGoldFFC125);
                    break;
                case 0x03:
                    mEduserPhoneGetFocus();
                    break;
                case 0x04:
                    mEduserPasswordGetFocus();
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
        mTv_Choose_type = findView(R.id.Tv_registered_msg1);
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
        //对输入框监听，设置格式化3-4-4手机号码模式
        edittextlistener();
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
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_registered_layout;
    }

    public void setButtonClick(OnButtonClick buttonClick) {
        this.buttonClick = buttonClick;
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

                //将光标移动到末尾
                muserPNEdtext.setSelection(muserPNEdtext.getText().toString().length());
                //处理
                if (s.toString().length() == 0) {
                    mIv_Password_clean.setVisibility(View.INVISIBLE);
                } else {
                    mIv_Password_clean.setVisibility(View.VISIBLE);
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

}
