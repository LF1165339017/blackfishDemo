package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.BankCardsInfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;

public class AddCreditBillActivity extends BaseActivity {
    @BindView(R.id.iv_keeper_add_bill_back)
    ImageView mImageBack;
    @BindView(R.id.rl_handed)
    RelativeLayout mRlHeadedLayout;
    @BindView(R.id.rl_import_email)
    RelativeLayout mRlImportEmailLayout;
    @BindView(R.id.rl_import_net_silver)
    RelativeLayout mRlImportNetSilverLayout;
    @BindView(R.id.tv_qq_email)
    TextView mTextQQEmail;
    @BindView(R.id.tv_other_email)
    TextView mTextOtherEmail;
    @BindView(R.id.gv_bank_cards)
    GridView mGridBankCards;
    private List<BankCardsInfo> mBankCardsInfos;
    private JsonUtil mJsonUtil;

    private Context mContext;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mGridBankCards.setAdapter(new GridBankCardAdapter(mBankCardsInfos, mContext));
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public int getlayoutId() {
        return R.layout.activity_credit_bill_layout;
    }

    @Override
    public void initView() {
        mContext = AddCreditBillActivity.this;
        mImageBack.setOnClickListener(this);
        mRlHeadedLayout.setOnClickListener(this);
        mRlImportEmailLayout.setOnClickListener(this);
        mRlImportNetSilverLayout.setOnClickListener(this);
        mTextQQEmail.setOnClickListener(this);
        mTextOtherEmail.setOnClickListener(this);
    }

    @Override
    public void intitdata() {
        mBankCardsInfos = new ArrayList<>();
        mJsonUtil = new JsonUtil();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.bankCardsInfo, new OnNetResultListener() {
            @Override
            public void OnSuccessListener(String result) {
                mBankCardsInfos = mJsonUtil.getDataFromJson(result, 1);
                Message message = mHandler.obtainMessage(0x01, mBankCardsInfos);
                mHandler.sendMessage(message);
            }

            @Override
            public void OnFailureListener(String result) {

            }
        });

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_keeper_add_bill_back:
                finishActivity();
                break;
            case R.id.rl_handed:
                skipActivity(new Intent(mContext, CreateCreditBillActivity.class));
                break;
            case R.id.rl_import_email:
                Toast.makeText(this, "邮箱导入", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_import_net_silver:
                Toast.makeText(this, "网银导入", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_qq_email:
                Toast.makeText(this, "QQ邮箱", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_other_email:
                Toast.makeText(this, "其他邮箱", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}
