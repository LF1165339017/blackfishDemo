package lf.com.android.blackfishdemo.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.ClassifyCommonAdaoter;
import lf.com.android.blackfishdemo.adapter.ClassifyTitleAdapter;
import lf.com.android.blackfishdemo.bean.ClassifyGoodsInfo;
import lf.com.android.blackfishdemo.bean.ClassifyGridInfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnClassifyItemClickListener;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.LogUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;
import lf.com.android.blackfishdemo.util.ToastUtil;
import lf.com.android.blackfishdemo.view.GridViewForScroll;

public class ClassifyGoodsActivity extends BaseActivity {
    private Context mContext;
    private ArrayList<String> mListTitles;
    private List<ClassifyGoodsInfo> mClassifyGoodsInfos;
    private ClassifyTitleAdapter mTitleAdapter;
    @BindView(R.id.rv_classify_goods_left_title)
    RecyclerView mRecyclerViewLeft;
    @BindView(R.id.iv_classify_goods_details_header)
    SimpleDraweeView mDraweeViewHeader;
    @BindView(R.id.gv_classify_common)
    GridViewForScroll mGridCommon;
    @BindView(R.id.gv_classify_hot)
    GridViewForScroll mGridHot;
    @BindView(R.id.rl_classify_heard_search)
    RelativeLayout mLayoutHeader;
    @BindView(R.id.iv_classify_goods_back)
    ImageView mImageBack;
    @BindView(R.id.iv_classify_heard_msg)
    ImageView mImageMsg;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    for (int i = 0; i < mClassifyGoodsInfos.size(); i++) {
                        String title = mClassifyGoodsInfos.get(i).getTitle();
                        mListTitles.add(title);
                        if (i == 0) {
                            setItemData(i);
                        }
                    }
                    mTitleAdapter = new ClassifyTitleAdapter(mContext, mListTitles);
                    mTitleAdapter.setListener(new OnClassifyItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            for (int i = 0; i < mRecyclerViewLeft.getChildCount(); i++) {
                                FrameLayout frameLayout = (FrameLayout) mRecyclerViewLeft.getChildAt(i);
                                TextView textView = (TextView) frameLayout.getChildAt(0);
                                if (i == position) {
                                    frameLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    textView.setTextColor(Color.parseColor("#FECD15"));
                                } else {
                                    frameLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
                                    textView.setTextColor(Color.parseColor("#222222"));
                                }
                            }
                            setItemData(position);
                        }
                    });
                    mRecyclerViewLeft.setAdapter(mTitleAdapter);
                    break;
                default:
                    break;
            }

            return false;
        }
    });

    @Override
    public int getlayoutId() {
        return R.layout.activity_classify_goods_layout;
    }

    @Override
    public void initView() {
        Fresco.initialize(this);

        //5.0及以上系统才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            //两个Flag必须要结合在一起使用，表示会让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //将状态栏设置成透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //将actionBar隐藏
            actionBar.hide();
        }
        mContext = ClassifyGoodsActivity.this;
        mRecyclerViewLeft.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewLeft.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mLayoutHeader.setOnClickListener(this);
        mImageMsg.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void intitdata() {
        mClassifyGoodsInfos = new ArrayList<>();
        mListTitles = new ArrayList<>();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.classifyGoodsUrl, new OnNetResultListener() {
            @Override
            public void OnSuccessListener(String result) {
                JsonUtil jsonUtil = new JsonUtil();
                mClassifyGoodsInfos = jsonUtil.getDataFromJson(result, 2);
                Message message = mHandler.obtainMessage(0x01, mClassifyGoodsInfos);
                mHandler.sendMessage(message);
            }

            @Override
            public void OnFailureListener(String result) {
                Toast toast = ToastUtil.setMyToast(mContext, ToastUtil.PROMPT, "网络请求失败", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    @Override
    public void ClickListener(View view) {
        switch (view.getId()) {
            case R.id.rl_classify_heard_search:
                Toast.makeText(mContext, "查询", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_classify_goods_back:
                finishActivity();
                break;
            case R.id.iv_classify_heard_msg:
                Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private void setItemData(int position) {
        List<ClassifyGridInfo> mGridInfosCommon = new ArrayList<>();
        List<ClassifyGridInfo> mGridInfosHot = new ArrayList<>();
        String hearderImageUrl = mClassifyGoodsInfos.get(position).getHeaderImageUrl();
        int commonSize = mClassifyGoodsInfos.get(position).getGridImageUrls1().size();
        int hotSize = mClassifyGoodsInfos.get(position).getGridImageUrls2().size();
        for (int i = 0; i < commonSize; i++) {
            mGridInfosCommon.add(mClassifyGoodsInfos.get(position).getGridImageUrls1().get(i));
        }

        for (int i = 0; i < hotSize; i++) {
            mGridInfosHot.add(mClassifyGoodsInfos.get(position).getGridImageUrls2().get(i));
        }

        mDraweeViewHeader.setImageURI(hearderImageUrl);
        mGridCommon.setAdapter(new ClassifyCommonAdaoter(mContext, mGridInfosCommon));
        mGridHot.setAdapter(new ClassifyCommonAdaoter(mContext, mGridInfosHot));
    }
}
