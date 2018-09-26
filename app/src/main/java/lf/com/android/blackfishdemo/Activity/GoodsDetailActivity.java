package lf.com.android.blackfishdemo.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.SimilarRecoAdapter;
import lf.com.android.blackfishdemo.bean.GoodsDetailsInfo;
import lf.com.android.blackfishdemo.bean.OptionalTypeInfo;
import lf.com.android.blackfishdemo.bean.SimilarRecoInfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.listener.OnNumChangeListener;
import lf.com.android.blackfishdemo.listener.OnRvBannerClickListener;
import lf.com.android.blackfishdemo.listener.OnSwitchRvBannerListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;
import lf.com.android.blackfishdemo.util.SpannableStringUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;
import lf.com.android.blackfishdemo.view.AmountView;
import lf.com.android.blackfishdemo.view.GridViewForScroll;
import lf.com.android.blackfishdemo.view.RecyclerViewBanner;
import lf.com.android.blackfishdemo.view.TagsLayout;

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
            if (msg.what == 0x01) {
                final GoodsDetailsInfo detailsInfo = mGoodsDetailsInfos.get(0);
                final List<String> bannerList = detailsInfo.getmBannerList();
                mRvBanner.setRvBannerData(bannerList);
                mRvBanner.setOnSwitchRvBannerListener(new OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, SimpleDraweeView draweeView) {
                        draweeView.setImageURI(bannerList.get(position));
                    }
                });

                mRvBanner.setListener(new OnRvBannerClickListener() {
                    @Override
                    public void OnClick(int position) {
                        skipActivity(new Intent(context, ShowImageActivity.class));
                    }
                });

                String totalPrice = "￥" + detailsInfo.getTotalPrice();
                SpannableString spannableString = new SpannableStringUtil().setMallGoodsPrice(totalPrice, 0, totalPrice.length());
                mTvPrice.setText(spannableString);
                String singlePrice = "月供 " + detailsInfo.getSinglePrice() + " 元起";
                mTvSinglePrice.setText(singlePrice);
                mTvDesc.setText(detailsInfo.getDesc());
                mTvChooseType.setText(detailsInfo.getDefaultType());
                mTvChooseAddress.setText("上海市 浦东新区");
                setDialogData(detailsInfo.getmOptionalTypeInfos());

                List<SimilarRecoInfo> similarRecoInfos = detailsInfo.getmSimilarRecoInfos();
                mGvSimilarReco.setAdapter(new SimilarRecoAdapter(context, similarRecoInfos));
            }
            return false;
        }
    });

    @Override
    public int getlayoutId() {
        return R.layout.activity_goods_detail_layout;
    }

    @Override
    public void initView() {
        Fresco.initialize(this);
        context = GoodsDetailActivity.this;
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

        mIvBack.setOnClickListener(this);
        mIvMore.setOnClickListener(this);
        mIvMoreType.setOnClickListener(this);
        mIvMoreAddress.setOnClickListener(this);
        mRIDialog.setOnClickListener(this);
        mLinearPeriodInfo.setOnClickListener(this);
        mTextFav.setOnClickListener(this);
        mTextImmPay.setOnClickListener(this);
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
                mDialog.show();
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
                Drawable[] drawables = mTextFav.getCompoundDrawables();
                if (!isFav) {
                    mTextFav.setText("已收藏");
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_fav_checked);
                    drawable.setBounds(0, 0, 50, 50);
                    mTextFav.setCompoundDrawables(drawables[0], drawable, drawables[2], drawables[3]);
                    isFav = true;
                } else {
                    mTextFav.setText("收藏");
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_fav_uncheck);
                    drawable.setBounds(0, 0, 50, 50);
                    mTextFav.setCompoundDrawables(drawables[0], drawable, drawables[2], drawables[3]);
                    isFav = false;
                }
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
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_type_layout, null);
        view.findViewById(R.id.iv_close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        SimpleDraweeView draweeView = view.findViewById(R.id.iv_good);
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipActivity(new Intent(context, ShowImageActivity.class));
            }
        });

        TextView textPrice = view.findViewById(R.id.tv_top_price);
        TextView textType = view.findViewById(R.id.tv_type);
        final TextView textAmount = view.findViewById(R.id.tv_num);
        final TextView textSingle = view.findViewById(R.id.tv_price_bottom);
        TextView textPay = view.findViewById(R.id.tv_diter_pay);
        textPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                skipActivity(new Intent(context, SubmitOrderActivity.class));
            }
        });

        TagsLayout tagsLayout = view.findViewById(R.id.labels_view);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < typeInfos.size(); i++) {
            TextView textView = new TextView(context);
            textView.setPadding(10, 10, 10, 10);
            textView.setBackgroundColor(R.drawable.shape_search);
            String s = "   " + typeInfos.get(i).getType() + "   ";
            textView.setText(s);

            if (i == 1) {
                textView.setBackgroundColor(R.drawable.shape_selected_bg);
                textView.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            tagsLayout.addView(textView, lp);
        }

        OptionalTypeInfo typeInfo = typeInfos.get(2);

        String totalPrice = "￥" + typeInfo.getTotalPrice();
        textPrice.setText(totalPrice);

        final double singlePrice = typeInfo.getSinglePrice();
        setSpann(textSingle, singlePrice);
        AmountView amountView = view.findViewById(R.id.amount_view);
        amountView.setMaxNumber(100);
        amountView.setOnNumChangeListener(new OnNumChangeListener() {
            @Override
            public void onChange(int num) {
                String s = "数量: " + num;
                textAmount.setText(s);
                setSpann(textSingle, singlePrice * num);
            }
        });

        mDialog.setContentView(view);
    }

    private void setSpann(TextView textView, double price) {
        String single = "月供 ￥" + price + " 起";
        SpannableString spannableString = new SpannableStringUtil().setMallGoodsPrice(single, 3, 4 + String.valueOf(price).length());
        textView.setText(spannableString);
    }
}
