package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.SelectBankCardAdapter;
import lf.com.android.blackfishdemo.bean.BankCardsInfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnItemLayoutClickListener;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;

public class SelectCardActivity extends BaseActivity {
    @BindView(R.id.tv_select_card_back)
    ImageView mImageBack;
    @BindView(R.id.rv_select_card_list)
    RecyclerView mRecyclerView;
    private Context mContext;
    private List<BankCardsInfo> mCardsInfosList;
    private JsonUtil jsonUtil;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    SelectBankCardAdapter adapter = new SelectBankCardAdapter(mContext, mCardsInfosList);
                    adapter.setmClickListener(new OnItemLayoutClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String cardName = mCardsInfosList.get(position).getName();
                            Intent intent = new Intent();
                            intent.putExtra("cardName", cardName);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public int getlayoutId() {
        return R.layout.activity_select_card_layout;
    }

    @Override
    public void initView() {
        mContext = SelectCardActivity.this;
        mCardsInfosList = new ArrayList<>();
        mImageBack.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    public void intitdata() {
        jsonUtil = new JsonUtil();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.bankCardsInfo, new OnNetResultListener() {
            @Override
            public void OnSuccessListener(String result) {
                mCardsInfosList = jsonUtil.getDataFromJson(result, 1);
                Message message = mHandler.obtainMessage(0x01, mCardsInfosList);
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
            case R.id.tv_select_card_back:
                finish();
                break;
            default:
                break;
        }
    }
}
