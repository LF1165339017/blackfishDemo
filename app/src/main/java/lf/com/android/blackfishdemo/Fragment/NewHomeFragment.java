package lf.com.android.blackfishdemo.Fragment;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import lf.com.android.blackfishdemo.R;
import lf.com.android.blackfishdemo.adapter.GeneralVLayoutAdapter;
import lf.com.android.blackfishdemo.bean.BannerInfo;
import lf.com.android.blackfishdemo.bean.HomeSortInfo;
import lf.com.android.blackfishdemo.bean.HomeSortItemfo;
import lf.com.android.blackfishdemo.listener.OnRvBannerClickListener;
import lf.com.android.blackfishdemo.listener.OnSwitchRvBannerListener;
import lf.com.android.blackfishdemo.view.RecyclerViewBanner;

public class NewHomeFragment extends BaseFragment {
    private TextView mtv_home_username, mtv_home_userLevel, mtv_home_userMsg, mtv_home_wallet,
            mtv_home_head_click;

    private RecyclerView mRecyclerView;
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

            return false;
        }
    });

    public static NewHomeFragment newInstance() {
        return new NewHomeFragment();
    }

    @Override
    public void initView() {
        //实例化控件
        mtv_home_username = findView(R.id.tv_home_username);
        mtv_home_userLevel = findView(R.id.tv_home_userlevel);
        mtv_home_userMsg = findView(R.id.tv_home_msg);
        mtv_home_wallet = findView(R.id.tv_home_wallet);
        mtv_home_head_click = findView(R.id.tv_home_head_click);
        mRecyclerView = findView(R.id.rv_fragment_home_context);
        //为TextView设置字体为加粗格式
        mtv_home_username.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mtv_home_userMsg.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mtv_home_wallet.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mtv_home_head_click.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        //初始化VirtualLayout和设置RecyclerView;
        mlayoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mlayoutManager);
        mviewPool = new RecyclerView.RecycledViewPool();
        mviewPool.setMaxRecycledViews(0, 20);
        mRecyclerView.setRecycledViewPool(mviewPool);
        mdelegateAdapter = new DelegateAdapter(mlayoutManager, false);
        mRecyclerView.setAdapter(mdelegateAdapter);

    }

    @Override
    public void initdata() {
        addItemView();
    }

    @Override
    public void lisetener(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav_home;
    }

    private void addItemView() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
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
                mRecyclerView = mainViewHolder.itemView.findViewById(R.id.rvb_home_banner);
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

                    }
                });
            }
        };
        madapters.add(bannerAdapter);

        mdelegateAdapter.setAdapters(madapters);
    }


}
