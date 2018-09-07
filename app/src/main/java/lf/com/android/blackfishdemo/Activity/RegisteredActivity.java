package lf.com.android.blackfishdemo.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import lf.com.android.blackfishdemo.AlertDialog.MyAlertDialogFragment;
import lf.com.android.blackfishdemo.Fragment.MyRegisteredFragment;
import lf.com.android.blackfishdemo.Fragment.MylosePasswordFragment1;
import lf.com.android.blackfishdemo.Fragment.MylosePasswordFragment2;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnButtonClick;
import lf.com.android.blackfishdemo.listener.OnCheckReturn;
import lf.com.android.blackfishdemo.listener.Ondialoglistener;
import lf.com.android.blackfishdemo.util.LogUtil;

public class RegisteredActivity extends BaseActivity {
    private String userPhoneNumber;
    private String PhonePassword;
    private MyRegisteredFragment fragment = new MyRegisteredFragment();
    private static final String TAG = "RegisteredActivity";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public int getlayoutId() {
        return R.layout.activity_registered_layout;
    }

    @Override
    public void initView() {
<<<<<<< HEAD
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        editor.putString("phoneNumber", "187 5693 5216");
        editor.putString("password", "123456");
        editor.apply();
=======
        fragment = (MyRegisteredFragment) getSupportFragmentManager().findFragmentById(R.id.registeredFrame_layout);
        fragment.setButtonClick(new OnButtonClick() {
            @Override
            public void OnClick(View v) {
                LogUtil.d("RegisteredActivity", "回调成功");
                replaceFragment(losefragment1, bundle);
            }
        });
>>>>>>> 01461fb85d6724c508303d6f8a9c147e25a3724b

        userPhoneNumber = pref.getString("phoneNumber", null);
        PhonePassword = pref.getString("password", null);
        fragment = (MyRegisteredFragment) getSupportFragmentManager().findFragmentById(R.id.registeredFrame_layout);
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

<<<<<<< HEAD
        fragment.updateView(userPhoneNumber, PhonePassword);

=======

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        editor.putString("phoneNumber", "187 5693 5216");
        editor.putString("password", "123456");
        editor.apply();

        userPhoneNumber = pref.getString("phoneNumber", null);
        PhonePassword = pref.getString("password", null);
        bundle.putString("PhoneNumber", userPhoneNumber);
        bundle.putString("Password", PhonePassword);
        bundle.putString("MyRegisteredFragmentPhoneNumber", fragment.getEdUserPhoneNumber());
        //bundle.putString("MyLosePasswordFragment1UserPhoneNumber", losefragment1.getEduserPhone());
        fragment.setArguments(bundle);
        fragment.updateView();
>>>>>>> 01461fb85d6724c508303d6f8a9c147e25a3724b
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

}
