package lf.com.android.blackfishdemo.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.bean.GoodsDetailsInfo;
import lf.com.android.blackfishdemo.bean.OptionalTypeInfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;
import lf.com.android.blackfishdemo.view.GridViewForScroll;
import lf.com.android.blackfishdemo.view.RecyclerViewBanner;

public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.rvb_banner)
    RecyclerViewBanner mRvBanner;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_single_price)
    TextView mTvSinglePrice;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_choose_type)
    TextView mTvChooseType;
    @BindView(R.id.iv_more_type)
    ImageView mIvMoreType;
    @BindView(R.id.tv_choose_address)
    TextView mTvChooseAddress;
    @BindView(R.id.iv_more_address)
    ImageView mIvMoreAddress;
    @BindView(R.id.gv_similar_reco)
    GridViewForScroll mGvSimilarReco;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.rl_dialog)
    RelativeLayout mRIDialog;
    @BindView(R.id.ll_period_info)
    LinearLayout mLinearPeriodInfo;
    @BindView(R.id.tv_fav)
    TextView mTextFav;
    @BindView(R.id.tv_imm_pay)
    TextView mTextImmPay;

    private Context context;
    private boolean isFav = false;
    private BottomSheetDialog mDialog;
    private List<GoodsDetailsInfo> mGoodsDetailsInfos;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    public int getlayoutId() {
        return R.layout.activity_goods_detail_layout;
    }

    @Override
    public void initView() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(
                        GoodsDetailActivity.this, "TabLayout" + tab.getPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void intitdata() {
        mGoodsDetailsInfos = new ArrayList<>();
        final JsonUtil jsonUtil = new JsonUtil();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.goodsDetailsUrl, new OnNetResultListener() {
            @Override
            public void OnSuccessListener(String result) {
                mGoodsDetailsInfos = jsonUtil.getDataFromJson(result, 4);
                Message message = mHandler.obtainMessage(0x01, mGoodsDetailsInfos);
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
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                Toast toast = ToastUtil.setMyToast(
                        this, ToastUtil.PROMPT, "更多", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.iv_more_type:
                break;
            case R.id.iv_more_address:
                Toast toast1 = ToastUtil.setMyToast(
                        this, ToastUtil.PROMPT, "选择配送地址", Toast.LENGTH_SHORT);
                toast1.show();
                break;
            case R.id.rl_dialog:
                showServiceDialog();
                break;
            case R.id.ll_period_info:
                Toast toast2 = ToastUtil.setMyToast(
                        this, ToastUtil.PROMPT, "月供详情", Toast.LENGTH_SHORT);
                toast2.show();
                break;
            case R.id.tv_fav:
                break;
            case R.id.tv_imm_pay:
                mDialog.show();
                break;
            default:
                break;
        }
    }

    private void showServiceDialog() {
        final Dialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_service_info_layout, null);
        view.findViewById(R.id.iv_close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void setDialogData(List<OptionalTypeInfo> typeInfos) {
        mDialog = new BottomSheetDialog(this);

    }
}
