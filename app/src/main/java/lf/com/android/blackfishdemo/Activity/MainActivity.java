package lf.com.android.blackfishdemo.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import lf.com.android.blackfishdemo.Fragment.FinancialFragment;
import lf.com.android.blackfishdemo.Fragment.HouseKeeperFragment;
import lf.com.android.blackfishdemo.Fragment.MallFragment;
import lf.com.android.blackfishdemo.Fragment.NewHomeFragment;
import lf.com.android.blackfishdemo.Fragment.NewMineFragment;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.NavigationViewPagerAdapter;
import lf.com.android.blackfishdemo.util.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {
    private MenuItem mMenuItem;
    @BindView(R.id.vp_main_content)
    ViewPager mViewPager;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mNavigationView;

    @Override
    public int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //5.0及以上系统才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            //两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            //将状态栏设置成透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //设置缓存页面
        mViewPager.setOffscreenPageLimit(4);
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);
        mNavigationView.setItemIconTintList(null);
        mNavigationViewListener();
        mViewPagerListener();

    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {

    }

    private void mNavigationViewListener() {
        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        boolean bSelected = false;
                        switch (menuItem.getItemId()) {
                            case R.id.bottom_nav_home:
                                mViewPager.setCurrentItem(0);
                                bSelected = true;
                                break;
                            case R.id.bottom_nav_mall:
                                mViewPager.setCurrentItem(1);
                                bSelected = true;
                                break;
                            case R.id.bottom_nav_financial:
                                mViewPager.setCurrentItem(2);
                                bSelected = true;
                                break;
                            case R.id.bottom_nav_housekeeper:
                                mViewPager.setCurrentItem(3);
                                bSelected = true;
                                break;
                            case R.id.bottom_nav_mine:
                                mViewPager.setCurrentItem(4);
                                bSelected = true;
                                break;
                            default:
                                break;
                        }
                        return bSelected;
                    }
                });

    }

    private void mViewPagerListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mNavigationView.getMenu().getItem(i);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        setupViewPager(mViewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        NavigationViewPagerAdapter adapter = new NavigationViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(NewHomeFragment.newInstance());
        adapter.addFragment(MallFragment.newInstance());
        adapter.addFragment(FinancialFragment.newInstance());
        adapter.addFragment(HouseKeeperFragment.newInstance());
        adapter.addFragment(NewMineFragment.newInstance());
        viewPager.setAdapter(adapter);
    }


}
