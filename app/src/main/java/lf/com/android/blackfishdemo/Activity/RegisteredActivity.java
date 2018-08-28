package lf.com.android.blackfishdemo.Activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import lf.com.android.blackfishdemo.Fragment.MyRegisteredFragment;
import lf.com.android.blackfishdemo.R;

public class RegisteredActivity extends BaseActivity {


    @Override
    public int getlayoutId() {
        return R.layout.activity_registered_layout;
    }

    @Override
    public void initView() {
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {

    }

    //获取FragmentManager，开启事务，添加碎片
    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.registeredFrame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, R.anim.activity_bottom_out);
    }
}
