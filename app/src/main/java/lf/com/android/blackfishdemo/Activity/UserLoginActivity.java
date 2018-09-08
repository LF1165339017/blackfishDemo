package lf.com.android.blackfishdemo.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;

import lf.com.android.blackfishdemo.AlertDialog.MyAlertDialogFragment;
import lf.com.android.blackfishdemo.Fragment.UserLoginFragment;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnBackPress;
import lf.com.android.blackfishdemo.listener.OnCheckReturn;
import lf.com.android.blackfishdemo.listener.Ondialoglistener;

public class UserLoginActivity extends BaseActivity {
    private String userPhoneNumber;
    private String PhonePassword;
    private UserLoginFragment fragment = new UserLoginFragment();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private OnBackPress onBackPress;
    private boolean isInterception = false;

    @Override
    public int getlayoutId() {
        return R.layout.activity_user_login;
    }

    @Override
    public void initView() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        editor.putString("phoneNumber", "187 5693 5216");
        editor.putString("password", "123456");
        editor.apply();
        fragment = (UserLoginFragment) getSupportFragmentManager().findFragmentById(R.id.userloginFrame_layout);
        userPhoneNumber = pref.getString("phoneNumber", null);
        PhonePassword = pref.getString("password", null);
        fragment.setCheckReturn(new OnCheckReturn() {
            @Override
            public void onCheckResultReturn() {
                MyAlertDialogFragment fragment = new MyAlertDialogFragment();
                fragment.setOnLosePassword(new Ondialoglistener() {
                    @Override
                    public void OnClick(View view) {

                    }
                });
                fragment.show(getSupportFragmentManager(), "MyAlertDialogFragment");
            }
        });
        fragment.updateView(userPhoneNumber, PhonePassword);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.activity_bottom_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (isInterception()) {
                if (onBackPress != null) {
                    onBackPress.onBackPress();
                    return false;
                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public void setOnBackPress(OnBackPress onBackPress) {
        this.onBackPress = onBackPress;
    }

    public void setInterception(boolean interception) {
        isInterception = interception;
    }

    public boolean isInterception() {
        return isInterception;
    }
}
