package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import lf.com.android.blackfishdemo.Activity.LoginActivity;
import lf.com.android.blackfishdemo.Activity.RegisteredActivity;
import lf.com.android.blackfishdemo.Activity.UserLoginActivity;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnBackPress;
import lf.com.android.blackfishdemo.util.CheckPhonePassword;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class MylosePasswordFragment3 extends BaseFragment implements OnBackPress {
    private EditText medtTxt_PhonePassword;
    private ImageView mimgView_icon_clean, mimgView_icon_showType, mimgView_icon_back;
    private Button mbtn_submission;
    private int eyeType;//记录点击是否显示密码图标的次数
    private String medtTxtString;
    private boolean isInput;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01://通过点击次数判断是否显示密码，此时显示
                    mimgView_icon_showType.setImageResource(R.drawable.user_icon_eye_open_blue);
                    medtTxt_PhonePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    break;
                case 0x02://通过点击次数判断是否显示密码，此时不显示
                    mimgView_icon_showType.setImageResource(R.drawable.user_icon_eye_close_blue);
                    medtTxt_PhonePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        //实例化控件
        mimgView_icon_back = findView(R.id.icon_left);
        mbtn_submission = findView(R.id.btn_lose3_submission);
        medtTxt_PhonePassword = findView(R.id.Ed_ChangePhonePassword);
        mimgView_icon_clean = findView(R.id.imgView_icon_clean);
        mimgView_icon_showType = findView(R.id.imgView_icon_showType);
        //设置监听事件
        mimgView_icon_back.setOnClickListener(this);
        mbtn_submission.setOnClickListener(this);
        mimgView_icon_showType.setOnClickListener(this);
        mimgView_icon_clean.setOnClickListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        setEditTxtlistener();
    }

    @Override
    public void initdata() {

    }

    @Override
    public void lisetener(View view) {
        switch (view.getId()) {
            case R.id.icon_left:
                getFragmentManager().popBackStack(null, 1);
                break;
            case R.id.imgView_icon_clean:
                medtTxt_PhonePassword.setText("");
            case R.id.imgView_icon_showType:
                enregisterClick();
                break;
            case R.id.btn_lose3_submission:
                checkbtn();
                break;
            default:
                break;
        }
    }

    private void checkbtn() {
        Toast toast1 = ToastUtil.setMyToast(getActivity(),
                ToastUtil.PROMPT, "密码格式不正确，请检查", Toast.LENGTH_SHORT);

        Toast toast2 = ToastUtil.setMyToast(getActivity(),
                ToastUtil.PROMPT, "密码格式长度，请检查", Toast.LENGTH_SHORT);
        if (medtTxtString.length() < 21 && medtTxtString.length() > 5) {
            isInput = CheckPhonePassword.isPhonePassword(medtTxtString);
            if (isInput) {
                editor.putString("password", medtTxtString.toString());
                editor.apply();
                Intent intent = new Intent(getContext(), UserLoginActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                toast1.show();
            }
        } else {
            toast2.show();
        }

    }


    private void enregisterClick() {
        if (eyeType % 2 == 0) {
            Message message = mHandler.obtainMessage(0x01);
            mHandler.sendMessage(message);
        } else {
            Message message = mHandler.obtainMessage(0x02);
            mHandler.sendMessage(message);
        }
        eyeType++;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_losepassword_3;
    }

    private void setEditTxtlistener() {
        medtTxt_PhonePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    medtTxt_PhonePassword.setText(str1);
                    medtTxt_PhonePassword.setSelection(start);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                medtTxtString = s.toString();
                if (s.toString().length() == 0) {
                    mimgView_icon_clean.setVisibility(View.INVISIBLE);
                } else {
                    mimgView_icon_clean.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    //对虚拟返回键的监听
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisteredActivity) {
            ((RegisteredActivity) context).setInterception(true);
            ((RegisteredActivity) context).setOnBackPress(this);
        }
        if (getActivity() instanceof UserLoginActivity) {
            ((UserLoginActivity) getActivity()).setInterception(true);
            ((UserLoginActivity) getActivity()).setOnBackPress(this);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof RegisteredActivity) {
            ((RegisteredActivity) getActivity()).setInterception(false);
            ((RegisteredActivity) getActivity()).setOnBackPress(null);
        }
        if (getActivity() instanceof UserLoginActivity) {
            ((UserLoginActivity) getActivity()).setInterception(false);
            ((UserLoginActivity) getActivity()).setOnBackPress(null);
        }
    }

    @Override
    public void onBackPress() {
        getFragmentManager().popBackStack(null, 1);
    }


}
