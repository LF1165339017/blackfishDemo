package lf.com.android.blackfishdemo.Fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.GeneralVLayoutAdapter;
import lf.com.android.blackfishdemo.adapter.GridOnlyImageAdapter;
import lf.com.android.blackfishdemo.adapter.MallHotClassifyGridAdapter;
import lf.com.android.blackfishdemo.adapter.RecommendGoodsAdapter;
import lf.com.android.blackfishdemo.bean.BannerInfo;
import lf.com.android.blackfishdemo.bean.GridInfo;
import lf.com.android.blackfishdemo.bean.MallGoodsInfo;
import lf.com.android.blackfishdemo.bean.MallGoodsItemInfo;
import lf.com.android.blackfishdemo.bean.MallHotClassifyGridInfo;
import lf.com.android.blackfishdemo.bean.MallPagerInfo;
import lf.com.android.blackfishdemo.bean.RecommendGoodsInfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.listener.OnRvBannerClickListener;
import lf.com.android.blackfishdemo.listener.OnSwitchRvBannerListener;
import lf.com.android.blackfishdemo.listener.OnViewItemClickListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.LogUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;
import lf.com.android.blackfishdemo.view.GridViewForScroll;
import lf.com.android.blackfishdemo.view.RecyclerViewBanner;

public class MallFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;

    private Toolbar mToolbar;
    private ImageView mIvMenu, mIvMsg;
    private RelativeLayout mHeaderLayout;
    private RecyclerViewBanner mBanner;
    private List<BannerInfo> mBannerInfos;

    private List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    private VirtualLayoutManager layoutManager;
    private RecyclerView.RecycledViewPool viewPool;

    private DelegateAdapter delegateAdapter;

    private SimpleDraweeView mImageGridItem;
    private TextView mTextGridItem;
    private JsonUtil mJsonUtil = new JsonUtil();
    private List<MallPagerInfo> mMallPagerInfos;
    private List<GridInfo> mGridInfos;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    addItemView(mMallPagerInfos.get(0), onViewItemClickListener);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    public static MallFragment newInstance() {
        return new MallFragment();
    }

    @Override
    public void initView() {
        Fresco.initialize(getActivity());
        mToolbar = findView(R.id.toolbar_mall);
        mToolbar.getBackground().setAlpha(0);
        mContext = getActivity();
        mRecyclerView = findView(R.id.rv_fragment_mall_context);
        mRefreshLayout = findView(R.id.swipe_container);
        mIvMenu = findView(R.id.iv_mall_heard_menu);
        mIvMsg = findView(R.id.iv_mall_header_msg);
        mHeaderLayout = findView(R.id.rl_mall_head_layout);

        layoutManager = new VirtualLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        delegateAdapter = new DelegateAdapter(layoutManager, false);
        mRecyclerView.setAdapter(delegateAdapter);
    }

    @Override
    public void initdata() {
        mJsonUtil = new JsonUtil();
        mMallPagerInfos = new ArrayList<>();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.mallGoodsUrl, new OnNetResultListener() {
            @Override
            public void OnSuccessListener(String result) {
                mMallPagerInfos = mJsonUtil.getDataFromJson(result, 3);
                LogUtil.d("LF1234", "mMallPagerInfos= " + mMallPagerInfos);
                Message msg = mHandler.obtainMessage(0x01);
                mHandler.sendMessage(msg);
            }

            @Override
            public void OnFailureListener(String result) {

            }
        });
    }

    @Override
    public void lisetener(View view) {
        mIvMenu.setOnClickListener(this);
        mIvMsg.setOnClickListener(this);
        mHeaderLayout.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_mall;
    }


    private void addItemView(final MallPagerInfo mallPagerInfo, final OnViewItemClickListener listener) {
        final SingleLayoutHelper layoutHelper = new SingleLayoutHelper();
        GeneralVLayoutAdapter bannerAdapter = new GeneralVLayoutAdapter(mContext, layoutHelper, 1) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(
                        mContext).inflate(R.layout.mall_pager_banner_layout, viewGroup, false);

                return new MainViewHolder(view);
            }


            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
                super.onBindViewHolder(mainViewHolder, i);
                mBanner = mainViewHolder.itemView.findViewById(R.id.rvb_mall_header);
                mBannerInfos = mallPagerInfo.getmBannerInfos();
                mBanner.setRvBannerData(mBannerInfos);
                mBanner.setOnSwitchRvBannerListener(new OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, SimpleDraweeView draweeView) {
                        draweeView.setImageURI(mBannerInfos.get(position).getUrl());
                    }
                });
                mBanner.setListener(new OnRvBannerClickListener() {
                    @Override
                    public void OnClick(int position) {

                    }
                });
            }

        };
        adapters.add(bannerAdapter);


        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
        GeneralVLayoutAdapter gridAdapter = new GeneralVLayoutAdapter(mContext, gridLayoutHelper, 10) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(mContext).inflate(
                        R.layout.mall_pager_two_line_grid, viewGroup, false);
                return new MainViewHolder(view);
            }


            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, final int i) {
                super.onBindViewHolder(mainViewHolder, i);
                mImageGridItem = mainViewHolder.itemView.findViewById(R.id.iv_mall_grid_item);
                mTextGridItem = mainViewHolder.itemView.findViewById(R.id.tv_mall_grid_item);
                mGridInfos = mallPagerInfo.getmClassifyInfo();
                mImageGridItem.setImageURI(mGridInfos.get(i).getImageUrl());
                mTextGridItem.setText(mGridInfos.get(i).getTitle());

                mImageGridItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick("ClassifyGridItem" + i);
                    }
                });
            }
        };
        adapters.add(gridAdapter);

        SingleLayoutHelper fourGoodsHelper = new SingleLayoutHelper();
        GeneralVLayoutAdapter fourGoodsAdapter = new GeneralVLayoutAdapter(mContext, fourGoodsHelper, 1) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(mContext).inflate(
                        R.layout.mall_pager_four_goods_layout, viewGroup, false);
                return new MainViewHolder(view);
            }


            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
                super.onBindViewHolder(mainViewHolder, i);

                SimpleDraweeView headerImage = mainViewHolder.itemView.findViewById(R.id.iv_four_header_image);
                GridViewForScroll gridFourGoods = mainViewHolder.itemView.findViewById(R.id.gv_four_goods);
                gridFourGoods.setAdapter(new GridOnlyImageAdapter(mContext, mallPagerInfo.getmGridGoodsInfos()));
                headerImage.setImageURI(mallPagerInfo.getSingleImageUrl());

                headerImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick("SingleHeaderImage");
                    }
                });
            }
        };
        adapters.add(fourGoodsAdapter);

        GridLayoutHelper gridHotClassifyHelper = new GridLayoutHelper(1);
        GeneralVLayoutAdapter hotClassifyAdapter = new GeneralVLayoutAdapter(
                mContext, gridHotClassifyHelper, mallPagerInfo.getmMallGoodsInfos().size()) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(mContext).inflate(
                        R.layout.mall_pager_hot_classify_grid_layout, viewGroup, false);
                return new MainViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, final int i) {
                super.onBindViewHolder(mainViewHolder, i);
                List<MallHotClassifyGridInfo> mallHotClassifyGridInfos = new ArrayList<>();
                SimpleDraweeView headerImage = mainViewHolder.itemView.findViewById(R.id.iv_hot_classify_header_image);
                GridViewForScroll gridGoods = mainViewHolder.itemView.findViewById(R.id.gv_hot_classify);
                List<MallGoodsInfo> mallGoodsInfos = mallPagerInfo.getmMallGoodsInfos();
                headerImage.setImageURI(mallGoodsInfos.get(i).getHeadImageUrl());
                headerImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick("HotGoodsHeaderImage" + i);
                    }
                });

                int itemSize = mallGoodsInfos.get(i).getmMallGoodsItemInfos().size();

                for (int j = 0; j < itemSize; j++) {
                    MallGoodsItemInfo mallGoodsItemInfo = mallGoodsInfos.get(i).getmMallGoodsItemInfos().get(i);
                    String goodsImage = mallGoodsItemInfo.getImageUrl();
                    String goodsDesc = mallGoodsItemInfo.getDesc();
                    String goodsPeriods = "￥" + mallGoodsItemInfo.getSinglePrice()
                            + " x " + mallGoodsItemInfo.getPeriods() + "期";
                    String goodsPrice = "￥" + mallGoodsItemInfo.getPrice();
                    mallHotClassifyGridInfos.add(new MallHotClassifyGridInfo(goodsImage, goodsDesc, goodsPeriods, goodsPrice));
                }

                MallHotClassifyGridAdapter adapter = new MallHotClassifyGridAdapter(mContext, mallHotClassifyGridInfos);
                adapter.setmOnViewItemClickListener(onViewItemClickListener);
                gridGoods.setAdapter(adapter);

            }
        };

        adapters.add(hotClassifyAdapter);

        final SingleLayoutHelper recoHelper = new SingleLayoutHelper();
        GeneralVLayoutAdapter recoAdapter = new GeneralVLayoutAdapter(mContext, recoHelper, 1) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.mall_pager_recommend_goods_list, viewGroup, false);
                return new MainViewHolder(view);
            }


            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
                super.onBindViewHolder(mainViewHolder, i);
                RecyclerView recyclerView = mainViewHolder.itemView.findViewById(R.id.rv_mall_recommend);
                List<RecommendGoodsInfo> recommendGoodsInfos = mallPagerInfo.getmRecommendGoodsInfos();
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(new RecommendGoodsAdapter(mContext, recommendGoodsInfos));
            }
        };
        adapters.add(recoAdapter);

        delegateAdapter.setAdapters(adapters);
    }

    private OnViewItemClickListener onViewItemClickListener = new OnViewItemClickListener() {
        @Override
        public void onItemClick(String id) {

        }
    };
}
