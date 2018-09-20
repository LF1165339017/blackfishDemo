package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;

public class SubmitOrderActivity extends BaseActivity {
    @BindView(R.id.iv_goods)
    SimpleDraweeView mIvGoods;
    @BindView(R.id.btn_submit_order)
    Button mBtnSubmitOrder;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    private Context mContext;


    @Override
    public int getlayoutId() {
        return R.layout.activity_submit_order_layout;
    }

    @Override
    public void initView() {
        mContext = SubmitOrderActivity.this;
        mIvGoods.setImageURI(UrlInfoBean.dialogImage);


    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit_order:
                skipActivity(new Intent(mContext, PayTypeActivity.class));
                break;
            default:
                break;
        }
    }
}
