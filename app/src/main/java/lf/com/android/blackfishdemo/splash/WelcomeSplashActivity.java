package lf.com.android.blackfishdemo.splash;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lf.com.android.blackfishdemo.Activity.LoginActivity;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.ViewPagerTransformer.DepthPageTransformer;

public class WelcomeSplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private List<View> mViewList = new ArrayList<>();
    private Button mBtn_splash;
    private ViewPagerAdapter adapter;
    private LinearLayout mlayout;
    private ImageView mIvpoint;
    private ImageView[] mIvPointArray;
    private int[] Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_splash);
        //编写布局
        initView();
        //编写指示器
        initPoint();

    }

    private void initView() {
        //实例化控件
        mViewPager = findViewById(R.id.viewpager_splash);
        mBtn_splash = findViewById(R.id.btn_splash);
        mlayout = findViewById(R.id.linear_layout_indicator);

        //添加视图
        Id = new int[]{R.layout.splash_layout_1, R.layout.splash_layout_2,
                R.layout.splash_layout_3, R.layout.splash_layout_4};
        for (int i = 0; i < Id.length; i++) {
            View view = LayoutInflater.from(this).inflate(Id[i], null);
            mViewList.add(view);
        }
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        adapter = new ViewPagerAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mBtn_splash.setOnClickListener(this);
    }

    private void initPoint() {
        mIvPointArray = new ImageView[mViewList.size()];
        for (int i = 0; i < mViewList.size(); i++) {
            mIvpoint = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            mIvpoint.setLayoutParams(params);
            params.setMargins(10, 0, 10, 0);
            mIvPointArray[i] = mIvpoint;
            if (i == 0) {
                mIvpoint.setImageResource(R.drawable.shape_circle_yellow);
            } else {
                mIvpoint.setImageResource(R.drawable.shape_circle_white);
            }

            mlayout.addView(mIvpoint);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        for (int j = 0; j < mIvPointArray.length; j++) {
            mIvPointArray[i].setImageResource(R.drawable.shape_circle_yellow);
            if (i != j) {
                mIvPointArray[j].setImageResource(R.drawable.shape_circle_white);
            }
        }

        if (i == mViewList.size() - 1) {
            mBtn_splash.setVisibility(View.VISIBLE);

        } else {
            mBtn_splash.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(WelcomeSplashActivity.this, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> lists;

        public ViewPagerAdapter(List<View> list) {
            this.lists = list;
        }

        @Override
        public int getCount() {
            if (lists != null) {
                return lists.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(lists.get(position));
            return lists.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,
                                @NonNull Object object) {
            container.removeView(lists.get(position));
        }
    }
}
