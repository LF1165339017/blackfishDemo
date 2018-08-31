package lf.com.android.blackfishdemo.Activity;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import lf.com.android.blackfishdemo.AlertDialog.MyAlertDialogFragment;
import lf.com.android.blackfishdemo.Fragment.MyRegisteredFragment;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.listener.OnButtonClick;
import lf.com.android.blackfishdemo.listener.OnCheckReturn;
import lf.com.android.blackfishdemo.listener.Ondialoglistener;

public class RegisteredActivity extends BaseActivity {
    private MyRegisteredFragment fragment = new MyRegisteredFragment();

    @Override
    public int getlayoutId() {
        return R.layout.activity_registered_layout;
    }

    @Override
    public void initView() {
        fragment.setButtonClick(new OnButtonClick() {
            @Override
            public void OnClick(View v) {

            }
        });

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

    //获取FragmentManager，开启事务，添加碎片
    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_right_in, 0);
        transaction.replace(R.id.registeredFrame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
