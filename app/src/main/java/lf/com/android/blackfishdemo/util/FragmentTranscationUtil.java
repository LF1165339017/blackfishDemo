package lf.com.android.blackfishdemo.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import lf.com.android.blackfishdemo.R;

public class FragmentTranscationUtil {
    //获取FragmentManager，开启事务，添加碎片
    public static void replaceFragment(FragmentActivity activity, Fragment fragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_right_in, 0, 0, R.anim.activity_right_out);
        transaction.replace(R.id.userloginFrame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //获取FragmentManager，开启事务，添加碎片
    public static void replaceFragment(FragmentActivity activity, Fragment fragment, Bundle bundle) {
        FragmentManager manager = activity.getSupportFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_right_in, 0, 0, R.anim.activity_right_out);
        transaction.replace(R.id.userloginFrame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
