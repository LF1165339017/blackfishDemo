package lf.com.android.blackfishdemo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lf.com.android.blackfishdemo.Activity.BaseWebViewActivity;
import lf.com.android.blackfishdemo.Activity.ClassifyGoodsActivity;
import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.GeneralVLayoutAdapter;
import lf.com.android.blackfishdemo.bean.BannerInfo;
import lf.com.android.blackfishdemo.bean.HomeSortInfo;
import lf.com.android.blackfishdemo.bean.HomeSortItemfo;
import lf.com.android.blackfishdemo.bean.UrlInfoBean;
import lf.com.android.blackfishdemo.listener.OnNetResultListener;
import lf.com.android.blackfishdemo.listener.OnRvBannerClickListener;
import lf.com.android.blackfishdemo.listener.OnSwitchRvBannerListener;
import lf.com.android.blackfishdemo.util.AlphaRecyclerListener;
import lf.com.android.blackfishdemo.util.JsonUtil;
import lf.com.android.blackfishdemo.util.LogUtil;
import lf.com.android.blackfishdemo.util.OkHttpUtil;
import lf.com.android.blackfishdemo.util.RecyclerAlphaListener;
import lf.com.android.blackfishdemo.util.ToastUtil;
import lf.com.android.blackfishdemo.view.RecyclerViewBanner;

public class NewHomeFragment extends BaseFragment implements AlphaRecyclerListener {
    private RecyclerView mRecyclerView;
    private RelativeLayout mTitleLayout;
    private VirtualLayoutManager mlayoutManager;
    private RecyclerView.RecycledViewPool mviewPool;//Recycler复用池
    private DelegateAdapter mdelegateAdapter;
    private List<DelegateAdapter.Adapter> madapters = new LinkedList<>();
    private List<BannerInfo> mBannerInfos;
    private List<HomeSortInfo> mHomeSortInfos;
    private List<HomeSortItemfo> mHomeSortItemfos;
    private RecyclerViewBanner mRecyclerViewBanner;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0x01) {
                addItemView(mHomeSortInfos);
            }
            return false;
        }
    });

    public static NewHomeFragment newInstance() {
        return new NewHomeFragment();
    }


    @Override
    public void initView() {
        Fresco.initialize(getContext());//初始化默认配置
        //实例化控件
        mRecyclerView = findView(R.id.rv_fragment_home_context);
        mTitleLayout = findView(R.id.rl_title);
        //初始化VirtualLayout和设置RecyclerView;
        mlayoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);
        mviewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(mviewPool);
        mviewPool.setMaxRecycledViews(0, 20);
        mdelegateAdapter = new DelegateAdapter(mlayoutManager, false);
        mRecyclerView.setAdapter(mdelegateAdapter);
        //设置滑动监听
        RecyclerAlphaListener alphaListener = new RecyclerAlphaListener(this);
        mRecyclerView.addOnScrollListener(alphaListener);
    }

    @Override
    public void initdata() {
        mHomeSortInfos = new ArrayList<>();
        mHomeSortItemfos = new ArrayList<>();
        final JsonUtil jsonUtil = new JsonUtil();
        OkHttpUtil.getInstance().startGet(UrlInfoBean.homeGoodsUrl, new OnNetResultListener() {
            @Override
            public void OnSuccessListener(String result) {
                LogUtil.d("LF1234", "result=" + result);
                mHomeSortInfos = jsonUtil.getDataFromJson(result, 0);
                LogUtil.d("LF123", "mHomeSortInfos=" + mHomeSortInfos);
                Message message = mHandler.obtainMessage(0x01, mHomeSortInfos);
                mHandler.sendMessage(message);
            }

            @Override
            public void OnFailureListener(String result) {

            }
        });
    }

    @Override
    public void lisetener(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_home;
    }

    private void addItemView(final List<HomeSortInfo> homeSortInfos) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        GeneralVLayoutAdapter topAdapter = new GeneralVLayoutAdapter(
                getContext(), singleLayoutHelper, 1) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(
                        R.layout.new_home_fragment_toolbar_layout, viewGroup, false);
                return new MainViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
                TextView mTvUserName = mainViewHolder.itemView.findViewById(R.id.tv_home_username);
                TextView mTvMsg = mainViewHolder.itemView.findViewById(R.id.tv_home_msg);
                TextView mTvWallet = mainViewHolder.itemView.findViewById(R.id.tv_home_wallet);
                LinearLayout mLlCard = mainViewHolder.itemView.findViewById(R.id.linear_card);

                mTvUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "点击了姓名", Toast.LENGTH_SHORT).show();
                    }
                });

                mTvMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "点击了信息", Toast.LENGTH_SHORT).show();
                    }
                });
                mTvWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "点击了钱包", Toast.LENGTH_LONG).show();
                    }
                });

                mLlCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "点击了会员卡", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        madapters.add(topAdapter);

        GeneralVLayoutAdapter bannerAdapter = new GeneralVLayoutAdapter(
                getContext(), singleLayoutHelper, 1) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(
                        R.layout.home_pager_bannner_layout, viewGroup, false);
                return new MainViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
                super.onBindViewHolder(mainViewHolder, i);
                mRecyclerViewBanner = mainViewHolder.itemView.findViewById(R.id.rvb_home_banner);
                mBannerInfos = new ArrayList<>();
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/06/5ac733bc51d0a.png"));
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/06/5ac735502effe.png"));
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/07/5ac8459fc9b6a.png"));
                mBannerInfos.add(new BannerInfo("https://i.loli.net/2018/04/06/5ac7339ee876e.jpg"));
                mRecyclerViewBanner.setRvBannerData(mBannerInfos);
                mRecyclerViewBanner.setOnSwitchRvBannerListener(new OnSwitchRvBannerListener() {
                    @Override
                    public void switchBanner(int position, SimpleDraweeView draweeView) {
                        draweeView.setImageURI(mBannerInfos.get(position).getUrl());
                    }
                });
                mRecyclerViewBanner.setListener(new OnRvBannerClickListener() {
                    @Override
                    public void OnClick(int position) {
                        toWebActivity(UrlInfoBean.homeBannerUrls[position]);
                    }
                });
            }
        };
        madapters.add(bannerAdapter);

        //加载主页两行网格布局

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        GeneralVLayoutAdapter gridAdapter = new GeneralVLayoutAdapter(
                getContext(), gridLayoutHelper, 8) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(
                        R.layout.home_pager_grid_two_line, viewGroup, false);
                return new MainViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
                super.onBindViewHolder(mainViewHolder, i);
                ImageView imageView = mainViewHolder.itemView.findViewById(R.id.iv_home_one_grid_icon);
                TextView textView = mainViewHolder.itemView.findViewById(R.id.iv_home_one_grid_title);
                switch (i) {
                    case 0:
                        textView.setText("充值中心");
                        imageView.setImageResource(R.drawable.icon_voucher_center);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_1"));
                        break;
                    case 1:
                        textView.setText("手机通讯");
                        imageView.setImageResource(R.drawable.icon_phone);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_2"));
                        break;
                    case 2:
                        textView.setText("电影票");
                        imageView.setImageResource(R.drawable.icon_movie);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_3"));
                        break;
                    case 3:
                        textView.setText("全民游戏");
                        imageView.setImageResource(R.drawable.icon_game);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_4"));
                        break;
                    case 4:
                        textView.setText("代还信用卡");
                        imageView.setImageResource(R.drawable.icon_pay_card);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_5"));
                        break;
                    case 5:
                        textView.setText("现金分期");
                        imageView.setImageResource(R.drawable.icon_cash_fenqi);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_6"));
                        break;
                    case 6:
                        textView.setText("办信用卡");
                        imageView.setImageResource(R.drawable.icon_ban_card);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_7"));
                        break;
                    case 7:
                        textView.setText("全部分类");
                        imageView.setImageResource(R.drawable.icon_all_classify);
                        imageView.setOnClickListener(new MyClick("iv_home_one_grid_icon_8"));
                        break;
                    default:
                        break;
                }

            }
        };
        madapters.add(gridAdapter);

        int count = homeSortInfos.size();
        GridLayoutHelper sortHelp = new GridLayoutHelper(1);
        GeneralVLayoutAdapter sortAdapter = new GeneralVLayoutAdapter(getContext(), sortHelp, count) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(
                        getActivity()).inflate(R.layout.home_pager_goods_layout, viewGroup, false);
                return new MainViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, final int i) {
                super.onBindViewHolder(mainViewHolder, i);
                HomeSortInfo homeSortInfo = homeSortInfos.get(i);
                TextView textTitle = mainViewHolder.itemView.findViewById(R.id.tv_home_goods_title_text);
                textTitle.setText(homeSortInfo.getTitle());
                SimpleDraweeView draweeView = mainViewHolder.itemView.findViewById(R.id.iv_home_goods_bigImage);
                draweeView.setImageURI(homeSortInfo.getSortImageUrl());
                draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toWebActivity(UrlInfoBean.homeHeaderUrls[i]);
                    }
                });

                TextView textLeftImage = mainViewHolder.itemView.findViewById(R.id.tv_home_goods_title_image);
                switch (i) {
                    case 1:
                        textLeftImage.setBackground(getContext().getResources().getDrawable(R.drawable.shape_home_goods_title1));
                        break;
                    case 2:
                        textLeftImage.setBackground(getContext().getResources().getDrawable(R.drawable.shape_home_goods_title2));
                        break;
                    case 3:
                        textLeftImage.setBackground(getContext().getResources().getDrawable(R.drawable.shape_home_goods_title3));
                        break;
                    default:
                        break;
                }

                RelativeLayout heardLayout = mainViewHolder.itemView.findViewById(R.id.rl_goods_title_layout);
                heardLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), ClassifyGoodsActivity.class));
                        getActivity().overridePendingTransition(R.anim.activity_right_in, 0);
                    }
                });

                List<HomeSortItemfo> sortItemfos = homeSortInfo.getmItemfos();
                SimpleDraweeView draweeView1Item1 = mainViewHolder.itemView.findViewById(R.id.iv_home_goods_item_1);
                SimpleDraweeView draweeView1Item2 = mainViewHolder.itemView.findViewById(R.id.iv_home_goods_item_2);
                SimpleDraweeView draweeView1Item3 = mainViewHolder.itemView.findViewById(R.id.iv_home_goods_item_3);
                SimpleDraweeView draweeView1Item4 = mainViewHolder.itemView.findViewById(R.id.iv_home_goods_item_4);

                draweeView1Item1.setImageURI(sortItemfos.get(0).getGoodsImageUrl());
                draweeView1Item2.setImageURI(sortItemfos.get(1).getGoodsImageUrl());
                draweeView1Item3.setImageURI(sortItemfos.get(2).getGoodsImageUrl());
                draweeView1Item4.setImageURI(sortItemfos.get(3).getGoodsImageUrl());

                draweeView1Item1.setOnClickListener(new MyClick("iv_home_goods_item_1"));
                draweeView1Item2.setOnClickListener(new MyClick("iv_home_goods_item_2"));
                draweeView1Item3.setOnClickListener(new MyClick("iv_home_goods_item_3"));
                draweeView1Item4.setOnClickListener(new MyClick("iv_home_goods_item_4"));
            }
        };

        madapters.add(sortAdapter);

        mdelegateAdapter.setAdapters(madapters);

    }

    @Override
    public void onScrollAlpha(float alpha) {
        mTitleLayout.setAlpha(alpha);
    }

    @Override
    public void onScroll(RecyclerView recyclerView, int dx, int dy) {

    }

    private class MyClick implements View.OnClickListener {
        private String text;

        public MyClick(String text) {
            this.text = text;
        }

        @Override
        public void onClick(View v) {
            for (int i = 1; i <= 4; i++) {
                if (text.equals("iv_home_goods_item_" + i)) {
                    Toast.makeText(getActivity(), "点击了第" + i + "Item页面", Toast.LENGTH_SHORT).show();
                }
            }
            switch (text) {
                case "iv_home_one_grid_icon_1":
                    Toast toast1 = ToastUtil.setMyToast(
                            getContext(), ToastUtil.PROMPT,
                            "充值中心", Toast.LENGTH_SHORT);
                    toast1.show();
                    break;
                case "iv_home_one_grid_icon_2":
                    Toast toast2 = ToastUtil.setMyToast(
                            getContext(), ToastUtil.PROMPT,
                            "手机通讯", Toast.LENGTH_SHORT);
                    toast2.show();
                    break;
                case "iv_home_one_grid_icon_3":
                    Toast toast3 = ToastUtil.setMyToast(
                            getContext(), ToastUtil.PROMPT,
                            "电影票", Toast.LENGTH_SHORT);
                    toast3.show();
                    break;
                case "iv_home_one_grid_icon_4":
                    toWebActivity(UrlInfoBean.gameUrl);
                    break;
                case "iv_home_one_grid_icon_5":
                    Toast toast5 = ToastUtil.setMyToast(
                            getContext(), ToastUtil.PROMPT,
                            "代还信用卡", Toast.LENGTH_SHORT);
                    toast5.show();
                    break;
                case "iv_home_one_grid_icon_6":
                    Toast toast6 = ToastUtil.setMyToast(
                            getContext(), ToastUtil.PROMPT,
                            "现金分期", Toast.LENGTH_SHORT);
                    toast6.show();
                    break;
                case "iv_home_one_grid_icon_7":
                    toWebActivity(UrlInfoBean.bankCard);
                    break;
                case "iv_home_one_grid_icon_8":
                    startActivity(new Intent(getContext(), ClassifyGoodsActivity.class));
                    getActivity().overridePendingTransition(R.anim.activity_right_in, 0);
                    break;
                default:
                    break;
            }
        }
    }

    private void toWebActivity(String loadUrl) {
        Intent intent = new Intent(getActivity(), BaseWebViewActivity.class);
        intent.putExtra("loadUrl", loadUrl);
        startActivity(intent);

    }

}
