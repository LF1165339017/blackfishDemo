package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.ShowImageAdapter;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnImageClickListener;

public class ShowImageActivity extends BaseActivity {
    @BindView(R.id.tv_indicator)
    TextView mTvIndicator;
    @BindView(R.id.vp_image)
    ViewPager mVpImage;
    @BindView(R.id.rl_show_image)
    RelativeLayout relativeLayout;
    private Context mContext;

    @Override
    public int getlayoutId() {
        return R.layout.activity_show_image_layout;
    }

    @Override
    public void initView() {
        mContext = ShowImageActivity.this;
        ShowImageAdapter showImageAdapter = new ShowImageAdapter(mContext, UrlInfoBean.bigImageUrls);
        mVpImage.setAdapter(showImageAdapter);
        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                String text = i + 1 + "/" + UrlInfoBean.bigImageUrls.length;
                mTvIndicator.setText(text);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mVpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        showImageAdapter.setmOnImageClickListener(new OnImageClickListener() {
            @Override
            public void onImageClick() {
                finishActivity();
            }
        });

        relativeLayout.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_show_image:
                finishActivity();
                break;
            default:
                break;
        }
    }

}
