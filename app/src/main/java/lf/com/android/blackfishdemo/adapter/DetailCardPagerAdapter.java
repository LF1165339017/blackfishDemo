package lf.com.android.blackfishdemo.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lf.com.android.blackfishdemo.Fragment.BillFragment;
import lf.com.android.blackfishdemo.Fragment.PayHistoryFragment;

public class DetailCardPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"账单", "还款记录"};

    public DetailCardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return BillFragment.newInstance();
        } else if (i == 1) {
            return PayHistoryFragment.newInstance();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
