package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.util.ToastUtil;

public class AddBillActivity extends BaseActivity {
    private Context mContext;
    @BindView(R.id.iv_keeper_add_bill_back)
    ImageView mImageBack;
    @BindView(R.id.rl_add_bank_bill)
    RelativeLayout mAddBankCardBillLayout;
    @BindView(R.id.rl_add_wd_bill)
    RelativeLayout mAddWdLayout;
    @BindView(R.id.rl_add_zfb_bill)
    RelativeLayout mAddZfbBillLayout;
    @BindView(R.id.rl_add_gjj_bill)
    RelativeLayout mAddGjjBillLayout;

    @Override
    public int getlayoutId() {
        return R.layout.activity_add_bill_layout;

    }

    @Override
    public void initView() {
        mContext = AddBillActivity.this;
        mImageBack.setOnClickListener(this);
        mAddBankCardBillLayout.setOnClickListener(this);
        mAddZfbBillLayout.setOnClickListener(this);
        mAddGjjBillLayout.setOnClickListener(this);
    }

    @Override
    public void intitdata() {

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_add_bill_back:
                finish();
                break;
            case R.id.rl_add_bank_bill:
                skipActivity(new Intent(this, AddCreditBillActivity.class));
                break;
            case R.id.rl_add_wd_bill:
                Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "网贷账单", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.rl_add_zfb_bill:
                Toast toast1 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "支付宝", Toast.LENGTH_SHORT);
                toast1.show();
                break;
            case R.id.rl_add_gjj_bill:
                Toast toast2 = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "公积金", Toast.LENGTH_SHORT);
                toast2.show();
                break;
            default:
                break;
        }
    }
}
