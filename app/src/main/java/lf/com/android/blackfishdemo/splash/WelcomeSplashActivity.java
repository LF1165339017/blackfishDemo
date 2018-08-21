package lf.com.android.blackfishdemo.splash;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.ViewPagerTransformer.DepthPageTransformer;
import lf.com.android.blackfishdemo.listener.OnViewListener;
import lf.com.android.blackfishdemo.util.BitmapUtil;
import lf.com.android.blackfishdemo.util.ViewUtil;

public class WelcomeSplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private List<View> mViewList;
    private int[] mImageArray;
    private Button mBtn_splash;
    private Bitmap mBitmap;
    private ViewPagerAdapter adapter;
    private LinearLayout mlayout;
    private ImageView mIvpoint;
    private ImageView[] mIvPointArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        initPoint();

    }

    private void initView() {
        //实例化控件
        mViewPager.findViewById(R.id.viewpager_splash);
        mBtn_splash.findViewById(R.id.btn_splash);
        mlayout.findViewById(R.id.linear_layout_indicator);
        //获得图片资源
        mImageArray = new int[]{R.drawable.icon_splash_1, R.drawable.icon_splash_2,
                R.drawable.icon_splash_3, R.drawable.icon_splash_4};
        //设置控件参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //循环设置ViewList
        for (int i = 0; i < mImageArray.length; i++) {
            final ImageView mImageView = new ImageView(this);
            final int ImageResourcesID = mImageArray[i];//获得当前资源ID
            mImageView.setLayoutParams(params);
            ViewUtil.getViewWidth(mImageView, new OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    mBitmap = BitmapUtil.Resource(getResources(), ImageResourcesID, width, height);
                    mImageView.setImageBitmap(mBitmap);
                    mViewList.add(mImageView);
                }
            });
        }

        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        adapter = new ViewPagerAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
    }

    private void initPoint() {
        mIvPointArray = new ImageView[mViewList.size()];
        for (int i = 0; i < mViewList.size(); i++) {
            mIvpoint = new ImageView(this);
            mIvpoint.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
            mIvpoint.setPadding(30, 0, 30, 0);
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

        if (i == mImageArray.length - 1) {
            mBtn_splash.setVisibility(View.VISIBLE);
        } else {
            mBtn_splash.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> lists;

        public ViewPagerAdapter(List<View> list) {
            lists = list;
        }

        @Override
        public int getCount() {
            return lists.size();
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
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(lists.get(position));
        }
    }
}
